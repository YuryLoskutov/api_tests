package tests;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DemowebshopTests {

    static String login = "qaguru@qa.guru",
            password = "qaguru@qa.guru1",
            authorizationCookieName = "NOPCOMMERCE.AUTH";

    @BeforeAll
    static void configureBaseUrl() {
        Configuration.baseUrl = "http://demowebshop.tricentis.com";
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
    }

    @AfterEach
    void afterEach() {
        closeWebDriver();
    }


    @Test
    @Tag("demowebshop")
    @DisplayName("Successful authorization to some demowebshop (UI)")
    void loginTest() {
        step("Open login page", () ->
                open("/login"));

        step("Fill login form", () -> {
            $("#Email").setValue(login);
            $("#Password").setValue(password)
                    .pressEnter();
        });

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }

    @Test
    @Tag("demowebshop")
    @DisplayName("Successful authorization to some demowebshop (UI)")
    void loginWithApiTest() {
        step("Get cookie and set it into browser", () -> {
        String authorizationCookiesValue = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .log().all()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract().cookie(authorizationCookieName);

        open("/Themes/DefaultClean/Content/images/logo.png");
        Cookie ck = new Cookie(authorizationCookieName, authorizationCookiesValue);
        WebDriverRunner.getWebDriver().manage().addCookie(ck);

        open("/");
        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }

}