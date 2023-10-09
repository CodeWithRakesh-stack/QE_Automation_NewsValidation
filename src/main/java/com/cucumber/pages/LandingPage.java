package com.cucumber.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cucumber.actions.StepActions;
import com.cucumber.base.PageContext;
import com.cucumber.base.TestContext;
import com.cucumber.exceptions.DriverException;
import com.cucumber.helper.Logger.LoggerHelper;
import com.cucumber.utils.CommonUtils;

public class LandingPage extends PageContext {

	private Logger logger = LoggerHelper.getLogger(this.getClass());

	StepActions stepActions;
	private int articleRandomNum = Integer.MIN_VALUE;

	// *******************************************************************************************
	// Constructor - initialize page elements
	// instantiate step actions to use the reusable methods to perform actions.
	// *******************************************************************************************

	public LandingPage(TestContext testcontext, StepActions stepActions) {
		super(testcontext.webDriver);
		this.stepActions = stepActions;
	}

	// Page Element to get Article name
	@FindBy(xpath = "//h3/a[@data-link-name='article']/span")
	private List<WebElement> articleNames;

	// Page Element to get click on article link
	@FindBy(xpath = "//h3/a[@data-link-name='article']")
	private List<WebElement> articleLink;

	// Page Element for cookies frame
	@FindBy(xpath = "//iframe[contains(@src, 'theguardian')]")
	private WebElement frame;

	// Page Element for browser cookie consent button
	@FindBy(xpath = "//button[contains(@title, 'happy')]")
	private WebElement cookiesAcceptBtn;
	
	// Page Element to get Headline
	@FindBy(xpath = "//a[contains(@class,'sign-in my-account')]")
	private WebElement signInWebelement;
	
	@FindBy(id ="my-account-toggle")
	private WebElement myAccountWebElement;
	
	@FindBy(xpath = "//div[text()='Account overview']")
	private WebElement accountOverviewElement;
		

	/**
	 * Function to verify broken image on landing page
	 * 
	 * @return count of brokenImage(Integer)
	 */
	public int verifyImage() {
		int brokenImageCount = 0;
		for (WebElement image : webDriver.findElements(By.cssSelector("img"))) {
			if (stepActions.isImageBroken(image)) {
				brokenImageCount++;
				logger.debug(" The URL broken in the application is :->" + image.getAttribute("src"));
			}
		}
		return brokenImageCount;
	}

	/**
	 * Function to get the page title, and also click on the cookies consent accept
	 * button in case appers.
	 * 
	 * @return page title(String)
	 * 
	 */
	public String getLandinPageTitle() {
		try {
			stepActions.waitForFrameToLoadAndSwitchToIt(frame);
			stepActions.clickElement(cookiesAcceptBtn);
		} catch (Exception e) {
			logger.info("The frame element has not been displayed." + e);
		}
		return stepActions.getPageTitle();
	}

	/**
	 * Function to get random news article name
	 * 
	 * @return article name(String)
	 */
	public String getRandomArticleName() throws DriverException {
		stepActions.waitForElementToDisplay(articleNames.get(0));
		articleRandomNum = CommonUtils.generateRandomNumber(0, articleNames.size() - 1);
		return stepActions.getElementText(articleNames.get(articleRandomNum));
	}

	/**
	 * Function to click on random article
	 * 
	 * @return article name(String)
	 */
	public void clickOnRandomArticle() {
		stepActions.clickElementByJS(articleLink.get(articleRandomNum));
	}
	
	/**
	 * Function to click on signIn link
	 * 
	 */
	public void clickOnSignInlink() {
		stepActions.clickElementByJS(signInWebelement);
	}
	
	/**
	 * Function verify my account link on landingPage
	 * @return true if displayed
	 * @throws InterruptedException 
	 */
	public boolean verifyMyAccountlink() throws InterruptedException {
		stepActions.waitForPageToLoad();
		return stepActions.isDisplayed(myAccountWebElement);
	}
	
	/**
	 * Function to click on my account link
	 * @throws DriverException
	 */
	public void clickMyAccountOverViewTablink() throws DriverException {
		try {
		stepActions.clickElementByJS(myAccountWebElement);
		stepActions.waitForElementToDisplay(accountOverviewElement);
		stepActions.clickElementByJS(accountOverviewElement);	
		} catch (Exception e) {
			throw new DriverException("Unable to click on my article link "+ e );
		}
		}
	
	}
