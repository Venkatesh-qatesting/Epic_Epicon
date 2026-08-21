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
