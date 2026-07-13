package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * Базовый класс для всех тестов.
 * Настраивает браузер и управляет его открытием/закрытием.
 */

public abstract class BaseTest {

    @BeforeAll
    static void setupBrowser() {

        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.baseUrl = "https://stepik.org/catalog";
    }


    @BeforeEach
    void openBrowser() {

        Selenide.open("/");
    }


    @AfterEach
    void closeBrowser() {

        Selenide.closeWebDriver();
    }
}