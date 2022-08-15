Feature: Validation of login scenarios

  Background:
    #Given user is navigated to HRMS application

  @adminlogin @smoke
  Scenario: Admin Login
    #Given user is navigated to HRMS application
    When user enters valid admin credentials
    And user clicks on login button
    Then admin user is successfully logged in

    @regression @smoke
    Scenario: Employee login
     # Given user is navigated to HRMS application
      When user enters valid ESS credentials
      And user clicks on login button
      Then ESS user is successfully logged in

      @regression @smoke
    Scenario: Invalid Login
     # Given user is navigated to HRMS application
      When user enters invalid credentials
      And user clicks on login button
      Then user will see an error message on the screen

        @smoke
    Scenario: Empty Username Login
      When user passes empty username field
      And user enters valid password
      And user clicks on login button
      Then user will see Username cannot be empty message on the screen

  @smoke
    Scenario:Empty Password Login
      When user enters a valid username
      And user passes an empty password field
      And user clicks on login button
      Then user will see Password cannot be empty message on the screen
