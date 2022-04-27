package testAutomationListner;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import resources.baseClass.BaseClass;

import java.io.IOException;

public class ITestListenerImpl extends BaseClass implements ITestListener
{
	ExtentTest testcase;

	public void onTestStart(ITestResult result) {

	}

	public void onTestSuccess(ITestResult result) {
		Log.info("PASS");
	}

	public void onTestFailure(ITestResult result) {

		testcase.log(Status.FAIL, "Test is failed");
		Log.error("Test is failed");
//		extent.flush();
		ExtentReportListener extentReportListener=new ExtentReportListener();
		try {
			extentReportListener.captureScreenShot(driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver.quit();
	}

	public void onTestSkipped(ITestResult result) {

		testcase.log(Status.SKIP, "Test is skipped");
		System.out.println("SKIP");

	}


	public void onStart(ITestContext context) {
		System.out.println("Execution started....");
		extent= setUp();
	}

	public void onFinish(ITestContext context) {
		System.out.println("Execution completed...");
		extent.flush();		
		System.out.println("Generated Report. . .");	
		
	}
	

}
