package ObjectRepositories_POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageLocators {

	// Reusable locator for the Login button (used with presence-based waits).
	public static final By LOGIN_BUTTON = By
			.xpath("//a[contains(normalize-space(.), 'Login')] | //button[contains(normalize-space(.), 'Login')]");

	@FindBy(xpath = "//a[contains(normalize-space(.), 'Login')] | //button[contains(normalize-space(.), 'Login')]")
		private WebElement loginButton;
	
	@FindBy(xpath = "//input[@id='mobile_number']")
	private WebElement mobile_usernametextField;
	
	@FindBy(xpath = "//button[contains(text(),'Get OTP')]")
	private WebElement getOTPButton;
	
	@FindBy(xpath = "//button[contains(text(),'Login with password')]")
	private WebElement loginWithPasswordButton;
	
	@FindBy(xpath = "//button[@onclick='login_skip()']")
	private WebElement closeButtonInLoginPage;
	
	@FindBy(xpath = "//p/parent::h6/following-sibling::div/a[@href]")
	private WebElement facebookLoginButton;
	
	@FindBy(xpath = "//p/parent::h6/following-sibling::div/a[@href]")
	private WebElement googleLoginButton;
	
	public WebElement getLoginButton() {
		return loginButton;
	}

	public WebElement getMobile_usernametextField() {
		return mobile_usernametextField;
	}

	public WebElement getOTPButton() {
		return getOTPButton;
	}

	public WebElement getLoginWithPasswordButton() {
		return loginWithPasswordButton;
	}

	public WebElement getCloseButtonInLoginPage() {
		return closeButtonInLoginPage;
	}

	public WebElement getFacebookLoginButton() {
		return facebookLoginButton;
	}

	public WebElement getGoogleLoginButton() {
		return googleLoginButton;
	}

	public LoginPageLocators(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void Login(WebDriver driver, String username) {
	LoginPageLocators login = new LoginPageLocators(driver);
	login.mobile_usernametextField.sendKeys(username);
	login.getOTPButton.click();
		}
	}