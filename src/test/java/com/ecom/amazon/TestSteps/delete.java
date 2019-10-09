package com.ecom.amazon.TestSteps;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class delete {

	public static AndroidDriver driver;
	
	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub

		DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus 5X API 24");
		cap.setCapability(MobileCapabilityType.UDID, "emulator-5554");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7");
	cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
	cap.setCapability("appPackage", "in.amazon.mShop.android.shopping");
	cap.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
		
	cap.setCapability(MobileCapabilityType.APP, "C:\\Users\\johnjoseph_m\\workspace\\amazon\\App\\in.amazon.mShop.android.shopping.apk");
	driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);

	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	
	driver.findElement(By.id("in.amazon.mShop.android.shopping:id/skip_sign_in_button")).click();
	
	}

}
