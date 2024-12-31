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
    static int id;


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


        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(productPojo)
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
            id = (int) storeMap.get("id");
            System.out.println("Store Id: " + id);
        }
    }

    @Test
    public void test003(){

        String firstName = ProductCrudTest.name + "_Updated";

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setDescription(description);

        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .when()
                .body(productPojo)
                .put(EndPoints.UPDATE_PRODUCTS_BY_ID);
        response.then().log().ifValidationFails().statusCode(200);

    }


    @Test
    public void test004() {
        given().log().all()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then().log().all()
                .statusCode(204);

        given().log().all()
                .pathParam("id", id)
                .when()
                .get(EndPoints.UPDATE_PRODUCTS_BY_ID)
                .then().log().all().statusCode(404);
    }
}




