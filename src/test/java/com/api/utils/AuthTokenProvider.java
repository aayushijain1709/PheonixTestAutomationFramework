package com.api.utils;


import com.api.constant.Roles;
import com.api.pojo.UserCredential;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class AuthTokenProvider {
    private static String  token;
    private static Map<Roles, String> tokenCache = new HashMap<>();

    private AuthTokenProvider() {
    }
    public static String getToken(Roles user){
        if(tokenCache.get(user)==null) {
            String userName = "USERFD";
            if (user == Roles.FD) {
                userName = "USERFD";
            } else if (user == Roles.SUP) {
                userName = "USERSUP";
            } else if (user == Roles.ENG) {
                userName = "USERENG";
            } else if (user == Roles.QC) {
                userName = "USERQC";
            }
            UserCredential userCred = new UserCredential(ConfigManager2.getProperty(userName),
                    ConfigManager2.getProperty("PASSWORD"));
            Response response = given().
                    baseUri(ConfigManager2.getProperty("BASE_URI")).
                    contentType(ContentType.JSON).
                    accept(ContentType.ANY).
                    body(userCred).
                    log().all().
                    when().
                    post("/login").
                    then().statusCode(200)
                    .extract().response();

            token = response.jsonPath().getString("data.token");
            tokenCache.put(user, token);
            return token;
        }
        else
            return  tokenCache.get(user);
    }
}
