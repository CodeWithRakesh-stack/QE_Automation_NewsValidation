package com.cucumber.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.cucumber.base.BrowserManager;
import com.cucumber.base.TestContext;
import com.cucumber.utils.CommonUtils;
import com.cucumber.utils.RunConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class ScenarioHooks {

	TestContext testContext;

	public ScenarioHooks(TestContext testContext) {
		this.testContext = testContext;
	}

	@Before
	public void scenarioSetup() {
		System.out.println();
		BrowserManager browserManager = new BrowserManager();
		this.testContext.webDriver = browserManager.getDriver();
		this.testContext.launchApplication(CommonUtils.getConfigProperty("url", RunConstants.RUN_CONFIG_LOCATION));
	}

	@After
	public void scenarioTearDown(Scenario scenario) {

		if (scenario.isFailed()) {

			File screenshotsDir = Paths.get(System.getProperty("user.dir"), "screenshots").toFile();

			if (!screenshotsDir.exists()) {
				screenshotsDir.mkdir();
			}

			TakesScreenshot takesScreenshot = (TakesScreenshot) this.testContext.webDriver;
			File screenshotAs = takesScreenshot.getScreenshotAs(OutputType.FILE);
			File destinationFile = new File(screenshotsDir, scenario.getId() + ".png");

			scenario.attach(takesScreenshot.getScreenshotAs(OutputType.BYTES), "image/png", "Screenshot");

			try (FileInputStream screenShotFileStream = new FileInputStream(screenshotAs);
					FileOutputStream destinationFileStream = new FileOutputStream(destinationFile);
					FileChannel screenShotReadChannel = screenShotFileStream.getChannel();
					FileChannel screenShotDestChannel = destinationFileStream.getChannel()) {
				screenShotDestChannel.transferFrom(screenShotReadChannel, 0, screenShotReadChannel.size());
			} catch (Exception e) {
				System.out.println("An error has occurred on save screenshot!");
			}
		}
		this.testContext.webDriver.quit();
	}

	@BeforeAll
	public static void deleteScreenshotFolder() throws Exception {
		File screenshotFolder = new File("screenshots");
		if (screenshotFolder.exists()) {
			FileUtils.deleteDirectory(screenshotFolder);
		}
	}
}