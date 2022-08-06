package utils;

import org.json.JSONObject;
import org.openqa.selenium.json.Json;

public class APIPayloadConstants {

    //Payload constants allow us to store our payloads and just call them in our request when needed we don't need to create
        // payload over and over again

    /**
     * Creating JSON object to dynamically create our payload is the best option so we are not hardcoding anything
     * we need to create a json object in our method using values passed as arguments
     *and then turn it into a string to return a string
     *
     *
     */
    public static String createEmployeePayload(){
        String createEmployee = "{\n" +
                "  \"emp_firstname\": \"Wardell\",\n" +
                "  \"emp_lastname\": \"Curry\",\n" +
                "  \"emp_middle_name\": \"Stephen\",\n" +
                "  \"emp_gender\": \"M\",\n" +
                "  \"emp_birthday\": \"1985-06-11\",\n" +
                "  \"emp_status\": \"employed\",\n" +
                "  \"emp_job_title\": \"QA Engineer\"\n" +
                "}";
        return createEmployee;
    }

    //passing the body from json object
    public static String createEmployeePayloadViaJson(){
        JSONObject obj=new JSONObject(); // storing our data in JSON object and storing in key values
        obj.put("emp_firstname","Wardell");
        obj.put("emp_lastname","Curry");
        obj.put("emp_middle_name","Stephen");
        obj.put("emp_gender","M");
        obj.put("emp_birthday","1985-06-11");
        obj.put("emp_status","employed");
        obj.put("emp_job_title","QA Engineer");
        return obj.toString();
    }


    public static String createEmployeeDynamic
            (String firstname,String lastname,String middlename,String gender,
             String birthday,String status,String jobTitle){
        JSONObject obj=new JSONObject(); // method that passes parameters of json object that holds the payolad of our
                                        // createEmployee POST call
                                        //Arguments will be passed through the feature file
        obj.put("emp_firstname",firstname);
        obj.put("emp_lastname",lastname);
        obj.put("emp_middle_name",middlename);
        obj.put("emp_gender",gender);
        obj.put("emp_birthday",birthday);
        obj.put("emp_status",status);
        obj.put("emp_job_title",jobTitle);
        return obj.toString();
    }


    public static String generateTokenPayload(){
        String generateToken="{\n" +
                "  \"email\": \"Hello123@test.com\",\n" +
                "  \"password\": \"Tester123\"\n" +
                "}";
        return generateToken;
    }

    public static String generateTokenPayloadViaJson(){
        JSONObject obj=new JSONObject();// using json object to pass the email and password of user to generate token
                                                //for authorization
        obj.put("email","Hello123@test.com");
        obj.put("password","Tester123");
        return obj.toString();
    }






}
