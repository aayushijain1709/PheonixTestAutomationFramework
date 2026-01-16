package com.api.test;

import com.api.constant.Roles;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.ConfigManager2;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class UserDetailAPITest {

    @Test
    public void userDetailAPIRequest()
    {
        given()
                .baseUri(ConfigManager2.getProperty("BASE_URI"))
                .header("Authorization", AuthTokenProvider.getToken(Roles.QC))
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .log().uri()
                .log().headers()
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
    @Test
    public void userDetailsWithoutAuthToken()
    {
            given().baseUri(ConfigManager2.getProperty("BASE_URI"))
                    .log().uri()
                    .when()
                    .get("/userdetails")
                    .then()
                    .log().all()
                    .statusCode(401);
    }
}
