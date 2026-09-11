package ObjectRepositories_POM;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object for the "Max Device" limit page that appears when the account has
 * reached its allowed number of logged-in devices. From here the user can log
 * out the current device or log out other devices (one by one) to free a slot
 * and continue logging in.
 */
public class MaxDeviceLimitPage {

	// Title text shown on this page (read via driver.getTitle()).
	public static final String PAGE_TITLE_TEXT = "Max Device";

	// Container holding the "other devices" list; used to detect the page and to
	// locate each device's logout button.
	public static final By OTHER_DEVICES_CONTAINER = By.xpath("//div[@id='otherDevice']/child::div");

	// Logout button for each of the "other" devices (one button per device).
	public static final By OTHER_DEVICE_LOGOUT_BUTTONS = By
			.xpath("//div[@id='otherDevice']/child::div//button");

	// Logout button for the current device.
	@FindBy(xpath = "//p[.='This Device']/parent::div/following-sibling::div/child::div/child::div/button")
	private WebElement currentDeviceLogoutButton;

	// The other-devices container (used as a visible detector for this page).
	@FindBy(xpath = "//div[@id='otherDevice']/child::div")
	private WebElement otherDevicesContainer;

	public WebElement getCurrentDeviceLogoutButton() {
		return currentDeviceLogoutButton;
	}

	public WebElement getOtherDevicesContainer() {
		return otherDevicesContainer;
	}

	/**
	 * Returns the list of logout buttons for the "other" devices. Iterate and
	 * click each one to log out from all other devices.
	 */
	public List<WebElement> getOtherDeviceLogoutButtons(WebDriver driver) {
		return driver.findElements(OTHER_DEVICE_LOGOUT_BUTTONS);
	}

	public MaxDeviceLimitPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
}
