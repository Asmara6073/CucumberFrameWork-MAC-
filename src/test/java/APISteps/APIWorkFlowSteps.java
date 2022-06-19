package APISteps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utils.APIConstants;
import utils.APIPayloadConstants;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class APIWorkFlowSteps {

    RequestSpecification request;
    Response response;
    public static String employee_id;

    @Given("a request is prepared to create an employee")
    public void a_request_is_prepared_to_create_an_employee() {
        request=given().header(APIConstants.HEADER_CONTENT_TYPE,APIConstants.HEADER_CONTENT_TYPE_VALUE).
                header(APIConstants.HEADER_AUTHORIZATION, GenerateTokenSteps.token).
                body(APIPayloadConstants.createEmployeePayload());
    }

    @When("a POST call is made to create an employee")
    public void a_post_call_is_made_to_create_an_employee() {
        response=request.when().post(APIConstants.CREATE_EMPLOYEE_URI);
        response.prettyPrint();
    }

    @Then("the status code for the created employee is {int}")
    public void the_status_code_for_the_created_employee_is(Integer code) {
      response.then().assertThat().statusCode(code);
    }

    @Then("the employee created contains key {string} and value {string}")
    public void the_employee_created_contains_key_and_value(String key, String value) {
    response.then().assertThat().body(key,equalTo(value));
    }


    @Then("the employee id {string} is stored as a global variable to be used for other calls")
    public void the_employee_id_is_stored_as_a_global_variable_to_be_used_for_other_calls(String empId) {
     employee_id = response.jsonPath().getString(empId);
        System.out.println(employee_id);
    }


    //---------------------------------------------------------------------------------------------

//second call for getting the employee after creating it

    @Given("a request is prepared to get the employee")
    public void a_request_is_prepared_to_get_the_employee() {
       request= given().header(APIConstants.HEADER_CONTENT_TYPE,APIConstants.HEADER_CONTENT_TYPE_VALUE).
               header(APIConstants.HEADER_AUTHORIZATION,GenerateTokenSteps.token).
               queryParam("employee_id",employee_id);
    }

    @When("a GET call is made to retrieve the created employee")
    public void a_get_call_is_made_to_retrieve_the_created_employee() {
      response=request.when().get(APIConstants.GET_ONE_EMPLOYEE_URI);
    }

    @Then("the status call for the created employee is {int}")
    public void the_status_call_for_the_created_employee_is(Integer code) {
        response.then().assertThat().statusCode(code);
    }

    @Then("the employee we are getting having ID {string} must match with the globally stored employee id")
    public void the_employee_we_are_getting_having_id_must_match_with_the_globally_stored_employee_id(String empID) {
        String tempID=response.jsonPath().getString(empID);
        Assert.assertEquals(tempID,employee_id);
    }

    @Then("the retrieved data at {string} object matches the data used to create the employee having employee id {string}")
    public void the_retrieved_data_at_object_matches_the_data_used_to_create_the_employee_having_employee_id(String empObject, String responseEmpID, DataTable dataTable) {
        //data comes from feature files data table
       List<Map<String,String>> expectedData=dataTable.asMaps(String.class,String.class);

       //data comes from get call body -- the response object from our get call
        // we use get() method instead of getString() because we are passing employee object(with small e) from our
            //feature file (the one we got from the response of our get call
       Map<String,String> actualData=response.body().jsonPath().get(empObject);

       // then we iterate our expected data List of Maps and get all the keys from single maps and store them in a set
       for(Map<String,String> singlePairOfData: expectedData){
        // we use set to store the keys because only stores unique values
           // we need the keys to later get the values from our actualData and expectedData to compare them
           Set<String> keys=singlePairOfData.keySet();

           // Then we iterate our keys and get the expectedvalue by using .get() method on our singlePairofData Map
                // this returns the value at specfied key
           for(String key:keys){
               String expectedValue=singlePairOfData.get(key);

               //We are getting the actual value using .get() method on actualData Map
               String actualValue=actualData.get(key);
               Assert.assertEquals(expectedValue,actualValue);

           }
       }

    }


    @Given("a request is prepared to create an employee via json payload")
    public void a_request_is_prepared_to_create_an_employee_via_json_payload() {
        request=given().header(APIConstants.HEADER_CONTENT_TYPE,APIConstants.HEADER_CONTENT_TYPE_VALUE).
                header(APIConstants.HEADER_AUTHORIZATION,GenerateTokenSteps.token).
                body(APIPayloadConstants.createEmployeePayloadViaJson());
    }

    @Given("a request is prepared to create an employee via dynamic payload {string} , {string}, {string}, {string}, {string},{string}, {string}")
    public void a_request_is_prepared_to_create_an_employee_via_dynamic_payload(String firstname, String lastname, String middlename, String gender, String birthday, String status, String jobTitle) {
      request=given().header(APIConstants.HEADER_CONTENT_TYPE,APIConstants.HEADER_CONTENT_TYPE_VALUE).
              header(APIConstants.HEADER_AUTHORIZATION,GenerateTokenSteps.token).
              body(APIPayloadConstants.createEmployeeDynamic(firstname,lastname,middlename,gender,birthday,status,jobTitle));
    }





}
