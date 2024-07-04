package com.jsonServer;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DynamicPostTest {
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
        int id = given()
                .baseUri(baseUrl)
                .port(3000)
                .log().uri()
                .when()
                .get("/posts")
                .then()
//                .log().body()
                .statusCode(200)
                .extract().jsonPath().getInt("[0].id");

        given()
                .baseUri(baseUrl)
                .port(3000)
                .log().uri()
                .when()
                .get("posts/" + id)
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(id));
    }


    @Test
    public void postPostShouldSucceed() {
        String title = LoremIpsum.getInstance().getTitle(5);
        String author = LoremIpsum.getInstance().getName();

        Map<String, String> json = new HashMap<>();
        json.put("title", title);
        json.put("author", author);

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
                .statusCode(201)
                .body("author", equalTo(author))
                .body("title", equalTo(title));

    }

    @Test
    public void updatePostUsePutShouldSucceed() {
        int id = given()
                .baseUri(baseUrl)
                .port(3000)
                .log().uri()
                .when()
                .get("/posts")
                .then()
//                .log().body()
                .statusCode(200)
                .extract().jsonPath().getInt("[0].id");

        String title = LoremIpsum.getInstance().getTitle(5);
        String author = LoremIpsum.getInstance().getName();

        Map<String, String> json = new HashMap<>();
        json.put("title", title);
        json.put("author", author);

        given()
                .baseUri(baseUrl)
                .port(3000)
                .header("Content-Type", "application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .put("/posts/" + id)
                .then()
                .log().body()
                .statusCode(200)
                .body("author", equalTo(author))
                .body("title", equalTo(title))
                .body("id", equalTo(id));

    }

    @Test
    public void updatePostUsePatchShouldSucceed() {
        int id = given()
                .baseUri(baseUrl)
                .port(3000)
                .log().uri()
                .when()
                .get("/posts")
                .then()
//                .log().body()
                .statusCode(200)
                .extract().jsonPath().getInt("[0].id");

        String title = LoremIpsum.getInstance().getTitle(5);

        Map<String, String> json = new HashMap<>();
        json.put("title", title);


        given()
                .baseUri(baseUrl)
                .port(3000)
                .header("Content-Type", "application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .patch("/posts/" + id)
                .then()
                .log().body()
                .statusCode(200)
                .body("title", equalTo(title))
                .body("id", equalTo(id));

    }

    @Test
    public void deletePostShouldSucceed() {
        int id = given()
                .baseUri(baseUrl)
                .port(3000)
                .log().uri()
                .when()
                .get("/posts")
                .then()
//                .log().body()
                .statusCode(200)
                .extract().jsonPath().getInt("[0].id");

        given()
                .baseUri(baseUrl)
                .port(3000)
                .log().uri()
                .when()
                .delete("/posts/" + id)
                .then()
                .statusCode(200);
    }
}
