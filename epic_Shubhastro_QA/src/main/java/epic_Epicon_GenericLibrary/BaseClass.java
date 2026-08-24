package epic_Epicon_GenericLibrary;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass implements IPath {
	public WebDriver driver;
	protected File_Utilities_TestData fileUtilities = new File_Utilities_TestData();
	protected SeleniumUtility seleniumUtility = new SeleniumUtility();

	@BeforeSuite
	public void beforeSuite() {
		Reporter.log("Connecting to the database...", true);
		Reporter.log("Database connection established successfully.", true);
	}

	@BeforeTest
	public void beforeTest() throws IOException {
		Reporter.log("Launching the browser...", true);
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		seleniumUtility.implicitWait(driver, 5);
		String url = fileUtilities.PropertyFileData("url");
		driver.get(url);
		Reporter.log("Browser launched successfully.", true);
	}

	@BeforeMethod
	public void beforeMethod() {
		Reporter.log("Before Method: Preparing for test execution.", true);
	}

	@AfterMethod
	public void afterMethod() {
		Reporter.log("After Method: Test method execution completed.", true);
	}

	@AfterTest
	public void afterTest() {
		if (driver != null) {
			driver.close();
			Reporter.log("After Test: Browser closed.", true);
		}
	}

	@AfterSuite
	public void afterSuite() {
		if (driver != null) {
			driver.quit();
			Reporter.log("After Suite: All browsers terminated and cleanup done.", true);
		}
	}

	/**
	 * DataProvider that reads all rows from the "Login" sheet in Excel.
	 * Row 0 = header (skipped), Row 1 onwards = test data.
	 * Each row becomes one test iteration.
	 */
	@DataProvider(name = "loginData")
	public Object[][] getLoginData() throws EncryptedDocumentException, IOException {
		return fileUtilities.getExcelDataForDataProvider("Login");
	}
}
