package com.api.test;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class UserDetailAPIRequest extends BaseTest{
    LoginAPITest loginAPITest;
    @BeforeTest
    public void setLoginAPITest() {
         loginAPITest = new LoginAPITest();
         loginAPITest.loginWithValidCredentials();
    }
    @Test
    public void userDetailAPIRequest()
    {
        given().header("Authorization",loginAPITest.getToken())
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .when()
                .get("/userdetails")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", Matchers.equalTo("Success"))
                .body("data.id",Matchers.notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsAPIResponseSchema.json"))
                .extract().response();
    }
}
