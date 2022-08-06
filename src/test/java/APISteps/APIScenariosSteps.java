package APISteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.APIConstants;
import utils.APIPayloadConstants;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.when;

public class APIScenariosSteps {


    RequestSpecification request;
    Response response;
    public static String employee_id;



    @Given("our request is prepared to create an employee using {string},{string},{string},{string},{string},{string},{string}")
    public void ourRequestIsPreparedToCreateAnEmployeeUsing(String firstname, String lastname, String middlename,
                                                            String gender, String birthday, String status, String jobTitle) {
        request=given().header(APIConstants.HEADER_CONTENT_TYPE,APIConstants.HEADER_CONTENT_TYPE_VALUE).
                header(APIConstants.HEADER_AUTHORIZATION,GenerateTokenSecond.token).
                body(APIPayloadConstants.createEmployeeDynamic(firstname,lastname,middlename,gender,birthday,status,jobTitle));

    }



    @When("we send the POST call to create our employee")
    public void we_send_the_post_call_to_create_our_employee() {
       response=request.when().post(APIConstants.CREATE_EMPLOYEE_URI);
        response.prettyPrint();
    }
    @Then("the status code will be {int}")
    public void the_status_code_will_be(Integer statusCode) {
     response.then().assertThat().statusCode(statusCode);// asserting that the status code is equal to 201 for employee created
    }
    @Then("the ID {string} is stored as a global variable for other calls")
    public void the_id_is_stored_as_a_global_variable_for_other_calls(String empID) {
        employee_id=response.jsonPath().getString(empID);
        System.out.println(employee_id);
    }


}
