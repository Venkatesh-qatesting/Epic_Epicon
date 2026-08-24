package epic_Epicon_GenericLibrary;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ListenerClass implements ITestListener, IPath {

	@Override
	public void onTestStart(ITestResult result) {
		Reporter.log("Test Started: " + result.getName(), true);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Reporter.log("Test Passed: " + result.getName(), true);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Reporter.log("Test Failed: " + result.getName(), true);
		Reporter.log("Failure Reason: " + result.getThrowable().getMessage(), true);

		// Take screenshot on failure
		Object testClass = result.getInstance();
		WebDriver driver = ((BaseClass) testClass).driver;

		if (driver != null) {
			String screenshotPath = takeScreenshot(driver, result.getName());
			Reporter.log("Screenshot saved at: " + screenshotPath, true);
			Reporter.log("<a href='" + screenshotPath + "'><img src='" + screenshotPath + "' height='200' width='300'/></a>");
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Reporter.log("Test Skipped: " + result.getName(), true);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Reporter.log("Test Failed within success percentage: " + result.getName(), true);
	}

	@Override
	public void onStart(ITestContext context) {
		Reporter.log("========== Test Suite Started: " + context.getName() + " ==========", true);
	}

	@Override
	public void onFinish(ITestContext context) {
		Reporter.log("========== Test Suite Finished: " + context.getName() + " ==========", true);
		Reporter.log("Passed Tests: " + context.getPassedTests().size(), true);
		Reporter.log("Failed Tests: " + context.getFailedTests().size(), true);
		Reporter.log("Skipped Tests: " + context.getSkippedTests().size(), true);
	}

	/**
	 * Takes a screenshot and saves it in the Screenshots folder with timestamp
	 */
	public String takeScreenshot(WebDriver driver, String testName) {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String filePath = SCREENSHOT_FOLDER_PATH + testName + "_" + timestamp + ".png";

		try {
			// Create Screenshots folder if it doesn't exist
			File screenshotDir = new File(SCREENSHOT_FOLDER_PATH);
			if (!screenshotDir.exists()) {
				screenshotDir.mkdirs();
			}

			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(filePath);
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			Reporter.log("Failed to take screenshot: " + e.getMessage(), true);
		}

		return filePath;
	}
}
