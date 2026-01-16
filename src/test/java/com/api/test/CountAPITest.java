package com.api.test;

import com.api.constant.Roles;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager2;
import static org.hamcrest.Matchers.*;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class CountAPITest {
    @Test
    public void verifyCountAPIResponse()
    {
        given().
                baseUri(ConfigManager2.getProperty("BASE_URI")).
                header("Authorization", AuthTokenProvider.getToken(Roles.FD)).
                log().uri().
                log().headers().
                when().
                get("/dashboard/count").
                then()
                .log().all()
                .statusCode(200)
                .time(lessThan(500L))
                .body("message",equalTo("Success"))
                .body("data",hasSize(3))
                .body("data.label",containsInAnyOrder("Pending for delivery","Pending for FST assignment","Created today"))
                .body("data.count",notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserCountAPIResponseSchema.json"));
    }

    @Test
    public void CountAPIWitoutAuthToken()
    {
        given().
                baseUri(ConfigManager2.getProperty("BASE_URI")).
                log().uri().
                log().headers().
                when().
                get("/dashboard/count").
                then()
                .log().all()
                .statusCode(401);
    }
}
