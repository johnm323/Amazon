package com.ecom.amazon.TestSteps;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import java.io.IOException;

import com.aventstack.extentreports.ExtentTest;
import com.ecom.amazon.Objects.HomeScreenObjects;
import com.ecom.amazon.Objects.ProductDescriptionScreenObject;
import com.ecom.amazon.Objects.SearchScreenObject;
import com.ecom.amazon.Objects.SignInScreenObjects;
import com.ecom.amazon.Objects.WelcomeLoginScreenObjects;
import com.ecom.amazon.Utilties.BaseClass;
import com.ecom.amazon.Utilties.ExcelDataProvider;

public class SignInScreenTestSteps extends BaseClass {

	ExcelDataProvider excel = new ExcelDataProvider();

	@Test(priority = 1)
	public void TapOnSkipButton() {

		logger = report.createTest("Verify Skip button and Tap");

		try {
			click(SignInScreenObjects.SkipSignInButton, "id");
		} catch (Exception e) {
			System.out.println("element not found" + e.getMessage());

		}

	}

	@Test(priority = 2)
	public void ClickOnNoThanksButton() {

		logger = report.createTest("Verify No Thanks Button and Tap");

		try {
			click(HomeScreenObjects.NoThanks, "id");
		} catch (Exception e) {
			System.out.println("element not found" + e.getMessage());

		}

	}

	@Test(priority = 3)
	public void ProductSearch() {

		logger = report.createTest("Verify Searchbox and Search Product");

		click(HomeScreenObjects.SearchBarPlate, "id");
		try {
			enterTextAndClickEnter(SearchScreenObject.SearchTextField, excel.getStringData(0, 0, 1));
		} catch (Exception e) {
			System.out.println("Unable to find element" + e.getMessage());

		}

	}

	@Test(priority = 4)
	public void ScrollAndSelectProduct() {

		logger = report.createTest("Scroll and Tap on Correct Product");

		scrollTo(SearchScreenObject.ScrollAndChooseTV);
		try {
			click(SearchScreenObject.SelectTV, "xpath");
		} catch (Exception e) {
			System.out.println("Unable to find element" + e.getMessage());

		}

	}

	@Test(priority = 5)
	public void SwipeAndClickOnBuyNow() {

		logger = report.createTest("Verify Buy Button and Tap");

		swipedown();
		try {
			clickFromList(ProductDescriptionScreenObject.AddToCard, 0);
		} catch (Exception e) {
			System.out.println("Unable to find element" + e.getMessage());

		}

	}

	@Test(priority = 6)
	public void EnterUserName() {

		logger = report.createTest("Verify User Name field and Enter UserName");
		click(WelcomeLoginScreenObjects.EnterUserName, "xpath");

		try {
			Thread.sleep(3000);
			enterText(WelcomeLoginScreenObjects.EnterUserName, excel.getStringData(0, 1, 1), "xpath");
		} catch (Exception e) {
			System.out.println("Unable to find element" + e.getMessage());

		}
	}

	@Test(priority = 7)
	public void ClickOnContinueButton() {
		logger = report.createTest("Verify Continue Button and tap");

		try {
			click(WelcomeLoginScreenObjects.ContinueButton, "xpath");
		} catch (Exception e) {
			System.out.println("Unable to find element" + e.getMessage());

		}
	}

	@Test(priority = 8)
	public void EnterPassword() {
		logger = report.createTest("Verify Password field and Enter Password");
		try {
			clickFromList(WelcomeLoginScreenObjects.EnterPassword, 0);
			enterText(WelcomeLoginScreenObjects.EnterPassword, excel.getStringData(0, 2, 1), "className");
		} catch (Exception e) {
			System.out.println("Unable to find element" + e.getMessage());

		}

	}

	@Test(priority = 9)
	public void ClickOnLoginButton() {
		logger = report.createTest("Verify Login Button and tap");
		try {
			clickFromList(WelcomeLoginScreenObjects.LoginButton, 0);
		} catch (Exception e) {
			System.out.println("Unable to find element" + e.getMessage());

		}

	}

	@Test(priority = 10)
	public void VerifyProduct() {
		logger = report.createTest("Verify Expected Product with Actual Product");

		Assert.assertEquals(SearchScreenObject.ProductText, excel.getStringData(0, 3, 1));

	}

}
