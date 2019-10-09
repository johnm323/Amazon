package com.ecom.amazon.Utilties;

import static io.appium.java_client.touch.offset.PointOption.point;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Dimension;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.touch.WaitOptions;

public class BaseClass {

//	public static ExtentReports reports;
	public static boolean initilizestatus = false;
	public static List<String> scenariolist;
	public static InputStream isConfig;
	public static WebDriver webDriver;
	public static WebElement element;
	public static List<WebElement> elements;
	public static Configuration config = null;
	public static URL url = null;
	public static int emulatorPort = 0;
	public static AndroidDriver<AndroidElement> driver;

	public static Logger Log = Logger.getLogger(BaseClass.class);

	public static AppiumDriverLocalService service;
	public static String deviceOS;
	public static String curdir;

	public static ExtentReports report;
	public ExtentTest logger;
	
	
	@BeforeSuite
	public static AndroidDriver<AndroidElement> capabilities() throws IOException, InterruptedException {

		System.getProperty("user.dir");

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\config\\application.properties");
		Properties prop = new Properties();
		prop.load(fis);

		ExcelDataProvider excel = new ExcelDataProvider();
		
		ExtentHtmlReporter extent = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + "\\Reports\\"+ currentDateTime() + ".html"));

		report= new ExtentReports();
		report.attachReporter(extent);

		
		
		startServer();

		File f = new File("App");
		File fp = new File(f, "in.amazon.mShop.android.shopping.apk");

		String device = (String) prop.get("DeviceType");
		if (device.equalsIgnoreCase("Emulator")) {

			startEmulator();
		}

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, prop.get("DEVICE_NAME"));
		cap.setCapability(MobileCapabilityType.UDID, prop.get("UDID"));
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, prop.get("PLATFORM_VERSION"));
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.get("AUTOMATION_NAME"));
		cap.setCapability(MobileCapabilityType.APP, fp.getAbsolutePath());
		cap.setCapability("appPackage", prop.get("appPackage"));
		cap.setCapability("appActivity", prop.get("appActivity"));
		cap.setCapability("noReset", prop.get("noReset"));
		cap.setCapability("unicodeKeyboard", "true");

		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		return driver;

	}

	public static void startEmulator() throws IOException, InterruptedException {

		Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\Resource\\startEmulator.bat");
		Thread.sleep(6000);
	}

	public static AppiumDriverLocalService startServer() {

		boolean flag = checkIfServerIsRunning(4723);
		if (!flag) {

			service = AppiumDriverLocalService.buildDefaultService();
			service.start();

		}
		return service;
	}

	public static boolean checkIfServerIsRunning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;

	}

	@AfterSuite
	public static void stopServer() {

		service.stop();

	}

	public static void killAppiumPort() throws IOException, InterruptedException {

		Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\Resource\\KillAppiumPort.bat");

	}

	
	@AfterMethod
	public void tearDownMethod(ITestResult result) throws IOException{
		
		if(result.getStatus()==ITestResult.SUCCESS){
			
			getScreenshot(driver);
		}
		else if
		(result.getStatus()==ITestResult.FAILURE){
			getScreenshot(driver);
		}
		else{
			System.out.println("Please check Screenshot method in BaseClass");
		}
		
		report.flush();
	}
	
	
	
	
	public static void getScreenshot(AndroidDriver driver) throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		try{
			
			FileUtils.copyFile(scrFile,
					new File(System.getProperty("user.dir") + "\\Screenshots\\" +"Amazon_"+ currentDateTime() + ".png"));
		}
		catch(IOException e){
			System.out.println("Unable to Capture Screenshot");
			
		}

	
	}

	
	public static void clickElement(String element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element))).click();

	}

	public static void enterTextAndClickEnter(String element, String Searchtext) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element)));
		search.sendKeys(Searchtext);
		// driver.pressKeyCode(AndroidKeyCode.KEYCODE_NUMPAD_ENTER );
		((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);

	}

	public static void enterText(String element, String Searchtext, String locator) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);

		if (locator.equalsIgnoreCase("xpath")) {
			WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
			search.sendKeys(Searchtext);
		} else if (locator.equalsIgnoreCase("id")) {
			WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element)));
			search.sendKeys(Searchtext);
		} else if (locator.equalsIgnoreCase("className")) {
			WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(element)));
			search.sendKeys(Searchtext);
		} else if (locator.equalsIgnoreCase("name")) {

			WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element)));
			search.sendKeys(Searchtext);
		} else {
			System.out.println("Please verify your locator and update your script");
		}
		Thread.sleep(3000);

	}

	public static void click(String element, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		if (locator.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element))).click();
		} else if (locator.equalsIgnoreCase("id")) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element))).click();
		} else if (locator.equalsIgnoreCase("className")) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className(element))).click();
		} else if (locator.equalsIgnoreCase("name")) {

			wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element))).click();
		} else {
			System.out.println("Please verify your locator and update your script");
		}
	}

	public static void scrollToElement(String element) {
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(" + element + "))");

	}

	public static void clickFromList(String element, int num) throws InterruptedException {
		Thread.sleep(3000);
		List<AndroidElement> index = driver.findElements(By.className(element));
		index.get(num).click();

	}

	public static String captureScreenshot(AndroidDriver driver) {

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\Amazon_" + currentDateTime() + ".png";

		System.out.println("Screenshot Captured Successfully");

		try {
			FileHandler.copy(src, new File(screenshotPath));
		} catch (IOException e) {

			System.out.println("Unable to capture screenshot" + e.getMessage());
		}
		return screenshotPath;
	}

	public static String currentDateTime() {

		DateFormat customformat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");

		Date currentdate = new Date();
		return customformat.format(currentdate);

	}

	public static void scrollTo(String element) {

		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + element + "\"))");

	}

	public static void scrollToElementUsing_getChildByDescription(String scrollableList, String uiSelector,
			String contentDesc) {
		MobileElement element = driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\"" + scrollableList + "\")).getChildByDescription("
						+ "new UiSelector().className(\"" + uiSelector + "\"), \"" + contentDesc + "\")"));

	}

	public static void swipedown() {
		try {
			Thread.sleep(4000);
			Dimension dimensions = driver.manage().window().getSize();
			Double screenHeightStart = dimensions.getHeight() * 0.95;
			int scrollStart = screenHeightStart.intValue();
			Double screenHeightEnd = dimensions.getHeight() * 0.10;
			int scrollEnd = screenHeightEnd.intValue();

			new TouchAction(driver).press(point(10, scrollStart))
					.waitAction(new WaitOptions().withDuration(Duration.ofMillis(900))).moveTo(point(11, scrollEnd))
					.release().perform();

			new TouchAction(driver).press(point(10, scrollStart))
					.waitAction(new WaitOptions().withDuration(Duration.ofMillis(900))).moveTo(point(11, scrollEnd))
					.release().perform();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void getText(String element, String locator, String gettextvalue){
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		if (locator.equalsIgnoreCase("xpath")) {
			gettextvalue = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element))).getText();
						
		} else if (locator.equalsIgnoreCase("id")) {
			gettextvalue = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element))).getText();
			
		} else if (locator.equalsIgnoreCase("className")) {
			gettextvalue = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(element))).getText();
		} else if (locator.equalsIgnoreCase("name")) {
			gettextvalue = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element))).getText();
		} else {
			System.out.println("Please verify your locator and update your script");
		}
		
	}
	
}
