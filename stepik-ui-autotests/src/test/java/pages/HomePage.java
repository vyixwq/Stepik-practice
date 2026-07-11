package pages;

import components.SearchComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class HomePage extends BasePage<HomePage> {

    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private final SearchComponent search = SearchComponent.byClass("catalog__search-form");

    public HomePage() {
        super($x("//h2[@class='catalog-block__title']"), HomePage.class);
        logger.debug("Главная страница инициализирована");
    }

    public HomePage openPage() {
        logger.info("Открытие главной страницы Stepik");
        open("https://stepik.org/catalog");
        logger.info("Главная страница открыта");
        return this;
    }

    public SearchResultsPage searchFor(String text) {
        logger.info("Поиск по запросу: '{}'", text);
        search.search(text);
        logger.info("Поиск выполнен");
        return page(SearchResultsPage.class);
    }
}