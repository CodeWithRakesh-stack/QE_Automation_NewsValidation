package com.cucumber.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cucumber.actions.StepActions;
import com.cucumber.base.PageContext;
import com.cucumber.base.TestContext;

public class MyAccountPage extends  PageContext{

	StepActions stepActions;
	// *******************************************************************************************
	// Constructor - initialize page elements
	// instantiate step actions to use the reusable methods to perform actions.
	// *******************************************************************************************
	public MyAccountPage(TestContext testcontext, StepActions stepActions) {
		super(testcontext.webDriver);
		this.stepActions = stepActions;
	}
	
	//WebElement for account overview tab validation
	@FindBy(xpath = "//h1[text()='Account overview']")
	private WebElement accountOverviewTabeloaddedElement;

	/**
	 * Function to check if my account overview tab 
	 * @return boolean
	 */
	public boolean verifyAccountOverViewTabloaded() {
		return stepActions.waitForElementToDisplay(accountOverviewTabeloaddedElement);
		
	}
}
