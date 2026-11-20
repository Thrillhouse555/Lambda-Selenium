Feature: Date page functionality
  As a visitor
  I want to view the date page
  So that I can see the current day, date, and time

    @flaky-test @false-positive
  Scenario Outline: [FALSE-DP-001] View Day - A false positive test to demonstrate Agent Smith's (AI) ability to detect flaky tests.
    Given I am on the date page
    Then I should see the current day of the week is "<day>"
    Examples:
      | day       |
      | Monday    |
      
    @flaky-test @false-positive
  Scenario Outline: [FALSE-DP-002] View Morning - A false positive test to demonstrate Agent Smith's (AI) ability to detect flaky tests.
    Given I am on the date page
    Then I should see the current period is "Morning"
