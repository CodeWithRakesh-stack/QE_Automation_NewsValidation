package com.cucumber.pages;

import com.cucumber.actions.StepActions;
import com.cucumber.base.PageContext;
import com.cucumber.base.TestContext;
import com.cucumber.exceptions.DriverException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageContext {

	StepActions stepActions;

	// *******************************************************************************************
	// Constructor - initialize page elements
	// instantiate step actions to use the reusable methods to perform actions.
	// *******************************************************************************************
	public LoginPage(TestContext testcontext, StepActions stepActions) {
		super(testcontext.webDriver);
		this.stepActions = stepActions;
	}

	// *******************************************************************************************
	// Page Elements declaration - SignIn - Email, Password
	// *******************************************************************************************
	@FindBy(name = "email")
	WebElement emailInputWebElement;

	@FindBy(name = "password")
	WebElement passwordElement;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement loginButton;
	
	// *******************************************************************************************
	// Action method to set UserName and Password.
	// *******************************************************************************************
	public boolean verifySignInPageloaded() {
		return stepActions.waitForElementToDisplay(emailInputWebElement);
	}
	
	public void loginToApp(String userName, String password) throws DriverException {
		try {
		stepActions.setDataInTextBox(emailInputWebElement, userName);
		stepActions.setDataInTextBox(passwordElement, password);
		stepActions.clickElement(loginButton);
		stepActions.handleCapchaInCaseAppears();
		}
		catch (Exception e) {
			throw new DriverException("Unable to login into application "+e);
		}
		}

}
