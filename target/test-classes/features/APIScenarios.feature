Feature: Api Related Scenarios

  Background:
    Given a JWT is created

    @POSTCREATEEMPLOYEE
Scenario: Adding an employee using API
  Given our request is prepared to create an employee using "Kobe","Bryant","Bean","M","1979-24-08","employed","Shooting Gaurd"
  When we send the POST call to create our employee
  Then the status code will be 201
  And the ID "Employee.employee_id" is stored as a global variable for other calls

