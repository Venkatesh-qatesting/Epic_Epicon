package epic_Epicon_GenericLibrary;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import org.apache.poi.EncryptedDocumentException;

import ObjectRepositories_POM.HomePage;
import ObjectRepositories_POM.LoginPage;

public class BaseClass {
	public WebDriver driver;
	File_Utilities_TestData fileUtilities = new File_Utilities_TestData();
	SeleniumUtility seleniumUtility = new SeleniumUtility();

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Connecting to the database...");
		System.out.println("Database connection established successfully.");
	}

	@BeforeTest
	public void beforeTest() throws IOException {
		System.out.println("Launching the browser...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		seleniumUtility.implicitWait(driver, 5);
		String url = fileUtilities.PropertyFileData("url");
		driver.get(url);
		System.out.println("Browser launched successfully.");
	}

	@BeforeClass
	public void beforeClass() throws EncryptedDocumentException, IOException {
		String username = fileUtilities.ExcelTestData("Sheet", 3, 0);
		String password = fileUtilities.ExcelTestData("Sheet", 3, 1);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.getLoginButton().click();
		loginPage.getMobile_usernametextField().sendKeys(username);
		loginPage.getGetOTPButton().click();
		System.out.println("Username and Password entered successfully.");
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("After Method: Test method execution completed.");
	}

	@AfterClass
	public void afterClass() {
		HomePage homePage = new HomePage(driver);
		homePage.getLogoutButton().click();
		System.out.println("After Class: User logged out successfully.");
	}

	@AfterTest
	public void afterTest() {
		if (driver != null) {
			driver.close();
			System.out.println("After Test: Browser closed.");
		}
	}

	@AfterSuite
	public void afterSuite() {
		if (driver != null) {
			driver.quit();
			System.out.println("After Suite: All browsers terminated and cleanup done.");
		}
	}
}
