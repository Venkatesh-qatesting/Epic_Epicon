package epic_Epicon_Login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import org.openqa.selenium.WebElement;

import ObjectRepositories_POM.HomePage;
import ObjectRepositories_POM.LoginPageLocators;
import ObjectRepositories_POM.MaxDeviceLimitPage;
import ObjectRepositories_POM.VerifyOtpPage;
import epic_Epicon_GenericLibrary.BaseClass;
import epic_Epicon_GenericLibrary.ListenerClass;

@Listeners(ListenerClass.class)
public class LoginPage extends BaseClass {

	// Centralized explicit-wait timeout (seconds) used via SeleniumUtility.
	private static final int WAIT_TIMEOUT = 15;

	// Short timeout (seconds) for the optional "Max Device" limit page check, so
	// we do not block the full WAIT_TIMEOUT on iterations where it is not shown.
	private static final int OPTIONAL_PAGE_TIMEOUT = 5;

	@Test(dataProvider = "loginData")
	public void verifyLogin(String username, String password, String expectedResult) throws Exception {

		Reporter.log("=== Test Iteration ===", true);
		Reporter.log("Username: " + username, true);
		Reporter.log("Expected Result: " + expectedResult, true);

		// Step 1: Re-initialize POM fresh for each iteration (avoids stale element)
		LoginPageLocators loginPage = new LoginPageLocators(driver);

		// Wait for the Login button to be present, scroll it into view, then JS-click.
		// Presence + jsClick is more robust than elementToBeClickable on this page,
		// where the button can be present but briefly not "clickable" after a reload.
		clickLoginButton();

		// Step 2: Enter username
		loginPage = new LoginPageLocators(driver); // Re-init after page change
		seleniumUtility.explicitWaitForVisibility(driver, loginPage.getMobile_usernametextField(), WAIT_TIMEOUT);
		loginPage.getMobile_usernametextField().sendKeys(username);
		Reporter.log("Entered username: " + username, true);

		// Step 3: Click Login with Password
		seleniumUtility.explicitWaitForClickable(driver, loginPage.getLoginWithPasswordButton(), WAIT_TIMEOUT);
		loginPage.getLoginWithPasswordButton().click();
		Reporter.log("Clicked Login with Password.", true);

		// Step 4: Enter password and submit
		VerifyOtpPage verifyOtpPage = new VerifyOtpPage(driver);
		seleniumUtility.explicitWaitForVisibility(driver, verifyOtpPage.getEnterPasswordField(), WAIT_TIMEOUT);
		verifyOtpPage.getEnterPasswordField().sendKeys(password);
		seleniumUtility.explicitWaitForClickable(driver, verifyOtpPage.getContinueToLoginButton(), WAIT_TIMEOUT);
		verifyOtpPage.getContinueToLoginButton().click();
		Reporter.log("Entered password and clicked Continue.", true);

		// Step 4b: If the account has hit its device limit, the "Max Device" page is
		// shown instead of completing the login. Handle it (free a slot) and continue.
		handleMaxDeviceLimitIfPresent();

		// Step 5: Validate based on expected result using TestNG Assert.
		try {
			if (expectedResult.equalsIgnoreCase("pass")) {
				Reporter.log("Valid login scenario - verifying login success.", true);

				// A successful login redirects to the home page where the Profile icon appears.
				// Wait for it by locator (fresh lookup) - the page may have re-rendered after
				// a Max Device redirect, so avoid reusing an earlier (possibly stale) element.
				seleniumUtility.explicitWaitForPresence(driver, HomePage.PROFILE_ICON, WAIT_TIMEOUT);
				Assert.assertTrue(seleniumUtility.isElementPresent(driver, HomePage.PROFILE_ICON, WAIT_TIMEOUT),
						"Login should be successful for user: " + username);
				Reporter.log("Login successful for: " + username, true);

				// Re-initialize the POM so PageFactory proxies point at the current DOM,
				// then scroll to and JS-click the Profile icon (stale-safe).
				HomePage homePage = new HomePage(driver);
				seleniumUtility.scrollToElement(driver, homePage.getprofileIcon());
				seleniumUtility.jsClick(driver, homePage.getprofileIcon());
				Reporter.log("Clicked on Profile icon.", true);

				// The Logout button sits at the bottom of the profile menu, so wait for it,
				// scroll it into view, then JS-click it. A native click can be intercepted by
				// the menu overlay/animation and silently miss, so jsClick is more reliable.
				seleniumUtility.explicitWaitForVisibility(driver, homePage.getLogoutButton(), WAIT_TIMEOUT);
				seleniumUtility.scrollToElement(driver, homePage.getLogoutButton());
				seleniumUtility.jsClick(driver, homePage.getLogoutButton());

				// Confirm logout actually happened: the Login button should reappear.
				seleniumUtility.explicitWaitForPresence(driver, LoginPageLocators.LOGIN_BUTTON, WAIT_TIMEOUT);
				Reporter.log("Logged out successfully.", true);

			} else {
				Reporter.log("Invalid login scenario - verifying error message.", true);

				// An invalid login must surface an error message; assert it is displayed.
				boolean errorShown = seleniumUtility
						.explicitWaitForVisibility(driver, verifyOtpPage.getErrorMessage(), WAIT_TIMEOUT)
						.isDisplayed();
				Assert.assertTrue(errorShown,
						"Error message should be displayed for invalid credentials: " + username);
				Reporter.log("Error displayed for invalid credentials: " + username, true);
			}
		} finally {
			// Reset to the home page so the next data-provider iteration starts clean.
			// Wrapped in try/catch so a reset failure never masks the real test failure
			// from the try block above.
			try {
				driver.navigate().to(fileUtilities.PropertyFileData("url"));
				seleniumUtility.explicitWaitForPresence(driver, LoginPageLocators.LOGIN_BUTTON, WAIT_TIMEOUT);
				Reporter.log("Navigated back to home page.", true);
			} catch (Exception resetEx) {
				Reporter.log("Home-page reset failed: " + resetEx.getMessage(), true);
			}
		}
	}

