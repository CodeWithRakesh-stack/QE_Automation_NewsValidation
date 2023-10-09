package com.cucumber.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.utils.RunConstants;

public class TestContext {

	public WebDriver webDriver;
	public WebDriverWait webDriverWait;
	
	public void launchApplication(String autURL) {	
		webDriver.manage().window().maximize();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().timeouts().implicitlyWait(RunConstants.IMPLICIT_WAIT, TimeUnit.SECONDS);
		webDriver.get(autURL);
	}
	
	
}
