
Feature: Blaze Demo website test

  Background: Search for flights from Boston to New York
    Given I am at "https://blazedemo.com/" website
    When I search a trip from "Boston" to "New York"
    And I submit the form

  Scenario: Count results
    Then the page must have the title "BlazeDemo - reserve"
    And table must have 5 rows

  Scenario: Choose flight
    When I click the result number 1
    And I fill in "Name" with "David Ferreira"
    And I fill in "Address" with "Main Town St. 3"
    And I fill in "City" with "Atlanta"
    And I fill in "State" with "Atlanta"
    And I fill in "Month" with "05"
    And I fill in "Year" with "2000"
    And I fill in "Name on Card" with "David Ferreira"
    And I submit the form
    Then the page heading must have the text "Thank you for your purchase today!"
    And the page must have the title "BlazeDemo Confirmation"



