package pages;

import components.SearchComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

/**
 * Главная страница каталога Stepik.
 * Содержит методы для открытия страницы и выполнения поиска.
 */

public class HomePage extends BasePage<HomePage> {

    private static final Logger logger = LogManager.getLogger(HomePage.class);

    // Блок поиска на главной странице
    private final SearchComponent search = SearchComponent.byClass("catalog__search-form");

    public HomePage() {
        super($x("//h2[@class='catalog-block__title']"), HomePage.class);
    }

    public HomePage openPage() {
        logger.info("Открытие главной страницы Stepik");
        open("https://stepik.org/catalog");
        return this;
    }

    public SearchResultsPage searchFor(String text) {
        logger.info("Поиск по запросу: '{}'", text);
        search.search(text);
        return page(SearchResultsPage.class);
    }
}