package ObjectRepositories_POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	@FindBy(xpath = "//a[@aria-label='Logout']")
	private WebElement logoutButton;

	public WebElement getLogoutButton() {
		return logoutButton;
	}

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void logout(WebDriver driver) {
		HomePage homePage = new HomePage(driver);
		homePage.getLogoutButton().click();
	}
}
