package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExamples {

    String baseURI=RestAssured.baseURI= "http://hrm.syntaxtechs.net/syntaxapi/api";// URI for request(Base URL+endpoint)
    String token= "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTUzMzQ5MTksImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY1NTM3ODExOSwidXNlcklkIjoiMzgzNyJ9.0L3hmfo6bFjBZVz6A9M_bBOCmwzYa1_z7vuNhlbG_3A";
    static String employee_id;

    @Test
    public void acreateEmployee(){
        // given prepares the request and header takes type of header and what it is, .body(takes the payload if needed)
        RequestSpecification request=given().header("Content-Type","application/json").
                header("Authorization",token).body("{\n" +
                        "  \"emp_firstname\": \"Wardell\",\n" +
                        "  \"emp_lastname\": \"Curry\",\n" +
                        "  \"emp_middle_name\": \"Stephen\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1985-06-11\",\n" +
                        "  \"emp_status\": \"employed\",\n" +
                        "  \"emp_job_title\": \"QA Engineer\"\n" +
                        "}");
                //hitting our endpoint and storing response in the response object
                Response response = request.when().post(" /createEmployee.php");// using when and specifying the method type
                response.prettyPrint(); //print the overall output in the console

                response.then().assertThat().statusCode(201);// assert the status code


                //Hamcrest matchers verify the string
                response.then().assertThat().body("Message", equalTo("Employee Created"));
                response.then().assertThat().body("Employee.emp_firstname",equalTo("Wardell"));
                //using jsonPath() - to specify the key in the body so that it returns the value against it
               employee_id=response.jsonPath().getString("Employee.employee_id");
               System.out.println(employee_id);

    }
    @Test
    public void bgetCreatedEmployee(){
        // creating our request
        RequestSpecification request =given().header("Content-Type","application/json").
                header("Authorization", token).
                queryParam("employee_id",employee_id);

        // hitting the endpoint and storing our response in response
        Response response = request.when().get("/getOneEmployee.php");
        response.then().assertThat().statusCode(200);// assert 200 status code
        response.prettyPrint();


        response.then().assertThat().body("employee.employee_id", equalTo(employee_id));

        // getting employee id generated in get call and storing in this variable
        String tempID=response.jsonPath().getString("employee.employee_id");
        //Assert.assertEquals(tempID,employee_id);

    }

    @Test
    public void cupdateEmployee(){
        RequestSpecification request = given().header("Content-Type","application/json").
                header("Authorization",token).
                body("{\n" +
                        "  \"employee_id\": \"" + employee_id +"\",\n" +
                        "  \"emp_firstname\": \"KOBE\",\n" +
                        "  \"emp_lastname\": \"BRYANT\",\n" +
                        "  \"emp_middle_name\": \"BEAN\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1980-08-24\",\n" +
                        "  \"emp_status\": \"employed\",\n" +
                        "  \"emp_job_title\": \"GOAT\"\n" +
                        "}");

        Response response =request.when().put("/updateEmployee.php");
        response.prettyPrint();

        response.then().assertThat().statusCode(200);
    }

    @Test
    public void dGetUpdatedEmployee(){

        RequestSpecification request= given().header("Content-Type","application/json").
                header("Authorization",token).queryParam("employee_id",employee_id);

        Response response=request.when().get("/getOneEmployee.php");

        response.then().assertThat().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void eGetAllEmployees(){
        RequestSpecification request= given().header("Authorization",token);

        Response response= request.when().get("/getAllEmployees.php");
        // returns string of response
        String allEmployees=response.body().asString();

        //jsonPath() vs jsonPath
        //jsonPath is a class that contains method for converting the values into json object
        //jsonPath() is a method that belongs to jsonPath class

        //creating object of jsonPath class
        JsonPath js =new JsonPath(allEmployees);

        //retrieving the total number of employees
       int count= js.getInt("Employees.size()");
        System.out.println(count);

        // print only the employee id of all the employees
        for(int i=0; i<count;i++){
            String empID=js.getString("Employees["+i+"].employee_id");
            System.out.println(empID);
        }



    }

}
