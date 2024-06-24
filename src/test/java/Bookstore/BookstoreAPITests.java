package Bookstore;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class BookstoreAPITests {

    @Test
    public void GetBooksDetails() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";

        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.get("");

        // System.out.println("Status received => " + response.getStatusLine());
        // System.out.println("Response=>" + response.asString());

        int statusCode = response.getStatusCode();

        Assert.assertEquals(statusCode, 200);


    }
}