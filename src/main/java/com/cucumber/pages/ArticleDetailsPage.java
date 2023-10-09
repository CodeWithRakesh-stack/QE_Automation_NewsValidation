package com.cucumber.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cucumber.actions.StepActions;
import com.cucumber.base.PageContext;
import com.cucumber.base.TestContext;

public class ArticleDetailsPage extends PageContext{
	
	StepActions stepActions;
	
	// *******************************************************************************************
	// Constructor - initialize page elements
	// instantiate step actions to use the reusable methods to perform actions.
	// *******************************************************************************************
	public ArticleDetailsPage(TestContext testcontext, StepActions stepActions) {
		super(testcontext.webDriver);
		this.stepActions = stepActions;
	}
	
	// Page Element to get Headline
	@FindBy(xpath = "//*[@data-gu-name='headline']//h1")
	WebElement headlineElement;
	
	public String getHeadlineText() {
		stepActions.waitForElementToDisplay(headlineElement);
		return stepActions.getElementText(headlineElement);
	}

	

}
