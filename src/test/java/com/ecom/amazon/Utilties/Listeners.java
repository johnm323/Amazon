package com.ecom.amazon.Utilties;

import com.ecom.amazon.Utilties.BaseClass;

import io.appium.java_client.android.AndroidDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {

	AndroidDriver driver;

	@Override
	public void onFinish(ITestContext result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		String s = result.getName();
		try {
			BaseClass.getScreenshot(driver);
		} catch (Exception e) {
			System.out.println("Test Case Failed" + e.getMessage());
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

		String s = result.getName();
		try {
			BaseClass.getScreenshot(driver);
		} catch (Exception e) {
			System.out.println("Test Case Passed" + e.getMessage());
		}

	}

}
