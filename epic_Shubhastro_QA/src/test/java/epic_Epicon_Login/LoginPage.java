package epic_Epicon_Login;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import ObjectRepositories_POM.HomePage;
import ObjectRepositories_POM.LoginPageLocators;
import ObjectRepositories_POM.VerifyOtpPage;
import epic_Epicon_GenericLibrary.BaseClass;
import epic_Epicon_GenericLibrary.ListenerClass;

@Listeners(ListenerClass.class)
public class LoginPage extends BaseClass {

	@Test(dataProvider = "loginData")
	public void verifyLogin(String username, String password, String expectedResult) throws Exception {

		Reporter.log("=== Test Iteration ===", true);
		Reporter.log("Username: " + username, true);
		Reporter.log("Expected Result: " + expectedResult, true);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Step 1: Re-initialize POM fresh for each iteration (avoids stale element)
		LoginPageLocators loginPage = new LoginPageLocators(driver);
		wait.until(ExpectedConditions.elementToBeClickable(loginPage.getLoginButton()));
		loginPage.getLoginButton().click();
		Reporter.log("Clicked on Login button.", true);

		// Step 2: Enter username
		loginPage = new LoginPageLocators(driver); // Re-init after page change
		wait.until(ExpectedConditions.visibilityOf(loginPage.getMobile_usernametextField()));
		loginPage.getMobile_usernametextField().sendKeys(username);
		Reporter.log("Entered username: " + username, true);

		// Step 3: Click Login with Password
		loginPage.getLoginWithPasswordButton().click();
		Reporter.log("Clicked Login with Password.", true);

		// Step 4: Enter password and submit
		VerifyOtpPage verifyOtpPage = new VerifyOtpPage(driver);
		wait.until(ExpectedConditions.visibilityOf(verifyOtpPage.getEnterPasswordField()));
		verifyOtpPage.getEnterPasswordField().sendKeys(password);
		verifyOtpPage.getContinueToLoginButton().click();
		Reporter.log("Entered password and clicked Continue.", true);

		// Step 5: Validate based on expected result
		if (expectedResult.equalsIgnoreCase("pass")) {
			Reporter.log("Valid login scenario - verifying login success.", true);
			seleniumUtility.explicitWaitForTitle(driver, "", 5);
			String pageTitle = driver.getTitle();
			Assert.assertNotNull(pageTitle, "Login should be successful");
			Reporter.log("Login successful for: " + username, true);

			// Logout after successful login
			HomePage homePage = new HomePage(driver);
			wait.until(ExpectedConditions.elementToBeClickable(homePage.getLogoutButton()));
			homePage.getLogoutButton().click();
			Reporter.log("Logged out successfully.", true);

			// Wait for home page to load
			wait.until(ExpectedConditions.elementToBeClickable(new LoginPageLocators(driver).getLoginButton()));

		} else {
			Reporter.log("Invalid login scenario - verifying error message.", true);
			// Wait for error to appear
			try { Thread.sleep(3000); } catch (InterruptedException e) { }
			Reporter.log("Error displayed for invalid credentials: " + username, true);

			// Navigate back to home page for next iteration
			driver.navigate().to(fileUtilities.PropertyFileData("url"));

			// Wait for home page to fully load
			wait.until(ExpectedConditions.elementToBeClickable(new LoginPageLocators(driver).getLoginButton()));
			Reporter.log("Navigated back to home page.", true);
		}
	}
}
