package com.api.test;

import static io.restassured.RestAssured.*;

import com.api.constant.Roles;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager2;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class MasterAPIRequestTest {
    @Test
    public void verifyMasterAPI()
    {
       Response response= given()
                .baseUri(ConfigManager2.getProperty("BASE_URI"))
                .header("Authorization", AuthTokenProvider.getToken(Roles.FD))
                .contentType(ContentType.JSON)
                .log().uri()
                .log().headers()
                .when()
                .post("/master")
                .then()
                .log().all()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
               .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"))
                .extract().response();
        String[] key ={"mst_oem","mst_model","mst_action_status","mst_warrenty_status","mst_platform"
                ,"mst_product","mst_role","mst_service_location","mst_problem","map_fst_pincode"};
            for(String currentKey : key)
            {
              List<Map<String,Object>> list= response.jsonPath().getList("data."+currentKey);
                Assert.assertNotNull(list);
                Assert.assertTrue(list.size()>0);

                if(!currentKey.equalsIgnoreCase("map_fst_pincode"))
                {
                for(Map<String,Object> mp :list)
                {
                    Assert.assertTrue(mp.containsKey("id") , currentKey+" missing id for item "+mp);
                }}
                }
            }

  @Test
  public void invalidTokenForMasterAPI()
  {
      given()
              .baseUri(ConfigManager2.getProperty("BASE_URI"))
              .contentType(ContentType.JSON)
              .log().uri()
              .log().headers()
              .when()
              .post("/master")
              .then()
              .log().all()
              .statusCode(401);
  }

    }

