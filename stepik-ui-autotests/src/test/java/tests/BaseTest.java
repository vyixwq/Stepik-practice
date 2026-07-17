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

    private static final String BROWSER_TYPE = "chrome";
    private static final long DEFAULT_TIMEOUT_MS = 10000;
    private static final String BASE_URL = "https://stepik.org/catalog";
    private static final String HOME_PAGE_PATH = "/";

    @BeforeAll
    static void setupBrowser() {

        Configuration.browser = BROWSER_TYPE;
        Configuration.timeout = DEFAULT_TIMEOUT_MS;
        Configuration.baseUrl = BASE_URL;
    }


    @BeforeEach
    void openBrowser() {

        Selenide.open(HOME_PAGE_PATH);
    }


    @AfterEach
    void closeBrowser() {

        Selenide.closeWebDriver();
    }
}