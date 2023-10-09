package com.cucumber.steps;

import org.picocontainer.annotations.Inject;
import org.testng.Assert;

import com.cucumber.exceptions.DriverException;
import com.cucumber.pages.LandingPage;
import com.cucumber.pages.LoginPage;
import com.cucumber.pages.MyAccountPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyAccountSteps {


	@Inject
	LandingPage landingPage;
	
	@Inject
	LoginPage loginPage;
	
	@Inject
	MyAccountPage myAccountPage;
	
	
	@Then("I should be seeing my account link on landing page")
	public void verify_my_account_link_should_display() throws DriverException, InterruptedException {
		Assert.assertTrue(landingPage.verifyMyAccountlink(), "My account link not displayed on login page");
	}
	
	@When("I click on my account overview link")
	public void I_click_on_my_account_overview_link() throws DriverException {
		landingPage.clickMyAccountOverViewTablink();
	}
	
	@Then("I Should be on my account overview tab")
	public void I_should_be_on_my_account_overview_tab() throws DriverException {
		Assert.assertTrue(myAccountPage.verifyAccountOverViewTabloaded(), "My account link not displayed on login page");
	}
	
	
}
