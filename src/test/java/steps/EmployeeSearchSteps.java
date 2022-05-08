package steps;

import utils.CommonMethods;
import utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmployeeSearchSteps extends CommonMethods {

    @Given("user is navigated to HRMS application")
    public void user_is_navigated_to_hrms_application() {
        //openBrowserAndLaunchApplication();
    }
    @When("user enters valid admin credentials")
    public void user_enters_valid_admin_credentials() {
        sendText(login.usernameBox,ConfigReader.getPropertyValue("username"));
        sendText(login.passwordBox,ConfigReader.getPropertyValue("password"));
    }
    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        click(login.loginBtn);
    }
    @When("user navigates to employee list page")
    public void user_navigates_to_employee_list_page() {
        click(employeeSearchPage.pimOption);
        click(employeeSearchPage.empListOption);
    }
    @When("user enters valid employee id")
    public void user_enters_valid_employee_id() {
        sendText(employeeSearchPage.idField,"8510142");
    }
    @When("user clicks on search button")
    public void user_clicks_on_search_button() {
        click(employeeSearchPage.searchBtn);
    }
    @Then("user is able to see employee information")
    public void user_is_able_to_see_employee_information() {
        System.out.println("Result displayed");
        //verification needed
       // tearDown();
    }

    @When("user enters valid employee name")
    public void user_enters_valid_employee_name() {
        sendText(employeeSearchPage.nameField,"Zubair");
    }




}
