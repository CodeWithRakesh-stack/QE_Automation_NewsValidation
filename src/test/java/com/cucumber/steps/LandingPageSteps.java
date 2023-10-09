package com.cucumber.steps;

import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.picocontainer.annotations.Inject;
import org.testng.Assert;

import com.cucumber.exceptions.DriverException;
import com.cucumber.helper.Logger.LoggerHelper;
import com.cucumber.pages.ArticleDetailsPage;
import com.cucumber.pages.LandingPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LandingPageSteps {

	private Logger logger = LoggerHelper.getLogger(LandingPageSteps.class);

	@Inject
	LandingPage landingPage;
	
	@Inject
	ArticleDetailsPage ariticleDetailsPage;
	
	public static String articleName = null;
	
	@Given("I am on the {string} landing page")
	public void i_am_on_the_lading_page(String titleName) {
	    assertTrue(landingPage.getLandinPageTitle().contains(titleName), "Unable to load landing page");
	}
	
	@When("I click on any article link")
	public void click_on_any_article_link() throws DriverException {
		try {
			articleName = landingPage.getRandomArticleName();
			landingPage.clickOnRandomArticle();
			logger.info("Clicked on article link");
		} catch (Exception e) {
			logger.error("Unable to click on article link "+e);
			throw new DriverException("Unable to click on any aricle" +e );
		}
	}
	
	@Then("I should be on the corresponding news article page")
	public void I_should_be_on_the_correseponding_news_article_page() throws DriverException {
		String headlineText=null;
		try {
			headlineText = ariticleDetailsPage.getHeadlineText();
		} catch (Exception e) {
			logger.error("Unable to verify headline text");
			throw new DriverException("Unable to fetch headline text" +e );
		}
		Assert.assertEquals(articleName, headlineText, "Corresponding artile not loadded");
	}
	
	@Then("all images on the homepage should load without errors")
	public void all_images_on_the_homepage_should_load_without_errors() throws DriverException {
		int count =Integer.MIN_VALUE;
		try {
		count =	landingPage.verifyImage();
		} catch (Exception e) {
			throw new DriverException("Unable to validate broken image" +e );
		}
		Assert.assertTrue(count==0, "Broken image displayed in the application");
	}
	
	@Then("I should see the article news on the landing page and note down any article")
	public void i_should_see_the_article_news_on_the_landing_page_and_note_down() throws DriverException {
		try {
			articleName = landingPage.getRandomArticleName();
			if(articleName == null) {
				throw new NullPointerException("Article not displayed on landing page");
			}
		} catch (Exception e) {
			logger.error("Unable to get the article link on landing page "+e);
			throw new DriverException("Unable to click on any aricle" + e );
		}

	}

	@When("I click on signIn link")
	public void I_click_on_signIn_link() {
		landingPage.clickOnSignInlink();
	}
		

	

}