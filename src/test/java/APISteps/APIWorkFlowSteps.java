package APISteps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
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
    RequestSpecification rs;
    Response response2;
    RequestSpecification request;
    Response response;
    public static String employee_id;
    public static String employee_id2;

    @Given("a request is prepared to create an employee")
    public void a_request_is_prepared_to_create_an_employee() {
        request=given().header(APIConstants.HEADER_CONTENT_TYPE,APIConstants.HEADER_CONTENT_TYPE_VALUE).
                header(APIConstants.HEADER_AUTHORIZATION, GenerateTokenSteps.token).
                body(APIPayloadConstants.createEmployeePayload());

        // getting the request specification of our request-- we need headers which are content type and authorization
        //also need ot send body along with our request
    }

    @When("a POST call is made to create an employee")
    public void a_post_call_is_made_to_create_an_employee() {
        response=request.when().post(APIConstants.CREATE_EMPLOYEE_URI);
        response.prettyPrint();

        // now using our requestspecificatino object we will send our request and hit the endpoint .when()
    }

    @Then("the status code for the created employee is {int}")
    public void the_status_code_for_the_created_employee_is(int code) {
      response.then().assertThat().statusCode(code);
      //response.then()-- how we verify our response
      //here we are passing status code as an argument to the method and verifying it with the status code we get from our response
    }

    @Then("the employee created contains key {string} and value {string}")
    public void the_employee_created_contains_key_and_value(String key, String value) {
    response.then().assertThat().body(key,equalTo(value));
    //response.then() -- how we verify our response
        //using hamcrest matcher to assert the key(Message) is equal to (Employee created) but passed through the feature file

    }


    @Then("the employee id {string} is stored as a global variable to be used for other calls")
    public void the_employee_id_is_stored_as_a_global_variable_to_be_used_for_other_calls(String empIdPath) {
     employee_id = response.jsonPath().getString(empIdPath);
     //using jsonPath().getString and passing path of employee id to store employee id in our global variable employee_id
        System.out.println(employee_id);
    }


    //---------------------------------------------------------------------------------------------

//second call for getting the employee after creating it

    @Given("a request is prepared to get the employee")
    public void a_request_is_prepared_to_get_the_employee() {
       request= given().header(APIConstants.HEADER_CONTENT_TYPE,APIConstants.HEADER_CONTENT_TYPE_VALUE).
               header(APIConstants.HEADER_AUTHORIZATION,GenerateTokenSteps.token).
               queryParam("employee_id",employee_id);// passing our employee id as jquery param to access specific employee
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
    public void the_employee_we_are_getting_having_id_must_match_with_the_globally_stored_employee_id(String empIDPath) {
        String tempID=response.jsonPath().getString(empIDPath);
        // getting the employee id from our get call using jsonPath.getString()
        Assert.assertEquals(tempID,employee_id);
        // checking to see if the employee id of our post call and our get call match
    }

    @Then("the retrieved data at {string} object matches the data used to create the employee ")
    public void the_retrieved_data_at_object_matches_the_data_used_to_create_the_employee(String empObject,DataTable dataTable) {
        //data comes from feature files data table and turning into a list of maps
       List<Map<String,String>> expectedData=dataTable.asMaps(String.class,String.class);

       //data comes from get call body -- the response object (employee object) from our get call
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

               // what we are doing here is we are passing our
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

    @Given("a request is prepared to add our employee dynamically using json Object {string},{string},{string},{string},{string},{string},{string}")
    public void a_request_is_prepared_to_add_our_employee_dynamically_using_json_object(String firstname, String lastname, String middlename, String gender, String dob, String status, String jobtitle) {
      rs=given().header(APIConstants.HEADER_CONTENT_TYPE,APIConstants.HEADER_CONTENT_TYPE_VALUE)
              .header(APIConstants.HEADER_AUTHORIZATION,GenerateTokenSteps.token)
              .body(APIPayloadConstants.createEmployeeDynamic(firstname, lastname, middlename, gender, dob, status, jobtitle));
    }



    @When("a POST call is made to add an employee")
    public void a_post_call_is_made_to_add_an_employee() {
        response2=rs.when().post(APIConstants.CREATE_EMPLOYEE_URI);
    }
    @Then("the status code should be {int}")
    public void the_status_code_should_be(Integer statusCode) {
      response2.then().assertThat().statusCode(statusCode);
    }
    @Then("the employee created has the key {string} and value {string}")
    public void the_employee_created_has_the_key_and_value(String key, String value) {
        response2.then().assertThat().body(key,equalTo(value));
    }


    @And("the employee id {string} is saved as a global variable to be used for other calls")
    public void theEmployeeIdIsSavedAsAGlobalVariableToBeUsedForOtherCalls(String employeeID) {

        employee_id2=response2.jsonPath().getString(employeeID);
    }

    @And("the response header for content-type is {string}")
    public void theResponseHeaderForContentTypeIs(String responseHeaderContentType) {

        String contentType=response2.header("Content-Type");
       Assert.assertEquals(contentType,responseHeaderContentType);
    }

    @And("the retrieved data at {string} object matches the data used to create the employee")
    public void theRetrievedDataAtObjectMatchesTheDataUsedToCreateTheEmployee(String empObject, DataTable dataTable) {
        /**
         * First we need to get the data table as a list of maps and then the empObject from response as a Map
         * Then we iterate through our expectedData and get maps of single pairs of data
         *         that was passed through feature file and we want to store all keys in a sat
         * Then we will iterate the keyset and the get the expected value from our single pair of data (Individual Maps)
         * Then we get the actual value at the same key for actualData map from empObject from response
         * compare the values of the data at the same key from both the datatable and the empobject
         */
        List<Map<String,String>>expectedData=dataTable.asMaps(String.class,String.class);
        // we are storing our expected data in the datatable in the feature file as a list of maps

        Map<String,String>actualData=response.jsonPath().get(empObject);
        // we are storing our response object as a map (Actual Data)

        for(Map<String,String>singlePairData:expectedData){
            //storing our keys from data we created in a set (no duplicates here)
            Set<String> dataKeySet=singlePairData.keySet();

            for(String key:dataKeySet){// iterating through the keys and using them to retrieve data from both expected data and actual data
                // After we retrieve the value from both maps with the keys we retrieve from keyset, then we use assertion to validate them.

                String expectedDataValue=singlePairData.get(key);
                String actualDataValue=actualData.get(key);
                Assert.assertEquals(expectedDataValue,actualDataValue);
            }

        }


    }
}
