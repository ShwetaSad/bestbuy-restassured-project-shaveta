package com.bestbuy.testsuite;

import com.bestbuy.testbase.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

public class ProductsExtractionTest {

    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        response = given()
                .when()
                .get("/products")
                .then().statusCode(200);
    }

    //21. Extract the limit
    @Test
    public void test1() {
        Integer limit = response.extract().path("limit");
        System.out.println("The Limit is " + limit);
    }

    //22. Extract the total
    @Test
    public void test2() {
        Integer total = response.extract().path("total");
        System.out.println("The Limit is " + total);
    }

    //23. Extract the name of 5th product
    @Test
    public void test3() {
        String name = response.extract().path("data[4].name");
        System.out.println("The name of 5th product is " + name);
    }

    //24. Extract the names of all the products
    @Test
    public void test4() {
        List<String> Names = response.extract().path("data.name");
        System.out.println("the names of all the products are " + Names);
    }

    //25. Extract the productId of all the products
    @Test
    public void test5() {
        List<Integer> productId = response.extract().path("data.id");
        System.out.println("the productId of all the products are " + productId);

    }

    //26. Print the size of the data list
    @Test
    public void test6() {

        List<HashMap<String, ?>> data = response.extract().path("data");
        System.out.println("The size of the data List is " + data.size());
    }

    //27. Get all the value of the product where product name = Energizer - MAX Batteries AA (4-
    //Pack)
    @Test
    public void test7() {
        List<HashMap<String, ?>> value = response.extract().path("data.findAll{it.name == 'Energizer - MAX Batteries AA (4-Pack)'}");
        System.out.println("the value of the product where product name = Energizer - MAX Batteries AA (4-Pack)" + value);

    }

    //28. Get the model of the product where product name = Energizer - N Cell E90 Batteries (2-Pack)
    @Test
    public void test8() {
        String model = response.extract().path("data[8].model");
        //  String model = response.extract().path("data.findAll{it.name == 'Energizer - N Cell E90 Batteries (2-Pack)'}.model");
        System.out.println("the model of the product where product name = Energizer - N Cell E90 Batteries (2-Pack) is " + model);
    }

    //29. Get all the categories of 8th products
    @Test
    public void test9() {
        List<HashMap<String, ?>> categories = response.extract().path("data[7].categories");
        System.out.println("the categories of 8th products are" + categories);

    }

    //30. Get categories of the store where product id = 150115
    @Test
    public void test10() {
        //  List<HashMap<String,?>> categories = response.extract().path("data[3].categories");
        List<HashMap<String, ?>> categories = response.extract().path("data.findAll{it.id == 150115}.categories");
        System.out.println("categories of the store where product id = 150115 are" + categories);

    }

    //31. Get all the descriptions of all the products
    @Test
    public void test11() {
        List<String> discr = response.extract().path("data.description");
        System.out.println(" the descriptions of all the products are " + discr);
    }

    //32. Get id of all the all categories of all the products
    @Test
    public void test12() {
        List<Integer> ids = response.extract().path("data.categories.id");
        System.out.println(" id of all the all categories of all the products are" + ids);
    }

    //33. Find the product names Where type = HardGood
    @Test
    public void test13() {
        List<String> productnames = response.extract().path("data.findAll{it.type == 'HardGood'}.name");
        System.out.println("the product names Where type = HardGood are " + productnames);
    }

    //34. Find the Total number of categories for the product where product name = Duracell - AA
    //1.5V CopperTop Batteries (4-Pack)
    @Test
    public void test14(){
        List<HashMap<String, ?>> categories = response.extract().path("data.findAll{it.name == 'Duracell - AA 1.5V CopperTop Batteries (4-Pack)'}.categories");
        System.out.println("the Total number of categories for the product where product name = Duracell - AA 1.5V CopperTop Batteries (4-Pack)" + categories.size());

    }

    //35. Find the createdAt for all products whose price < 5.49
    @Test
    public void test15() {
        List<String> createsAt =response.extract().path("data.findAll{it.price < 5.49}.createdAt");
        System.out.println("the createdAt for all products whose price < 5.49 are "+ createsAt);

    }

    //36. Find the name of all categories Where product name = “Energizer - MAX Batteries AA (4-
    //Pack)”
    @Test
    public void test16(){
        List<String> name = response.extract().path("data.findAll{it.name == 'Energizer - MAX Batteries AA (4-Pack)'}.categories.name");
        System.out.println("the name of all categories Where product name = “Energizer - MAX Batteries AA (4-PAck) are "+name);


    }

    //37. Find the manufacturer of all the products
    @Test
    public void test17(){
        List<String> m = response.extract().path("data.manufacturer");
        System.out.println("the manufacturer of all the products are"+ m);

    }

    //38. Find the imge of products whose manufacturer is = Energizer
    @Test
    public void test18(){
       List<String> image = response.extract().path("data.findAll{it.manufacturer == 'Energizer'}.image");
        System.out.println("the image of products whose manufacturer is = Energizer is "+ image);


    }

    //39. Find the createdAt for all categories products whose price > 5.99
    @Test
    public  void test19(){
        List<String> createsAt =response.extract().path("data.findAll{it.price > 5.49}.createdAt");
        System.out.println("the createdAt for all products whose price > 5.49 are "+ createsAt);

    }


    //40. Find the uri of all the products
    @Test
    public void test20(){
        List<String> url = response.extract().path("data.url");
        System.out.println("the url of all the products is"+ url);


    }

}
