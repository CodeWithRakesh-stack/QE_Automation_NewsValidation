package com.cucumber.base;


import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.cucumber.utils.CommonUtils;
import com.cucumber.utils.RunConstants;
import com.ep.cucumber.base.TestContext;

public class BrowserManager extends TestContext {

	public WebDriver getDriver() {

		String browserName = CommonUtils.getConfigProperty("browserName",
		RunConstants.RUN_CONFIG_LOCATION);
		String browserRunMode = CommonUtils.getConfigProperty("browserRunMode", RunConstants.RUN_CONFIG_LOCATION);
		
		switch (browserName.toLowerCase()) {

		case "chrome":
	        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");

			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--incognito");
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.addExtensions(new File("src/main/resources/extension/Capcha_Handler.crx"));
			if (browserRunMode.equalsIgnoreCase("headless"))
				chromeOptions.addArguments("--headless");
			return new ChromeDriver(chromeOptions);
	
			
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();

				firefoxOptions.addArguments("--private");
				if (browserRunMode.equalsIgnoreCase("headless"))
					firefoxOptions.addArguments("--headless");
			return new FirefoxDriver(firefoxOptions);
				
		}
		return webDriver;
		}

}