package com.cucumber.actions;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.base.TestContext;
import com.cucumber.helper.Logger.LoggerHelper;
import com.cucumber.utils.RunConstants;

public class StepActions {

	private final WebDriver webDriver;
	private WebDriverWait wait;
	
	private Logger logger = LoggerHelper.getLogger(StepActions.class);

	public StepActions(TestContext testContext) {
		this.webDriver = testContext.webDriver;
	}

	public void launchApplication(String autURL) {
		
		webDriver.manage().window().maximize();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().timeouts().implicitlyWait(RunConstants.IMPLICIT_WAIT, TimeUnit.SECONDS);
		webDriver.get(autURL);
		waitForPageToLoad();
	}
	
	public void waitForFrameToLoadAndSwitchToIt(WebElement element) {
		wait = new WebDriverWait(webDriver,
				RunConstants.FRAME_WAIT_TIME);
		wait.until(ExpectedConditions
				.frameToBeAvailableAndSwitchToIt(element));
	}

	
	public String getPageTitle() {
		return webDriver.getTitle();
	}
	/*
	 * Method wait for page load
	 * 
	 */
	public void waitForPageToLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		wait = new WebDriverWait(webDriver,
				RunConstants.WEB_DRIVER_WAIT);
		wait.until(pageLoadCondition);
	}
	
	
	/**
	 * Wait for element to display
	 * @param WebElement
	 */
	public boolean waitForElementToDisplay(WebElement element) {
		try {		
		wait = new WebDriverWait(webDriver,
		RunConstants.WEB_DRIVER_WAIT);
		wait.until(ExpectedConditions.visibilityOf(element));
		return true;
		}catch (Exception e) {
			logger.error("Webelement not loadded in page"+e);
			return false;
		}
	}

	
	/*
	 * Return boolean in case element displayed or else throw WebDriverException
	 * @param WebElement
	 */

	public boolean isDisplayed(WebElement we) throws InterruptedException {
		Thread.sleep(5000);
		if (we == null) {
			throw new WebDriverException("WebElement is null");
		}
		return we.isDisplayed();
	}
	
	/*
	 * Return boolean in case element Enabled or else throw WebDriverException
	 * @param WebElement
	 */
	public boolean isEnabled(WebElement we) {
		if (we == null) {
			throw new WebDriverException("WebElement is null");
		}
		return we.isEnabled();
	}

	/*
	 * Click the element
	 * @param WebElement
	 */
	public void clickElement(WebElement webElement) {
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
		waitForElementToDisplay(webElement);
		webElement.click();
	}
	/*
	 * Get the element text
	 * @param WebElement
	 */
	public String getElementText(WebElement webElement) {
		waitForElementToDisplay(webElement);
		return webElement.getText().trim();
	}
	
	/*
	 * Refresh the page
	 * 
	 */
	public void RefreshThePage() {
		webDriver.navigate().refresh();
	}
	
	/*
	 * Method to pass data in text box
	 * @param WebElement
	 * @param String data
	 */
	public void setDataInTextBox(WebElement webElement, String testData) {
		waitForElementToDisplay(webElement);
		webElement.sendKeys(testData);
	}
	
	/*
	 * Method to scroll till the element
	 * @param WebElement
	 * 
	 */
	public void scrollBy(WebElement element) {
			((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", element);

	}

	/*
	 * Method to scroll till the element
	 * @param WebElement
	 */
	public void clickElementByJS(WebElement webElement) {
		waitForElementToDisplay(webElement);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", webElement);
	}
	
	
	public boolean isImageBroken(WebElement image)
	{
	    if (image.getAttribute("naturalWidth").equals("0"))
	    {
	        System.out.println(image.getAttribute("outerHTML") + " is broken.");
	        return true;
	    }
	    return false;
	}
	

	public void scrollPageWithoutScrollViewer(WebElement element, String option) {
		JavascriptExecutor js;
		if (option.equalsIgnoreCase("Down")) {
			js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);

		} else if (option.equalsIgnoreCase("UP")) {
			js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].scrollIntoView(false);", element);
		}
	}

	public void scrollPageWithScrollViewer(String option) {
		Actions action;

		if (option.equalsIgnoreCase("Down")) {
			action = new Actions(webDriver);
			action.sendKeys(Keys.PAGE_DOWN).build().perform();
		} else if (option.equalsIgnoreCase("Up")) {
			action = new Actions(webDriver);
			action.sendKeys(Keys.PAGE_UP).build().perform();
		}
	}

}
