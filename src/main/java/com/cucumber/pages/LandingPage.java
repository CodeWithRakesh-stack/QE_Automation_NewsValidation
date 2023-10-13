package com.cucumber.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
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
		
	@FindBy(xpath = "//*[contains(@href, 'free-news-email')]")
	private WebElement signupForOurEmailElement;
	
	@FindBy(xpath = "//*[text()='Sign up']")
	private WebElement signupBtnElement;
	
	@FindBy(xpath ="//input[@type='email']")
	private WebElement emailInputElement;
	
	@FindBy(xpath="//*[contains(text(),'Subscription Confirmed')]")
	private WebElement subscriptionConfirmedElement;
	
	@FindBy(xpath="//*[contains(@href,'https://twitter.com/intent/tweet?text=Sign+up')]")
	private WebElement twitterSharingIconEle;
	
	@FindBy(xpath="//*[contains(@href,'https://www.facebook.com/dialog/share')]")
	private WebElement facebookSharingIconEle;
	
	@FindBy(xpath="//*[text()='Sign up for the First Edition newsletter: our free daily news email']")
	private WebElement signUpPageHeaderTitle;
	
	@FindBy(xpath="//iframe[@title='Sign up to First Edition']")
	private WebElement signupFrame;
	
	@FindBy(xpath="//iframe[@title='recaptcha challenge expires in two minutes']")
	private WebElement recapchaFrameEle;
	
	@FindBy(xpath ="//div[contains(@class,'help-button-holder')]")
	private WebElement shadowRootElement;
	
	/**
	 * Function to verify broken image on landing page
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
	 * button in case appears
	 * 
	 * @return page title(String)
	 * 
	 */
	public String getLandinPageTitle() {
		stepActions.handleCookiesPopup();
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
	
	

	/**
	 * Function to click on Sign up button 
	 */
	public void clickOnSignUpButtonForSubscribingOurNewsChannl() {
		System.out.println(signupForOurEmailElement.isDisplayed());
		stepActions.clickElement(signupForOurEmailElement);
	}
	
	
	/**
	 * Function to check if Signup button display on First Edition email section
	 * @return true if displayed
	 */
	public boolean verifySignupBtnForTheFirstEditionEmailPage() {
		webDriver.switchTo().defaultContent();
		System.out.println(signUpPageHeaderTitle.isDisplayed());
		System.out.print(stepActions.waitForElementToDisplay(signUpPageHeaderTitle));
		return stepActions.waitForElementToDisplay(signUpPageHeaderTitle);
	} 
	
	/**
	 * Function to enter email address and click on sign up button
	 * @param emailAddress
	 */
	public void enterEmailAddressAndClickOnSignUpButton(String emailAddress) {
		stepActions.waitForFrameToLoadAndSwitchToIt(signupFrame);
		stepActions.setDataInTextBox(emailInputElement, emailAddress);
		stepActions.clickElementByJS(signupBtnElement);
		stepActions.handleCapchaInCaseAppears();
	}
	
	
	/**
	 * Function to verify Subscription Succecs Message
	 * @return
	 */
	public boolean verifySubscriptionConfirmedSuccecsMessage() {
		webDriver.switchTo().defaultContent();
		if(signupFrame.isDisplayed()) {
			System.out.println(subscriptionConfirmedElement.isDisplayed());
			stepActions.waitForFrameToLoadAndSwitchToIt(signupFrame);
		}
		logger.info("Subcription Succes Message is displayed :->"+subscriptionConfirmedElement.isDisplayed());
		return stepActions.waitForElementToDisplay(subscriptionConfirmedElement);
	}
	
	/**
	 * Function to click twitter icon
	 */
	public void clickOnTwittericon() {
		stepActions.clickElementByJS(twitterSharingIconEle);
	}
	
	/**
	 * Function to click on facebook icon
	 */
	public void clickOnFacebookIcon() {
		stepActions.clickElementByJS(facebookSharingIconEle);
	}
}