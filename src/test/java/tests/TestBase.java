package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;

import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.config.AppConfig;
import tests.config.RemoteOwner;
import tests.helpers.AllureAttachments;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        AppConfig appConfig = ConfigFactory.create(AppConfig.class);
        Configuration.baseUrl = appConfig.webUrl();
        RestAssured.baseURI = appConfig.apiURI();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        RemoteOwner remoteOwner = ConfigFactory.create(RemoteOwner.class);

        String propertyBrowserSize = System.getProperty("browserSize", "1980x1024"),
                propertyRemoteUrl = System.getProperty("remoteUrl", remoteOwner.url());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        Configuration.browserSize = propertyBrowserSize;
        Configuration.remote = propertyRemoteUrl;

    }

    @AfterEach
    public void afterEach() {

        AllureAttachments.screenshotAs("Final screenshot");;
        AllureAttachments.pageSource();
        AllureAttachments.browserConsoleLogs();
        AllureAttachments.addVideo();

        closeWebDriver();

    }
}