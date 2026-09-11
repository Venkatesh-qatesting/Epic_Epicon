package epic_Epicon_GenericLibrary;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtility {

	// ==================== WAITS ====================

	/**
	 * Sets implicit wait for the driver
	 */
	public void implicitWait(WebDriver driver, int seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
	}

	/**
	 * Explicit wait - wait until element is visible
	 */
	public WebElement explicitWaitForVisibility(WebDriver driver, WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Explicit wait - wait until element is clickable
	 */
	public WebElement explicitWaitForClickable(WebDriver driver, WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Explicit wait - wait until title contains the given text
	 */
	public void explicitWaitForTitle(WebDriver driver, String title, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.titleContains(title));
	}

	/**
	 * Explicit wait - wait until an element located by the given locator is
	 * present in the DOM (does not require visibility or clickability).
	 */
	public WebElement explicitWaitForPresence(WebDriver driver, org.openqa.selenium.By locator, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * Non-throwing check for whether at least one element matching the locator is
	 * present in the DOM within the given time. Returns true/false instead of
	 * throwing a TimeoutException, so it is safe for optional/conditional pages.
	 */
	public boolean isElementPresent(WebDriver driver, org.openqa.selenium.By locator, int seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (org.openqa.selenium.TimeoutException e) {
			return false;
		}
	}

	// ==================== MOUSE ACTIONS ====================

	/**
	 * Mouse hover on an element
	 */
	public void mouseHover(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	/**
	 * Double click on an element
	 */
	public void doubleClick(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.doubleClick(element).perform();
	}

	/**
	 * Right click (context click) on an element
	 */
	public void rightClick(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.contextClick(element).perform();
	}

	/**
	 * Drag and drop from source to target
	 */
	public void dragAndDrop(WebDriver driver, WebElement source, WebElement target) {
		Actions actions = new Actions(driver);
		actions.dragAndDrop(source, target).perform();
	}

	/**
	 * Click and hold on an element
	 */
	public void clickAndHold(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.clickAndHold(element).perform();
	}

	// ==================== DROPDOWN HANDLING ====================

	/**
	 * Select dropdown option by visible text
	 */
	public void selectByVisibleText(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);
	}

	/**
	 * Select dropdown option by value attribute
	 */
	public void selectByValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	/**
	 * Select dropdown option by index
	 */
	public void selectByIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	/**
	 * Get all options from a dropdown
	 */
	public List<WebElement> getAllOptions(WebElement element) {
		Select select = new Select(element);
		return select.getOptions();
	}

	/**
	 * Get first selected option from dropdown
	 */
	public String getSelectedOption(WebElement element) {
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	// ==================== ALERTS ====================

	/**
	 * Accept an alert (click OK)
	 */
	public void acceptAlert(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	/**
	 * Dismiss an alert (click Cancel)
	 */
	public void dismissAlert(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	/**
	 * Get alert text
	 */
	public String getAlertText(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

	/**
	 * Send text to alert prompt
	 */
	public void sendTextToAlert(WebDriver driver, String text) {
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(text);
		alert.accept();
	}

	// ==================== FRAMES ====================

	/**
	 * Switch to frame by index
	 */
	public void switchToFrameByIndex(WebDriver driver, int index) {
		driver.switchTo().frame(index);
	}

	/**
	 * Switch to frame by name or ID
	 */
	public void switchToFrameByNameOrId(WebDriver driver, String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	/**
	 * Switch to frame by WebElement
	 */
	public void switchToFrameByElement(WebDriver driver, WebElement element) {
		driver.switchTo().frame(element);
	}

	/**
	 * Switch back to default content from frame
	 */
	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	// ==================== WINDOWS ====================

	/**
	 * Switch to new window/tab
	 */
	public void switchToWindow(WebDriver driver, String windowHandle) {
		driver.switchTo().window(windowHandle);
	}

	/**
	 * Get current window handle
	 */
	public String getWindowHandle(WebDriver driver) {
		return driver.getWindowHandle();
	}

	/**
	 * Get all window handles
	 */
	public java.util.Set<String> getAllWindowHandles(WebDriver driver) {
		return driver.getWindowHandles();
	}

	// ==================== JAVASCRIPT EXECUTOR ====================

	/**
	 * Scroll to an element using JavaScript
	 */
	public void scrollToElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * Click element using JavaScript (useful when normal click doesn't work)
	 */
	public void jsClick(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	/**
	 * Scroll page to bottom
	 */
	public void scrollToBottom(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	/**
	 * Scroll page to top
	 */
	public void scrollToTop(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
	}

	/**
	 * Highlight an element (useful for debugging)
	 */
	public void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	// ==================== SCREENSHOTS ====================

	/**
	 * Take screenshot and return as file
	 */
	public java.io.File takeScreenshot(WebDriver driver) {
		org.openqa.selenium.TakesScreenshot ts = (org.openqa.selenium.TakesScreenshot) driver;
		return ts.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
	}
}
