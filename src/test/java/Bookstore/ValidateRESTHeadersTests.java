package Bookstore;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

// https://demoqa.com/swagger/


public class ValidateRESTHeadersTests {

    @Test(priority=1)
    public void GetBooksDetails() {

        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://demoqa.com/Account/v1/User/";
        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.get("test");

        // Get the status code of the request.
        //If request is successful, status code will be 200
        int statusCode = response.getStatusCode();

        // Assert that correct status code is returned.
        assertEquals(statusCode /*actual value*/, 401 /*expected value*/);

    }

    @Test(priority=3)
    public void IteratingHeaders()
    { RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("");
        // Get all the headers and then iterate over allHeaders to print each header
        Headers allHeaders = response.headers();
        // Iterate over all the Headers
        for(Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }
    }

    @Test(priority=2)
    public void GetBookHeaders() {
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("");

        // Access header with a given name.
        String contentType = response.header("Content-Type");
        System.out.println("Content-Type value: " + contentType);

        // Access header with a given name.
        String serverType = response.header("Server");
        System.out.println("Server value: " + serverType);

        // Access header with a given name.
        String contentEncoding = response.header("Content-Encoding");
        System.out.println("Content-Encoding: " + contentEncoding);

        assertEquals(contentType /* actual value */, "application/json; charset=utf-8" /* expected value */);

        assertEquals(serverType /* actual value */, "nginx/1.17.10 (Ubuntu)" /* expected value */);
        assertEquals(contentEncoding, null);



    }
}


