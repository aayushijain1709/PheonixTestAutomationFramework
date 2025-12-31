package com.api.test;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

import java.io.File;

public class BaseTest {
    @BeforeTest
    public void setup()
    {
        RestAssured.baseURI = "http://64.227.160.186:9000/v1";
    }
}
