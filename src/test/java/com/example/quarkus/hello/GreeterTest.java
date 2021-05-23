package com.example.quarkus.hello;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import org.hamcrest.CoreMatchers;

@QuarkusTest
public class GreeterTest {

    @Test
    public void testHomeEndpoint() {
        given()
          .when().get("/")
          .then().assertThat()
            .statusCode(200)
            .body(CoreMatchers.containsString("Hello World! : IP"));
    }

    @Test
    public void testHeaders() {
        given()
          .when().get("/headers")
          .then().assertThat()
             .statusCode(200);
    }
}