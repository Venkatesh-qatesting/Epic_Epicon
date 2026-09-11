package ObjectRepositories_POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	// Reusable locator for the Profile icon (used with presence-based waits).
	public static final By PROFILE_ICON = By.xpath("//a[@aria-label='Profile']/span[@class='side-rail__icon']");

	@FindBy(xpath = "//div[@class='as-logout-section']/child::a//span")
	private WebElement logoutButton;

	@FindBy(xpath = "//a[@aria-label='Profile']/span[@class='side-rail__icon']")
	private WebElement profileIcon;

	public WebElement getprofileIcon() {
		return profileIcon;
	}

	public WebElement getLogoutButton() {
		return logoutButton;
	}

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void logout(WebDriver driver) {
		HomePage homePage = new HomePage(driver);
		homePage.getLogoutButton().click();
		homePage.getprofileIcon().click();
	}
}