	/**
	 * Negative test: open the login modal and click "Continue to Login" with both
	 * the Email/Mobile and Password fields left empty. The app must show the
	 * required-field validation messages for both fields.
	 */
	@Test
	public void verifyEmptyFieldsValidation() throws Exception {
		Reporter.log("=== Empty Fields Validation Test ===", true);

		// Open the login modal.
		clickLoginButton();

		// Click "Login with password" to reach the password screen (username left empty).
		LoginPageLocators loginPage = new LoginPageLocators(driver);
		seleniumUtility.explicitWaitForClickable(driver, loginPage.getLoginWithPasswordButton(), WAIT_TIMEOUT);
		loginPage.getLoginWithPasswordButton().click();
		Reporter.log("Clicked Login with Password.", true);

		VerifyOtpPage verifyOtpPage = new VerifyOtpPage(driver);

		// Leave both the Email/Mobile and Password fields empty, then click
		// "Continue to Login". Scroll + JS-click for reliability.
		seleniumUtility.explicitWaitForVisibility(driver, verifyOtpPage.getContinueToLoginButton(), WAIT_TIMEOUT);
		seleniumUtility.scrollToElement(driver, verifyOtpPage.getContinueToLoginButton());
		seleniumUtility.jsClick(driver, verifyOtpPage.getContinueToLoginButton());
		Reporter.log("Clicked Continue to Login with empty username and password.", true);

		// Both required-field messages must be displayed.
		boolean emailRequired = seleniumUtility
				.explicitWaitForVisibility(driver, verifyOtpPage.getEmailRequiredMessage(), WAIT_TIMEOUT)
				.isDisplayed();
		boolean passwordRequired = seleniumUtility
				.explicitWaitForVisibility(driver, verifyOtpPage.getPasswordRequiredMessage(), WAIT_TIMEOUT)
				.isDisplayed();

		Assert.assertTrue(emailRequired, "'Email or Mobile is required' message should be displayed.");
		Assert.assertTrue(passwordRequired, "'Password is required' message should be displayed.");
		Reporter.log("Required-field validation messages displayed for empty fields.", true);

		// Reset to the home page for any subsequent tests.
		try {
			driver.navigate().to(fileUtilities.PropertyFileData("url"));
			seleniumUtility.explicitWaitForPresence(driver, LoginPageLocators.LOGIN_BUTTON, WAIT_TIMEOUT);
			Reporter.log("Navigated back to home page.", true);
		} catch (Exception resetEx) {
			Reporter.log("Home-page reset failed: " + resetEx.getMessage(), true);
		}
	}

