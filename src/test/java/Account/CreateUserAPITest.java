package Account;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserAPITest {

    @BeforeClass
    public void setup() {
        // Устанавливаем базовый URI для RestAssured
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
    }
    @Test
    public void testCreateUser() {
        // Установка базового URL для RestAssured
        String requestBody = "{ \"userName\": \"dimitri7778\", \"password\": \"helloWorld123$\" }";

        // Отправляем POST-запрос и получаем ответ
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/Account/v1/User")
                .then()
                .statusCode(201) // Ожидаем, что статус-код будет 200
                .extract().response();

        // Проверка, что код ответа 201 (успех)
        response.then().statusCode(201);

        // Дополнительные проверки (например, проверка содержимого ответа)
        response.then().body("username", equalTo("dimitri7778"));

    }
}
