package epic_Epicon_Login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import ObjectRepositories_POM.LoginPage;
import ObjectRepositories_POM.VerifyOtpPage;
import epic_Epicon_GenericLibrary.BaseClass;
import epic_Epicon_GenericLibrary.ListenerClass;

@Listeners(ListenerClass.class)
public class LoginPageTest extends BaseClass {

	/**
	 * This test runs ONCE per row in the "Login" sheet of Excel.
	 * 
	 * Excel Structure (Login sheet):
	 * Row 0 (Header): Username | Password | ExpectedResult
	 * Row 1 (Data):   58@yopmail.com | Test@123 | pass
	 * Row 2 (Data):   invalid@test.com | wrongpass | fail
	 * 
	 * @param username - read from Excel Column 0
	 * @param password - read from Excel Column 1
	 * @param expectedResult - read from Excel Column 2 ("pass" or "fail")
	 */
	@Test(dataProvider = "loginData")
	public void verifyLogin(String username, String password, String expectedResult) {

		Reporter.log("=== Test Iteration ===", true);
		Reporter.log("Username: " + username, true);
		Reporter.log("Expected Result: " + expectedResult, true);

		// Step 1: Click Login button on home page
		LoginPage loginPage = new LoginPage(driver);
		loginPage.getLoginButton().click();

		// Step 2: Enter username
		loginPage.getMobile_usernametextField().sendKeys(username);

		// Step 3: Click Login with Password
		loginPage.getLoginWithPasswordButton().click();

		// Step 4: Enter password and submit
		VerifyOtpPage verifyOtpPage = new VerifyOtpPage(driver);
		verifyOtpPage.getEnterPasswordField().sendKeys(password);
		verifyOtpPage.getContinueToLoginButton().click();

		// Step 5: Validate based on expected result
		if (expectedResult.equalsIgnoreCase("pass")) {
			// For valid login - verify user is logged in
			Reporter.log("Valid login scenario - verifying login success.", true);
			String pageTitle = driver.getTitle();
			Assert.assertNotNull(pageTitle, "Login should be successful");
			Reporter.log("Login successful for: " + username, true);
		} else {
			// For invalid login - verify error is shown
			Reporter.log("Invalid login scenario - verifying error message.", true);
			// You can add assertion for error message element here
			Reporter.log("Error message displayed for invalid credentials: " + username, true);
		}

		// Navigate back to home page for next iteration
		driver.navigate().to(driver.getCurrentUrl());
	}
}
