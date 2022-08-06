package utils;

import io.restassured.RestAssured;

public class APIConstants {
//API constants contains all the constants that will be used throughout all api tests
    // has all of the URI's as well as our headers that we pass with most of our requests
    public static final String BaseURI= RestAssured.baseURI="http://hrm.syntaxtechs.net/syntaxapi/api";
    public static final String GENERATE_TOKEN_URI=BaseURI+ "/generateToken.php";
    public static final String CREATE_EMPLOYEE_URI=BaseURI+ "/createEmployee.php";
    public static final String GET_ONE_EMPLOYEE_URI=BaseURI+ "/getOneEmployee.php";
    public static final String UPDATE_EMPLOYEE_URI=BaseURI + "/updateEmployee.php";
    public static final String GET_ALL_EMPLOYEE_URI=BaseURI + "/getAllEmployees.php";
    public static final String DELETE_EMPLOYEE_URI=BaseURI+ "/deleteEmployee.php";
    public static final String UPDATE_PARTIAL_EMPLOYEE_URI=BaseURI+ "/updatePartialEmplyeesDetails.php";
    public static final String GET_EMPLOYEE_STATUS_URI=BaseURI+ "/employeementStatus.php";
    public static final String DEMOQA_BASEURI=RestAssured.baseURI= "https://demoqa.com";
    public static String DEMOQA_CREATETOKEN_URI=DEMOQA_BASEURI+ "/Account/v1/GenerateToken";


    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_TYPE_VALUE= "application/json";

    public static final String HEADER_AUTHORIZATION="Authorization";
    public static final String RESPONSE_CONTENT_TYPE_VALUE="application/json";



}
