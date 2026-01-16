package com.api.test;

import com.api.pojo.UserCredential;
import com.api.utils.ConfigManager;
import com.api.utils.ConfigManager2;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class LoginAPITest  {
    String token;
    @Test
    public void loginWithValidCredentials()
    {

        UserCredential user = new UserCredential(ConfigManager.getProperty("USERNAME"),
                ConfigManager.getProperty("PASSWORD"));
        Response response = given()
                .baseUri(ConfigManager2.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .body(user)
                .log().headers()
                .log().body()
                .log().uri()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", Matchers.equalTo("Success"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginAPIResponseSchema.json"))
                .body("data.token",Matchers.notNullValue())
                .extract().response();
    }
    @Test
    public void loginWithGetMethod() //Wrong HTTP Method
    {

        UserCredential user = new UserCredential(ConfigManager.getProperty("USERNAME"),
                ConfigManager.getProperty("PASSWORD"));
        Response response = given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .body(user)
                .log().headers()
                .log().body()
                .log().uri()
                .when()
                .get("/login")
                .then()
                .log().all()
                .statusCode(404)
                .extract().response();
    }
    @Test
    public void loginWithInvalidCredentials() //Login with empty username-password
    {

        UserCredential user = new UserCredential(ConfigManager.getProperty("USERNAME"),
                ConfigManager.getProperty("PASSWORDD"));
        given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .body(user)
                .log().headers()
                .log().body()
                .log().uri()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(500);


    }

}
