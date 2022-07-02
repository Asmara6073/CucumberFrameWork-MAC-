package APIReview;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class APIExamples {

String baseURI=RestAssured.baseURI="http://hrm.syntaxtechs.net/syntaxapi/api";
String token= "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTYxNzM3NDIsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY1NjIxNjk0MiwidXNlcklkIjoiNDAyMCJ9.0-ZvSKQCTrPfH-hhgvjTQjDWnZQYbzNwS7aIeH6J1Mc";

// create an employee in SyntaxHRMS
@Test
public void createAnEmployee(){
    RequestSpecification request=given().header("Content-Type","application/json").header("Authorization",token).
            body("{\n" +
                    "  \"emp_firstname\": \"Dr.\",\n" +
                    "  \"emp_lastname\": \"Dre\",\n" +
                    "  \"emp_middle_name\": \"Biggie\",\n" +
                    "  \"emp_gender\": \"M\",\n" +
                    "  \"emp_birthday\": \"2019-09-08\",\n" +
                    "  \"emp_status\": \"employed\",\n" +
                    "  \"emp_job_title\": \"businessman\"\n" +
                    "}");

                Response response = request.when().post("/createEmployee.php");
                //response.prettyPrint();

 //understanding GSON to decode json object
    //reading the response returned to us from rest assured and converting to a json element
    JsonElement json_element= new JsonParser().parse(response.asString());


    //converting our json element into json object. JSON element is just a VALUE, we must convert it into a json object
        // to extract data
        JsonObject json_data =json_element.getAsJsonObject();
        // extracting the value of message from the json object
        JsonElement messageValue=json_data.get("Message");
        System.out.println(messageValue);

        JsonElement employeeValue=json_data.get("Employee");

        JsonObject employee =employeeValue.getAsJsonObject();

        JsonElement idValue=employee.get("employee_id");
        JsonElement firstname=employee.get("emp_firstname");
        JsonElement lastname=employee.get("emp_lastname");
        JsonElement middlename=employee.get("emp_middle_name");
        JsonElement gender=employee.get("emp_gender");
        JsonElement DOB=employee.get("emp_birthday");
        JsonElement status=employee.get("emp_status");
        JsonElement jobTitle=employee.get("emp_job_title");

        Assert.assertEquals(employee,employeeValue);

}


}
