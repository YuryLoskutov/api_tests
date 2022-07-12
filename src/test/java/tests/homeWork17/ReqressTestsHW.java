package tests.homeWork17;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqressTestsHW {

    @Test
    void checkGetLastNamesFunkeTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.last_name", hasItem("Funke"));

    }

    @Test
    void checkGetSingleUserEmailTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.email", is("janet.weaver@reqres.in"));

    }

    @Test
    void checkGetSingleUserNotFoundTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body(is("{}"));

    }

    @Test
    void checkPostCreateUserTest() {
        String body = "{\"name\": \"morpheus\",\"job\":\"leader\" }";

        given()
                .log().uri()
                .log().body()
                .body(body).contentType(ContentType.JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);

    }
    @Test
    void checkPostRegisterUserTest() {
        String body = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";

        given()
                .log().uri()
                .log().body()
                .body(body).contentType(ContentType.JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }
}
