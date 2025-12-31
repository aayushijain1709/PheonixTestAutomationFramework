package com.api.test;

import com.api.pojo.UserCredential;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class LoginAPITest extends BaseTest {
    String token;
    @Test
    public void loginWithValidCredentials()
    {

        UserCredential user = new UserCredential("iamfd","password");
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .body(user)
                .log().headers()
                .log().body()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", Matchers.equalTo("Success"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginAPIResponseSchema.json"))
                .body("data.token",Matchers.notNullValue())
                .time(Matchers.lessThan(1000L))
                .extract().response();

        token = response.jsonPath().getString("data.token");
    }
    public String getToken() {
        return token;
    }
    @Test
    public void loginWithGetMethod() //Wrong HTTP Method
    {

        UserCredential user = new UserCredential("iamfd","password");
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .body(user)
                .log().headers()
                .log().body()
                .when()
                .get("/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", Matchers.equalTo("Success"))
                 .body("data.token",Matchers.notNullValue())
                .time(Matchers.lessThan(1000L))
                .extract().response();
    }
    @Test
    public void loginWithInvalidCredentials() //Login with empty username-password
    {

        UserCredential user = new UserCredential("iamfdd","password");
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .body(user)
                .log().headers()
                .log().body()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", Matchers.equalTo("Success"));


    }

}
