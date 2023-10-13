package com.cucumber.pages.common;

import java.util.List;


import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cucumber.actions.StepActions;
import com.cucumber.base.PageContext;
import com.cucumber.base.TestContext;
import com.cucumber.exceptions.DriverException;
import com.cucumber.helper.Logger.LoggerHelper;
import com.cucumber.utils.CommonUtils;
import com.cucumber.utils.RunConstants;


public class CommonPage extends PageContext{
	private Logger logger = LoggerHelper.getLogger(this.getClass());

	
	StepActions stepActions;
	public CommonPage(TestContext testcontext, StepActions stepActions) {
		super(testcontext.webDriver);
		this.stepActions = stepActions;
	}
	
	@FindBy(name = "q")
	WebElement googleSearchBarElement;
	
	@FindBy(xpath = "//div[@role='navigation']//*[text()='News']")
	WebElement newsTabElement;
	
	@FindBy(xpath = "//div[@aria-current='page']//*[text()='News']")
	WebElement newsTabLoaddedConfElement;
	
	@FindBy(xpath = "//a[not(contains(@href,'https://www.theguardian.com'))]//div[@role='heading']")
	List<WebElement> searchResultText;
	
	@FindBy(xpath = "//a[not(contains(@href,'https://www.theguardian.com'))]//div[@role='heading']//ancestor::a")
	List<WebElement> searchResultClickElement;
	
	@FindBy(xpath= "//a[contains(@aria-label,'Page')]")
	List<WebElement> nextPageClickElement;
	
	@FindBy(xpath ="//p[@role='heading'][contains(text(),'Your search')]")
	WebElement emptySearchResult;
	
	
	/**
	 * Function to Search article name on google browser
	 * @param articleName
	 */
	public void launchTheGooogleUrlAndSearchWithArticleName(String articleName) {
		webDriver.manage().deleteAllCookies();
		webDriver.get(CommonUtils.getConfigProperty("google_url", RunConstants.RUN_CONFIG_LOCATION));
		stepActions.waitForPageToLoad();
		stepActions.waitForElementToDisplay(googleSearchBarElement);
		stepActions.setDataInTextBox(googleSearchBarElement, articleName+" -theguardian");
		googleSearchBarElement.sendKeys(Keys.ENTER);
		
	}
	
	/**Function to click on News tab on search result page
	 * @return boolean if News tab loaded
	 */
	public boolean clickOnNewsTabAndVerifyTabLoaded() {
		stepActions.clickElementByJS(newsTabElement);
		return stepActions.waitForElementToDisplay(newsTabElement);
	}
	
	public boolean clickOnLinkAndGetTheTextMatchingScore(int index, String articleName, int percentage) throws DriverException {
		String currentURL = webDriver.getCurrentUrl();
		try {
			logger.info("Click on link to check the score in article details page");
			stepActions.clickElementByJS(searchResultClickElement.get(index));
			//searchResultText.get(count).findElement(By.xpath("//ancestor::a")).click();
			stepActions.waitForPageToLoad();
			String textInPage = CommonUtils.getTextFromPageSource(webDriver.getPageSource());
			int score = CommonUtils.getTextAnalysisScore(articleName, textInPage);
			if(currentURL!=webDriver.getCurrentUrl()) {
				webDriver.navigate().back();
				stepActions.waitForPageToLoad();
			}
			logger.info("Article details page score percetnage is->"+score+ " against earlier noted news." );
			return score>=percentage;
		} catch (Exception e) {
			logger.info("Unable to get the match score by clicking on link");
			if(currentURL!=webDriver.getCurrentUrl()) {
				webDriver.navigate().back();
				stepActions.waitForPageToLoad();
			}
		}
		return false;
		}
	
	/**
	 * Function to validate noted article news against other news site
	 * @param articleName
	 * @param percentage
	 * @param matchCount
	 * @return boolean if article matches with other news site
	 * @throws DriverException
	 */
	public boolean checkSearchResultAndVerifySimilarlty(String articleName, int matchCount, int percentage) throws DriverException {		int count=0;
		int textMatchCount=0;
		int pageCheckCount =0;
		try {
		while(textMatchCount!=2 || pageCheckCount!=3) {
			pageCheckCount++;
			//Check if search result page displayed,if not the break the while loop
			if(!stepActions.waitForElementToDisplay(searchResultText.get(0))){
				if(stepActions.isDisplayed(emptySearchResult)) { pageCheckCount =3; break;}
			}
			for(count=0;count<searchResultText.size();count++) {
				String result = stepActions.getElementText(searchResultText.get(count));
				logger.info(count+1+" link scoring percentage is :->" +CommonUtils.getTextAnalysisScore(articleName, result));
				//Checking each search result data, if data match to percentage increase the count of match
				if(CommonUtils.getTextAnalysisScore(articleName, result)>=percentage) {
					textMatchCount++;
				}
				//else click on link and try to get the match in article details page
				else {
					if(clickOnLinkAndGetTheTextMatchingScore(count, articleName, percentage)) {
						textMatchCount++;
					}}
				
				if(textMatchCount==2) return true;
				}
				
			if(nextPageClickElement.size()==0)  break;
			logger.info("Unable to get the match in current page, clicking on next page");
			stepActions.clickElement(nextPageClickElement.get(pageCheckCount-1));
			stepActions.waitForPageToLoad();}
		}catch (Exception e) {
			throw new DriverException("Unable to perform similarty check with other website :->"+e);
		}
		return false;
		}
	
	public boolean verifyNewSharingPageLoadded(String url) {
		stepActions.switchToNewTab();
		return webDriver.getCurrentUrl().contains(url);
	}

}


