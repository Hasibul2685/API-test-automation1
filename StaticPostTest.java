package com.jsonServer;

import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StaticPostTest {
    String baseUrl = "http://localhost";

    @Test
    public void getPostsShouldSucceed() {

        given()
                .baseUri(baseUrl)
                .port(3000)
                .log().uri()
                .when()
                .get("/posts")
                .then()
                .log().body()
                .statusCode(200);
    }


    @Test
    public void getPostDetailShouldSucceed() {
        given()
                .baseUri(baseUrl)
                .port(3000)
                .log().uri()
                .when()
                .get("posts/1")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void postPostShouldSucceed() {
        String json = "{\n" +
                "  \"title\": \"json-server3\",\n" +
                "  \"author\": \"typicode3\"\n" +
                "}";

        given()
                .baseUri(baseUrl)
                .port(3000)
                .contentType(ContentType.JSON)
                .log().uri()
                .log().body()
                .body(json)
                .when()
                .post("/posts")
                .then()
                .log().body()
                .statusCode(201);

    }


    @Test
    public void postPostShouldSucceed2() {
        Map<String, String> json = new HashMap<>();
        json.put("title", LoremIpsum.getInstance().getTitle(5));
        json.put("author", LoremIpsum.getInstance().getName());

        given()
                .baseUri(baseUrl)
                .port(3000)
                .header("Content-Type", "application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .post("/posts")
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void updayePostShouldSucceed() {
        Map<String, String> json = new HashMap<>();
        json.put("title", LoremIpsum.getInstance().getTitle(5));
        json.put("author", LoremIpsum.getInstance().getName());

        given()
                .baseUri(baseUrl)
                .port(3000)
                .header("Content-Type", "application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .put("/posts/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(2));

    }

    @Test
    public void deletePostShouldSucceed() {
        given()
                .baseUri(baseUrl)
                .port(3000)
                .log().uri()
                .when()
                .delete("/posts/3")
                .then()
                .statusCode(200);
    }
}
