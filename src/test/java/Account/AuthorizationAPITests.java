package Account;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthorizationAPITests {

    @BeforeClass
    public void setup() {
        // Устанавливаем базовый URI для RestAssured
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
    }

    @Test
    public void testAuthorizedEndpoint_Success() {
        // Создаем объект с данными для авторизации
        String requestBody = "{ \"userName\": \"dimitri7778\", \"password\": \"helloWorld123$\" }";

        // Отправляем POST-запрос и получаем ответ
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .statusCode(200) // Ожидаем, что статус-код будет 200
                .extract().response();

        // Проверяем, что в ответе содержится true
        Assert.assertTrue(response.as(Boolean.class), "Authorization should be successful.");
    }

    @Test
    public void testAuthorizedEndpoint_Failure() {
        // Создаем объект с некорректными данными для авторизации
        String requestBody = "{ \"userName\": \"dimitri777\", \"password\": \"invalidPassword\" }";

        // Отправляем POST-запрос и получаем ответ
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .statusCode(404) // Ожидаем, что статус-код будет 400
                .extract().response();

        // Проверяем, что в ответе содержится сообщение об ошибке
        String errorMessage = response.jsonPath().getString("message");
        Assert.assertNotNull(errorMessage, "Error message should be present for failed authorization.");
    }
}
