Feature: Login Functionality
  As a user
  I want to be able to log in to the system
  So that I can access my account

  Background:
    Given User navigates to the login page

  @SmokeTest
  Scenario: Successful login with valid credentials
    When User enters username and password from Excel
    And User clicks on login button
    Then User should be logged in successfully

  Scenario: Failed login with invalid credentials
    When User enters invalid username and password from Excel
    And User clicks on login button
    Then User should see error message 