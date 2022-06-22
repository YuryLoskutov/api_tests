package tests;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DemowebshopTests {

    static String login = "qaguru@qa.guru",
            password = "qaguru@qa.guru1",
            authCookieName = "NOPCOMMERCE.AUTH";

    @BeforeAll
    static void configure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

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
                    String authCookiesValue = given()
                            .contentType("application/x-www-form-urlencoded")
                            .formParam("Email", login)
                            .formParam("Password", password)
                            .log().all()
                            .when()
                            .post("/login")
                            .then()
                            .log().all()
                            .statusCode(302)
                            .extract().cookie(authCookieName);


                    step("Open minimal content", () ->
                            open("/Themes/DefaultClean/Content/images/logo.png"));

                    step("Set cookie to the browser", () -> {
                        Cookie authCookie = new Cookie(authCookieName, authCookiesValue);
                        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
                    });
                });

        step("Open main page", () ->
                open("/"));

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }

    @Test
    @Tag("demowebshop")
    @DisplayName("Successful authorization to some demowebshop (UI) with filter")
    void loginWithApiAndAllureListenerTest() {
        step("Get cookie and set it into browser", () -> {
                    String authCookiesValue = given()
                            //Добавляет логирование в Allure отчёт
                            .filter(new AllureRestAssured())
                            .contentType("application/x-www-form-urlencoded")
                            .formParam("Email", login)
                            .formParam("Password", password)
                            .log().all()
                            .when()
                            .post("/login")
                            .then()
                            .log().all()
                            .statusCode(302)
                            .extract().cookie(authCookieName);
                    step("Open minimal content", () ->
                            open("/Themes/DefaultClean/Content/images/logo.png"));
                    step("Set cookie to the browser", () -> {
                        Cookie authCookie = new Cookie(authCookieName, authCookiesValue);
                        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
                    });
                });

        step("Open main page", () ->
                open("/"));

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }

    @Test
    @Tag("demowebshop")
    @DisplayName("Successful authorization to some demowebshop (UI) with filter")
    void loginWithApiAndCustomListenerTest() {
        step("Get cookie and set it into browser", () -> {
                    String authCookiesValue = given()
                            //Добавляет катсомное логирование в отчёт, метод withCustomTemplates
                            .filter(withCustomTemplates())
                            .contentType("application/x-www-form-urlencoded")
                            .formParam("Email", login)
                            .formParam("Password", password)
                            .log().all()
                            .when()
                            .post("/login")
                            .then()
                            .log().all()
                            .statusCode(302)
                            .extract().cookie(authCookieName);
                    step("Open minimal content", () ->
                            open("/Themes/DefaultClean/Content/images/logo.png"));
                    step("Set cookie to the browser", () -> {
                        Cookie authCookie = new Cookie(authCookieName, authCookiesValue);
                        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
                    });
                });

        step("Open main page", () ->
                open("/"));

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }

}