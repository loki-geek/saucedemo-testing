package com.testing.utils;

import com.testing.testcases.*;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportingSD implements ITestListener {
	//ExtentReports is used along with TestNG Listeners
	public static ExtentReports extentReport;
	public static ExtentSparkReporter sparkReport;
	public static ExtentTest extentTest;
	public static String exceptn ="";
	
	public void onTestStart(ITestResult result) {
		//not implemented
	}

	/**
	 * Invoked each time a test succeeds.
	 *
	 * @param result <code>ITestResult</code> containing information about the run
	 *               test
	 * @see ITestResult#SUCCESS
	 */
	public void onTestSuccess(ITestResult result) {
		
		//logging the test pass info to the report.
		extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
		
	}

	/**
	 * Invoked each time a test fails.
	 *
	 * @param result <code>ITestResult</code> containing information about the run
	 *               test
	 * @see ITestResult#FAILURE
	 */
	public void onTestFailure(ITestResult result) {
		//logging the test failure info to the extentReport.
		if(!exceptn.isBlank()) {
			extentTest.log(Status.FAIL, "TestMethodName: \""+result.getName()+"\" | Exception thrown: \""+exceptn+"\"");
		}else {
		extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
		}
		//attaching screenshot to extentReport.
		extentTest.addScreenCaptureFromPath(BaseClassSD.screenShot(result.getName()));
		
	}
	

	public void onTestSkipped(ITestResult result) {
		// not implemented
	}

	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not implemented
	}

	
	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}

	/**
	 * Invoked before running all the test methods belonging to the classes inside
	 * the &lt;test&gt; tag and calling all their Configuration methods.
	 *
	 * @param context The test context
	 */
	public void onStart(ITestContext context) {
		//On start of the execution, object is created for ExtentReports and attached with sparkReporter.
		extentReport = new ExtentReports();
		sparkReport = new ExtentSparkReporter(
				System.getProperty("user.dir") + "/test-output/Extent Reports/Extentreports.html");
		extentReport.attachReporter(sparkReport);
		//systemInfo and config values are set.
		extentReport.setSystemInfo("Hostname", "local host");
		extentReport.setSystemInfo("Environment", "QA");
		extentReport.setSystemInfo("User", "Logesh");

		sparkReport.config().setDocumentTitle("SauceDemo Testing");
		sparkReport.config().setReportName("Functional Test Report");
		sparkReport.config().setTheme(Theme.DARK);
	}

	/**
	 * Invoked after all the test methods belonging to the classes inside the
	 * &lt;test&gt; tag have run and all their Configuration methods have been
	 * called.
	 *
	 * @param context The test context
	 */
	public void onFinish(ITestContext context) {
		extentReport.flush();
	}

}
