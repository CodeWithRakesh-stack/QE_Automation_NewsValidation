package com.cucumber.steps.common;

import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.picocontainer.annotations.Inject;

import com.cucumber.exceptions.DriverException;
import com.cucumber.helper.Logger.LoggerHelper;
import com.cucumber.pages.common.CommonPage;
import com.cucumber.steps.LandingPageSteps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonPageStep {
	
	@Inject
	CommonPage commonPage;
	
	private String articleName = LandingPageSteps.articleName;

	private Logger logger = LoggerHelper.getLogger(LandingPageSteps.class);

    
	@When("I have opened a web browser and search with noted article name")
	public void i_have_opened_a_web_browser_and_search_with_noted_article_name() throws DriverException {
		try {
			commonPage.launchTheGooogleUrlAndSearchWithArticleName(articleName);
		} catch (Exception e) {
			logger.error("Unable to search article on search bar");
			throw new DriverException("Unable to search article on search bar :->"+e);
		}
	}
		
	@When("I click on news tab and check news tab should be loadded")
	public void i_click_on_news_tab() {
	  assertTrue(commonPage.clickOnNewsTabAndVerifyTabLoaded(), "Unable to click on News tab");
		
	}
	@Then("I should see the noted article news in {int} diffrent website and percentage of match should be above {int}")
	public void i_should_see_news_page_related_noted_article_name(int matchCount, int percentage) throws DriverException {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(commonPage.checkSearchResultAndVerifySimilarlty
				(articleName, matchCount, percentage), "Unable to find the same news on two diffrent webiste");
	}

}
