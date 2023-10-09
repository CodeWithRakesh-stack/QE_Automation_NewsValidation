package com.cucumber.steps;

import org.picocontainer.annotations.Inject;
import org.testng.Assert;

import com.cucumber.exceptions.DriverException;
import com.cucumber.pages.LandingPage;
import com.cucumber.pages.LoginPage;
import com.cucumber.utils.CommonUtils;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginPageSteps {


		@Inject
		LandingPage landingPage;
		
		@Inject
		LoginPage loginPage;
		
		@Then("I should be on Login page")
		public void I_should_be_on_Login_page() {
			Assert.assertTrue(loginPage.verifySignInPageloaded(), "Sign in page not loadded");
			
		}
		@When("I enters the email and password and click on sign in button")
		public void i_enters_the_email_and_password_in_the_input_box_and_clicks_on_the_signIn_button() throws DriverException {
			loginPage.loginToApp(CommonUtils.getConfigProperty("email"), CommonUtils.getConfigProperty("password"));
		}
		
		

}
