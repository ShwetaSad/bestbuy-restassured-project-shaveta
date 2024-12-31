package com.bestbuy.crudtest;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class StoresCrudTest extends TestBase {


    static String name = TestUtils.getRandomValue() + "Sanay";
    static String type = TestUtils.getRandomValue() + "Christmas";
    static String address = TestUtils.getRandomValue() + "The Spinney";
    static String address2 = TestUtils.getRandomValue() + "";
    static String city = "Reading";
    static String state = "BerkShire";
    static String zip = "GB";
    static int storeid;

    @Test
    public void test001() {
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setState(state);
        storePojo.setCity(city);
        storePojo.setZip(zip);

        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(storePojo)
                .post();
        response.then().log().ifValidationFails().statusCode(201);
    }

    @Test
    public void test002() {
        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";

        ValidatableResponse response = given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .get()
                .then().log().all().statusCode(200);
        String jsonPath = "findAll{it.name == '" + name + "'}.get(0)";
        HashMap<String, Object> storeMap = response.extract().path(jsonPath);
        if (storeMap != null) {
            storeid = (int) storeMap.get("id");
            System.out.println("Store Id: " + storeid);
        }
    }

    @Test
    public void test003(){

        String firstName = StoresCrudTest.name + "_Updated";

        StorePojo storePojo = new StorePojo();
        storePojo.setName(firstName);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);

        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", storeid)
                .when()
                .body(storePojo)
                .put(EndPoints.UPDATE_STORE_BY_ID);
        response.then().log().ifValidationFails().statusCode(200);

    }


    @Test
    public void test004() {
        given().log().all()
                .pathParam("id", storeid)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then().log().all()
                .statusCode(204);

        given().log().all()
                .pathParam("id", storeid)
                .when()
                .get(EndPoints.UPDATE_STORE_BY_ID)
                .then().log().all().statusCode(404);
    }
    }


