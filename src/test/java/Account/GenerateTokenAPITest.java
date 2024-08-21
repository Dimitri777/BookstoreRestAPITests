package Account;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GenerateTokenAPITest {

    @BeforeClass
    public void setup() {
        // Устанавливаем базовый URI для RestAssured
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
    }

    @Test(priority=1)
    public void testGenerateToken_Success() {
        // Создаем объект с валидными данными для авторизации
        String requestBody = "{ \"userName\": \"dimitri7778\", \"password\": \"helloWorld123$\" }";

        // Отправляем POST-запрос и получаем ответ
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .statusCode(200) // Ожидаем, что статус-код будет 200
                .extract().response();

        // Проверяем, что в ответе содержится токен
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should be present in the response.");

        // Проверяем, что статус успешен
        String status = response.jsonPath().getString("status");
        Assert.assertEquals(status, "Success", "The status should be 'Success'.");
    }

    @Test(priority=2)
    public void testGenerateToken_Failure() {
        // Создаем объект с некорректными данными для авторизации
        String requestBody = "{ \"userName\": \"dimitri777\", \"password\": \"invalidPassword\" }";

        // Отправляем POST-запрос и получаем ответ
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .statusCode(200) // Ожидаем, что статус-код будет 200 (но с неудачными данными)
                .extract().response();

        // Проверяем, что в ответе отсутствует токен
        String token = response.jsonPath().getString("token");
        Assert.assertNull(token, "Token should not be present in the response.");

        // Проверяем, что статус "Failed"
        String status = response.jsonPath().getString("status");
        Assert.assertEquals(status, "Failed", "The status should be 'Failed'.");

        // Проверяем сообщение об ошибке
        String result = response.jsonPath().getString("result");
        Assert.assertEquals(result, "User authorization failed.", "The error message should indicate a failed authorization.");
    }
}

