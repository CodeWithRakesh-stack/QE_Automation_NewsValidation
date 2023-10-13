Feature: Validate My account section should load properly without any errors


Background:
    Given I am on the "The Guardian" landing page
    When I click on signIn link
   	Then I should be on Login page
   	When I enters the email and password and click on sign in button
  	
    
   @smoke @login @all
  Scenario: Verify My account link should display on landinPage.
  	Then I should be seeing my account link on landing page
  	
   @smoke @login @all
	Scenario: Verify my account overview tab 
	  Then I should be seeing my account link on landing page
		When I click on my account overview link
		Then I Should be on my account overview tab
		