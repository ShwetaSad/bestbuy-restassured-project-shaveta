package com.bestbuy.crudtest;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.model.ProductPojo;
import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ProductCrudTest  {

    @BeforeClass
    public void inIt(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        RestAssured.basePath = "/products";
    }

    static String name = TestUtils.getRandomValue() + "Batteries";
    static String type = TestUtils.getRandomValue() + "Lithium";
    static double price = 5.40;
    static int shipping = 0;
    static String description = "Compatable";
    static String manufacturer = "Sanay";
    static String model = "Wr345";
    static String upc = "344854";
    static int ProductId = 0;


    @Test
    public void test001() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setModel(model);


        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .when()
                .body(productPojo)
                .post()
        .then().log().ifValidationFails().statusCode(201);
        ProductId = response.extract().path("id");
        System.out.println("product id is : " + ProductId);


    }
    @Test
    public void test002() {
        ValidatableResponse response = given().log().ifValidationFails()
                .pathParam("id", ProductId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCTS_BY_ID)
                .then().log().ifValidationFails().statusCode(200);

        ProductId = response.extract().path("id");
        System.out.println("product id is : " + ProductId);

    }

    @Test
    public void test003(){

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name + "UpdatedName" + TestUtils.getRandomValue());
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setManufacturer(manufacturer);
        productPojo.setDescription(description);
        productPojo.setModel(model);


        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .pathParam("id", ProductId)
                .when()
                .body(productPojo)
                .put(EndPoints.UPDATE_PRODUCTS_BY_ID)
                .then().log().ifValidationFails().statusCode(200);
    }


    @Test
    public void test004() {
        given().log().ifValidationFails()
                .pathParam("id", ProductId)
                .when()
                .delete(EndPoints.DELETE_PRODUCTS_BY_ID)
                .then()
                .statusCode(200);

        given()
                .log()
                .ifValidationFails()
                .pathParam("id", ProductId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCTS_BY_ID)
                .then().log().ifValidationFails().statusCode(404);
    }


}




