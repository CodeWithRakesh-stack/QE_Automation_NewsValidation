Feature: Homepage Verification
  As a user
  I want to verify that the News article landing page should loads without errors
  So that I can ensure a smooth user experience

  Background:
    Given I am on the "The Guardian" landing page
  
 
  @smoke @all
  Scenario: Verify any Article Links
    When I click on any article link
    Then I should be on the corresponding news article page
   
  @regression @all
  Scenario: Verify Homepage Images
    Then all images on the homepage should load without errors
	 
  @newsvalidtion @all
  Scenario: Verify article news is valid to compare with another website
    Then I should see the article news on the landing page and note down any article
		When I have opened a web browser and search with noted article name
    And  I click on news tab and check news tab should be loadded
    Then I should see the noted article news in 2 diffrent website and percentage of match should be above 70
    
  @all
  Scenario: Verify Sign up for our email feature
  	When I click on sign up for our email button
  	Then I should be see on the Sign up for the First Edition newsletter page
  	When I enter email address and click on submit button
  	Then I should see the confirmation message
  
  @all
  Scenario: Verify twitter sharing option with friends
  	When I click on sign up for our email button
  	Then I should be see on the Sign up for the First Edition newsletter page
  	When I click on twitter icon for sharing
  	Then I should be on twitter page and my url should be "https://twitter.com/" 
  
  @all
  Scenario: Verify facebook sharing option with friends
  	When I click on sign up for our email button
  	Then I should be see on the Sign up for the First Edition newsletter page
  	When I click on facebook icon for sharing
  	Then I should be on facebook page and my url should be "https://www.facebook.com/"
  