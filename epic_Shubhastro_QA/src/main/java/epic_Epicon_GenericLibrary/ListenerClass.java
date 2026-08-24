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

public class ListenerClass implements ITestListener, IPath {

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test Started: " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Passed: " + result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed: " + result.getName());
		System.out.println("Failure Reason: " + result.getThrowable().getMessage());

		// Take screenshot on failure
		Object testClass = result.getInstance();
		WebDriver driver = ((BaseClass) testClass).driver;

		if (driver != null) {
			String screenshotPath = takeScreenshot(driver, result.getName());
			System.out.println("Screenshot saved at: " + screenshotPath);
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Skipped: " + result.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Test Failed within success percentage: " + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("========== Test Suite Started: " + context.getName() + " ==========");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("========== Test Suite Finished: " + context.getName() + " ==========");
		System.out.println("Passed Tests: " + context.getPassedTests().size());
		System.out.println("Failed Tests: " + context.getFailedTests().size());
		System.out.println("Skipped Tests: " + context.getSkippedTests().size());
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
			System.out.println("Failed to take screenshot: " + e.getMessage());
		}

		return filePath;
	}
}
