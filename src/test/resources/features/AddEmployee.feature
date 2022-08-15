Feature: Adding the employees in HRMS Application

Background:
  When user enters valid admin credentials
  And user clicks on login button
  Then admin user is successfully logged in
  When user clicks on PIM option
  And user clicks on add employee option

  @regression @tarik
  Scenario: Adding one employee from feature file
   # Given user is navigated to HRMS application
    And user enters firstname middlename and lastname
    And user captures empID
    And user clicks on save button
    Then employee added successfully


    Scenario: Adding one employee using cucumber feature file
      #simply passing the values we want to use in the step through our feature file
      # The step will contain three parameters of the string type ad use these values as arguments for the step definition
      And user enters "zuhoor" "Mujeeb" and "Sylvia"
      And user clicks on save button
      Then employee added successfully

      @test
      Scenario Outline: Adding multiple employees
        #passing parameters that match the headers of the examples table so when scenario runs it passes those values in this step
        And user provides "<firstName>" "<middleName>" and "<lastName>"
        And user clicks on save button
        Then employee added successfully
        Examples:
        |firstName|middleName|lastName|
        |Asel     |MS        |Tolga   |
        |Yazgul   |MS        |Kishan  |
        |Tarik    |MS        |Mujeeb  |
        |Nassir   |MS        |Volkan  |

      @test @dataTable
      Scenario: Add employee using data table
        #writing seperate code to understand the data table
        #List<Map<String,String>> will be created by using databale.asMaps and then we develop the logic to work with the data
        #loop through the maps (each row is a map) and grab the values and perform actions
        When user provides multiple employee data and verify they are added
        |firstName|middleName|lastName|
        |Asel     |MS        |Tolga   |
        |Yazgul   |MS        |Kishan  |
        |Tarik    |MS        |Mujeeb  |
        |Nassir   |MS        |Volkan  |

        @excel
      Scenario: Adding multiple employees from excel file
          When user adds multiple employees from excel file using "EmployeeData" sheet and verify the user added

  @e2eTest1
  Scenario: Adding one employee using cucumber feature file
    And user enters "Kobe" "Bean" and "Bryant"
    And user grabs the employee id
    And user clicks on save button
    And user query the database for same employee id
    Then user verifies the results