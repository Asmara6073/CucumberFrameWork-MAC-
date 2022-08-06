package APISteps;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;
import utils.APIConstants;
import utils.APIPayloadConstants;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class GenerateTokenSecond {

public static String token;
    @Given("a JWT is created")
    public void a_jwt_is_created() {
      RequestSpecification request=given().header(APIConstants.HEADER_CONTENT_TYPE,APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .body(APIPayloadConstants.generateTokenPayloadViaJson());
       Response response=request.when().post(APIConstants.GENERATE_TOKEN_URI);
        token="Bearer "+response.jsonPath().getString("token");
        System.out.println(token);
    }

}
