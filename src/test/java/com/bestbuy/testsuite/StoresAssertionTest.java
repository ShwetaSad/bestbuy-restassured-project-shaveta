package com.bestbuy.testsuite;

import com.bestbuy.testbase.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class StoresAssertionTest {

    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
    }

    //1. Verify the if the total is equal to 1561
    @org.testng.annotations.Test
    public void test1() {
        response.body("total", equalTo(1584));
    }

    //2. Verify the if the stores of limit is equal to 10
    @org.testng.annotations.Test
    public void test2() {
        response.body("limit", equalTo(10));
    }

    //3. Check the single ‘Name’ in the Array list (Inver Grove Heights)
    @org.testng.annotations.Test
    public void test3() {
        response.body("data[1].name", equalTo("Inver Grove Heights"));
    }

    //4. Check the multiple ‘Names’ in the ArrayList (Roseville, Burnsville, Maplewood)
    @org.testng.annotations.Test
    public void test4() {
        response.body(("data.name"), hasItems("Roseville", "Burnsville", "Maplewood"));
    }

    //5. Verify the storied=7 inside storeservices of the third store of second services
    @org.testng.annotations.Test
    public void test5() {
        response.body("data[2].services[2].storeservices.storeId", equalTo(7));
    }

    //6. Check hash map values ‘createdAt’ inside storeservices map where store name = Roseville
    @org.testng.annotations.Test
    public void test6() {
        response.body("data[2].services.storeservices", hasItem(hasKey("createdAt")));

    }
    //7. Verify the state = MN of forth store

    @org.testng.annotations.Test
    public void test7() {
        response.body("data[3].state", equalTo("MN"));
    }

    //8. Verify the store name = Rochester of 9th store
    @org.testng.annotations.Test
    public void test8() {
        response.body("data[8].name", equalTo("Rochester"));
    }

    //9. Verify the storeId = 11 for the 6th store
    @org.testng.annotations.Test
    public void test9() {
        response.body("data[5].services.storeservices.storeId", hasItem(equalTo(11)));
    }

    //10. Verify the serviceId = 4 for the 7th store of forth service

    @Test
    public void test10() {
        response.body("data[6].services[3].storeservices.serviceId", equalTo(4));
    }


}
