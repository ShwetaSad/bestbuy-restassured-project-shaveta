package com.bestbuy.testsuite;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.skyscreamer.jsonassert.comparator.JSONCompareUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StoresExtractionTest {
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

    ////1. Extract the limit
    @Test
    public void test001() {
        int limit = response.extract().path("limit");
        System.out.println("The value of limit is : " + limit);

    }

    ////2. Extract the total
    @Test
    public void test2() {
        int total = response.extract().path("total");
        System.out.println("The total is " + total);
    }

    ////3. Extract the name of 5th store
    @Test
    public void test3() {
        String name = response.extract().path("data[4].name");
        System.out.println("The name of 5th store is " + name);
    }

    ////4. Extract the names of all the store
    @Test
    public void test4() {
        List<String> listOfStores = response.extract().path("data.name");
        System.out.println("The names of all the stores are" + listOfStores);
    }

    ////5. Extract the storeId of all the store
    @Test
    public void test5() {
        List<Integer> storeId = response.extract().path("data.services.storeservices.storeId");
        System.out.println("The StoreIds of all the stores are" + storeId);
    }

    ////6. Print the size of the data list
    @Test
    public void xtest6() {
        List<String> size = response.extract().path("data");
        System.out.println("The size of data is " + size.size());
    }

    ////7. Get all the value of the store where store name = St Cloud
    @Test
    public void test7() {
        List<HashMap<String, ?>> value = response.extract().path("data.findAll{it.name  == 'St Cloud'}");
        System.out.println("The value of the Store " + value);
    }

    ////8. Get the address of the store where store name = Rochester
    @Test
    public void test8() {
        String address = response.extract().path("data[8].address");
        System.out.println("The address of Store Rochester is " + address);
    }

    ////9. Get all the services of 8th store
    @Test
    public void test9() {
        List<HashMap<String, ?>> services = response.extract().path("data[7].services");
        System.out.println("The services of the 8th Store are:" + services);
    }

    ////10. Get storeservices of the store where service name = Windows Store
    @Test
    public void test10() {
        List<HashMap<String, ?>> storeservices = response.extract().path("data.findAll{it.name.contains('Windows Store')}.storeservices");
        System.out.println("The Storeservices of the stores with service name 'WindowsStore' is :" + storeservices);

    }

    ////11. Get all the storeId of all the store
    @Test
    public void test11() {
        List<Integer> listOfStoreId = response.extract().path("data.services.storeservices.storeId");
        System.out.println("The List of StoreIds of all the Stores is" + listOfStoreId);
    }

    ////12. Get id of all the store
    @Test
    public void test12() {
        List<Integer> id = response.extract().path("data.id");
        System.out.println("The ids of all the stores are" + id);
    }

    ////13. Find the store names Where state = ND
    @Test
    public void test13() {
        List<String> storenames = response.extract().path("data.findAll{it.state == 'ND'}.services.name");
        System.out.println("The Storenames where state = 'ND' are " + storenames);

    }

    ////14. Find the Total number of services for the store where store name = Rochester
    @Test
    public void test14() {
        List<HashMap<String, ?>> services = response.extract().path("data[8].services");
        // List<HashMap<String,?>> services = response.extract().path("data.findAll{it.name == 'Rochester'}.services");
        System.out.println("The total number of services for the store where store name = Rochester are " + services.size());
    }

    ////15. Find the createdAt for all services whose name = “Windows Store”
    @Test
    public void test15() {
        List<String> createdAt = response.extract().path("data.findAll{it.name == 'Windows Store'}.storeservices.createdAt");
        System.out.println("the createdAt for all services whose name = Windows Store" + createdAt);
    }

    ////16. Find the name of all services Where store name = “Fargo”
    @Test
    public void test16() {
        List<String> names = response.extract().path("data[7].services.name");
        // List<String> names = response.extract().path("data.findAll{it.name == 'Fargo'}.services.name");
        System.out.println("the name of all services Where store name = “Fargo" + names);
    }

    ////17. Find the zip of all the store
    @Test
    public void test17() {
        List<Long> zip = response.extract().path("data.zip");
        System.out.println("The zip of all the stores is " + zip);

    }

    ////18. Find the zip of store name = Roseville
    @Test
    public void test18() {
        String zip1 = response.extract().path("data[2].zip");

        // String zip1 = response.extract().path("data.findAll{it.name == 'Roseville'}.zip");
        System.out.println(zip1);
    }

    ////19. Find the storeservices details of the service name = Magnolia Home Theater
    @Test
    public void test19() {
        List<HashMap<String, ?>> storeservices = response.extract().path("data.findAll{it.name == 'Magnolia Home Theater'}.storeservices");
        System.out.println("The Storeservices of the stores with service name 'Magnolia Home Theater' is :" + storeservices);

    }

    ////20. Find the lat of all the stores
    @Test
    public void test20() {
        List<Double> lat = response.extract().path("data.lat");
        System.out.println("The lat of all the stores is " + lat);
    }
    //
    //}
}
