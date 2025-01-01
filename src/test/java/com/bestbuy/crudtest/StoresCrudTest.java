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
import static org.assertj.core.api.BDDAssertions.then;

public class StoresCrudTest extends TestBase {


    static String name = TestUtils.getRandomValue() + "Sanay";
    static String type = TestUtils.getRandomValue() + "Christmas";
    static String address = TestUtils.getRandomValue() + "The Spinney";
    static String address2 = TestUtils.getRandomValue() + "";
    static String city = "Reading";
    static String state = "BerkShire";
    static String zip = "GB";
    static int storeId = 0;

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

        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .when()
                .body(storePojo)
                .post()
                .then()
                .log().ifValidationFails().statusCode(201);

        storeId = response.extract().path("id");
        System.out.println("store id is : " + storeId);
    }

    @Test
    public void test002() {
        ValidatableResponse response = given().log().ifValidationFails()
                .pathParam("id", storeId)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then().log().ifValidationFails().statusCode(200);

        storeId = response.extract().path("id");
        System.out.println("store id is : " + storeId);


    }

    @Test
    public void test003() {

        String firstName = StoresCrudTest.name + "_Updated";

        StorePojo storePojo = new StorePojo();
        storePojo.setName(firstName);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setState(state);
        storePojo.setCity(city);
        storePojo.setZip(zip);

        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .pathParam("id", storeId)
                .when()
                .body(storePojo)
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then().log().ifValidationFails().statusCode(200);

    }


    @Test
    public void test004() {
        given().log().ifValidationFails()
                .pathParam("id", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then()
                .statusCode(200);

        given()
                .log()
                .ifValidationFails()
                .pathParam("id", storeId)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then().log().ifValidationFails().statusCode(404);
    }

}
