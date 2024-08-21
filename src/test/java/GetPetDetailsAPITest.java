import org.testng.Assert;   //used to validate response status
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class GetPetDetailsAPITest {

    @Test(priority=1)
    public void GetPetDetails() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://demoqa.com/Account/v1/User/";
        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.get("");

        int statusCode = response.getStatusCode();
        String statusline = response.getStatusLine();

        System.out.println("The status code is " + statusCode);
        System.out.println("Status line: " + statusline);

        // Assert that correct status code is returned.
        Assert.assertEquals(statusline /*actual value*/, "HTTP/1.1 200 OK" /*expected value*/,
                "Correct status code returned");
        Assert.assertEquals(statusCode, 200);
    }
}