package Bookstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


// https://www.toolsqa.com/rest-assured/rest-api-test-using-rest-assured/

public class BookstoreAPITests {

    // Swagger: https://bookstore.demoqa.com/
    private static final String BASE_URL = "https://bookstore.demoqa.com";

    @Test(priority=1)
    public void GetAllBooksDetails() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";

        RequestSpecification httpRequest = given();

        Response response = httpRequest.get("");


        System.out.println("Status received => " + response.getStatusLine());
        System.out.println("Response=>" + response.asString());

        int statusCode = response.getStatusCode();

        assertEquals(statusCode, 200);
        assertNotNull(response.getBody());

        System.out.println(response.getBody().prettyPrint());


    }

    @Test(priority=2)
    public void testGetBookByISBN() {
        // Указание ISBN книги
        String isbn = "9781449325862";

        // Выполнение GET-запроса
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("ISBN", isbn)
                .accept("application/json")
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .statusCode(200)  // Проверка, что статус-код 200 OK
                .extract()
                .response();

        // Проверка тела ответа
        String responseBody = response.getBody().asString();
        assertNotNull(responseBody);
        int responseStatus = response.getStatusCode();

        // Дополнительные проверки (например, проверка полей JSON-ответа)
        String returnedIsbn = response.jsonPath().getString("isbn");
        assertEquals(returnedIsbn, isbn);

        // Проверка других полей книги (например, заголовок и автор)
        String title = response.jsonPath().getString("title");
        String subTitle = response.jsonPath().get("subTitle");
        String author = response.jsonPath().getString("author");
        String publishDate = response.jsonPath().getString("publish_date");
        String publisher = response.jsonPath().getString("publisher");
        int pages = response.jsonPath().getInt("pages");
        String description = response.jsonPath().getString("description");
        String website = response.jsonPath().getString("website");

        assertNotNull(title, "Название книги не должно быть null");
        assertNotNull(author, "Автор книги не должен быть null");
        assertEquals(responseStatus, 200);

        assertEquals(title, "Git Pocket Guide");
        assertEquals(subTitle, "A Working Introduction");
        assertEquals(author,"Richard E. Silverman");
        assertEquals(publishDate, "2020-06-04T08:48:39.000Z");
        assertEquals(publisher, "O'Reilly Media");
        assertEquals(pages, 234);
        assertTrue(description.contains("This pocket guide is the perfect on-the-job companion to Git,"));
        assertEquals(website, "http://chimera.labs.oreilly.com/books/1230000000561/index.html");

    }

    @Test
    public void testAddBookToCollection() {
        // Данные для запроса
        String userId = "user123"; // Используйте реальный userId
        String isbn = "9781449325862"; // Используйте реальный ISBN

        // Создание тела запроса
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", userId);

        // Создание списка книг
        Map<String, String> book = new HashMap<>();
        book.put("isbn", isbn);

        // Добавление списка книг в тело запроса
        requestBody.put("collectionOfIsbns", new Map[]{book});

        // Выполнение POST-запроса
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()  // Логирование запроса
                .when()
                .post("/BookStore/v1/Books/isbn")
                .then()
                .log().all()  // Логирование ответа
                .extract()
                .response();

        // Проверка статус-кода ответа
        int statusCode = response.getStatusCode();
        assertEquals(201, statusCode);

        // Проверка тела ответа
        String responseBody = response.getBody().asString();
        assertNotNull(responseBody);

        // Дополнительные проверки (например, проверка полей JSON-ответа)
        String returnedIsbn = response.jsonPath().getString("collectionOfIsbns[0].isbn");
        assertEquals(isbn, returnedIsbn, "ISBN в ответе не соответствует ожидаемому значению");
    }

}


