package ObjectRepositories_POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VerifyOtpPage {
	
	@FindBy(xpath = "//div[@class='childpin']/div[@class='childpin-input no-number-arrows']")
	private WebElement enterOtpFields;
	
	@FindBy(xpath = "//button[@id='resendBtn']")
	private WebElement resendOtpButton;
	
	@FindBy(xpath ="//button[contains(text(), 'Verify OTP')]")
	private WebElement verifyOtpButton;
	
	@FindBy(xpath = "//button[contains(text(), 'Login with password')]")
	private WebElement loginWithPasswordButton;

	@FindBy(xpath = "//input[@id='login_pwd']")
	private WebElement enterPasswordField;
	
	@FindBy(xpath = "//button[contains(text(),'Continue to Login')]")
	private WebElement continueToLoginButton;

	// Error message shown for a failed login. Matches both invalid-credentials
	// (wrong password) and user-not-found (non-existent account) variants.
	// translate(...) lowercases the text so the match is case-insensitive.
	@FindBy(xpath = "//div[contains(text(), 'Invalid credentials.')] "
			+ "| //*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'user not found')]")
	private WebElement errorMessage;

	// Required-field validation messages shown when Continue to Login is clicked
	// with the Email/Mobile and Password fields left empty.
	@FindBy(xpath = "//*[contains(text(),'Email or Mobile is required')]")
	private WebElement emailRequiredMessage;

	@FindBy(xpath = "//*[contains(text(),'Password is required')]")
	private WebElement passwordRequiredMessage;

	public WebElement getEmailRequiredMessage() {
		return emailRequiredMessage;
	}

	public WebElement getPasswordRequiredMessage() {
		return passwordRequiredMessage;
	}

	public WebElement getErrorMessage() {
		return errorMessage;
	}

	public WebElement getContinueToLoginButton() {
		return continueToLoginButton;
	}

	public WebElement getEnterOtpFields() {
		return enterOtpFields;
	}

	public WebElement getResendOtpButton() {
		return resendOtpButton;
	}

	public WebElement getVerifyOtpButton() {
		return verifyOtpButton;
	}

	public WebElement getLoginWithPasswordButton() {
		return loginWithPasswordButton;
	}
	
	public WebElement getEnterPasswordField() {
		return enterPasswordField;
	}
	
	public VerifyOtpPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void verifyOtp(WebDriver driver, CharSequence[] otp) {
	VerifyOtpPage verifyOtp = new VerifyOtpPage(driver);
	verifyOtp.enterOtpFields.sendKeys(otp);
	verifyOtp.resendOtpButton.click();
	verifyOtp.verifyOtpButton.click();
	}
	}