	/**
	 * Detects the "Max Device" limit page and, if present, frees a device slot so
	 * the login can proceed. Logging out any single device is enough - the app
	 * then redirects to the home page. Prefers an "other" device (keeps the
	 * current session), and falls back to the current device if none are listed.
	 * Does nothing if the Max Device page is not shown.
	 */
	private void handleMaxDeviceLimitIfPresent() {
		// Detect the page via title text or the presence of the other-devices list.
		boolean maxDevicePage = driver.getTitle() != null
				&& driver.getTitle().contains(MaxDeviceLimitPage.PAGE_TITLE_TEXT);
		boolean otherDevicesShown = seleniumUtility.isElementPresent(driver,
				MaxDeviceLimitPage.OTHER_DEVICES_CONTAINER, OPTIONAL_PAGE_TIMEOUT);

		if (!maxDevicePage && !otherDevicesShown) {
			return; // Normal flow - limit page not shown.
		}

		Reporter.log("Max Device limit page detected - logging out one device to free a slot.", true);
		MaxDeviceLimitPage limitPage = new MaxDeviceLimitPage(driver);

		// Log out a single device. Prefer an "other" device; otherwise the current one.
		List<WebElement> otherDeviceButtons = limitPage.getOtherDeviceLogoutButtons(driver);
		if (otherDeviceButtons != null && !otherDeviceButtons.isEmpty()) {
			WebElement button = otherDeviceButtons.get(0);
			seleniumUtility.scrollToElement(driver, button);
			seleniumUtility.jsClick(driver, button);
			Reporter.log("Logged out one other device.", true);
		} else {
			seleniumUtility.explicitWaitForClickable(driver, limitPage.getCurrentDeviceLogoutButton(), WAIT_TIMEOUT);
			seleniumUtility.scrollToElement(driver, limitPage.getCurrentDeviceLogoutButton());
			seleniumUtility.jsClick(driver, limitPage.getCurrentDeviceLogoutButton());
			Reporter.log("Logged out the current device.", true);
		}

		// After logging out one device the app redirects to the home page; wait for
		// it to be ready before the login validation continues.
		seleniumUtility.explicitWaitForPresence(driver, ObjectRepositories_POM.HomePage.PROFILE_ICON, WAIT_TIMEOUT);
		Reporter.log("Redirected to home page after freeing a device slot.", true);
	}

	/**
	 * Waits for the Login button to be present in the DOM, scrolls it into view,
	 * and clicks it via JavaScript. Centralizes the click used at the start of
	 * every iteration and after a logout.
	 */
	private void clickLoginButton() {
		LoginPageLocators loginPage = new LoginPageLocators(driver);
		seleniumUtility.explicitWaitForPresence(driver, LoginPageLocators.LOGIN_BUTTON, WAIT_TIMEOUT);
		seleniumUtility.scrollToElement(driver, loginPage.getLoginButton());
		seleniumUtility.jsClick(driver, loginPage.getLoginButton());
		Reporter.log("Clicked on Login button.", true);
	}
}
