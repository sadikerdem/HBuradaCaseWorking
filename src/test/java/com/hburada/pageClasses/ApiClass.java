package com.hburada.pageClasses;

import com.hburada.base.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class ApiClass extends TestBase {

    WebDriver driver;

    public ApiClass(WebDriver driver) {
        this.driver = driver;
    }

    public void getMethodLangStatus(String language) throws InterruptedException {
        RestAssured.baseURI = "https://generator.swagger.io/api/gen/clients/";
        Response response = RestAssured.given().when().get(language);
        int responseStatusCode = response.getStatusCode();
        Assert.assertEquals(responseStatusCode, 200,"Unexpected code taken: " + responseStatusCode);
        validateStatusCode(responseStatusCode,"Get");
    }

    public void postMethodLangStatus(int id) throws InterruptedException {
        RestAssured.baseURI ="https://petstore.swagger.io";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("id", id);
        requestParams.put("username", "string");
        requestParams.put("firstName", "string");
        requestParams.put("lastName", "string");
        requestParams.put("email", "string");
        requestParams.put("password", "string");
        requestParams.put("phone", "string");
        requestParams.put("userStatus", 0);

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type","application/json");
        map.put("accept","application/json");

        request.body(requestParams.toString());
        request.headers(map);
        Response response = request.post("https://petstore.swagger.io/v2/user");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200,"Unexpected code taken: " + statusCode);
        validateStatusCode(statusCode,"Post");
    }

    public void validateStatusCode(int responseStatusCode, String methodType) throws InterruptedException {

        switch (responseStatusCode) {
            case 200 :
                log.info("Status code is 200(OK) for "+methodType+" method!");
                Thread.sleep(500);
                break;

            case 201 :
                log.info("Status code is 201(Created) for "+methodType+" method!");
                Thread.sleep(500);
                break;

            default :
                log.info("Check your status code again! --> "+responseStatusCode);
                Thread.sleep(500);
                break;
        }

    }

}
