package pages;

import components.SearchComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import helpers.PagesConstants;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

/**
 * Главная страница каталога Stepik.
 * Содержит методы для открытия страницы и выполнения поиска.
 */

public class HomePage extends BasePage<HomePage> {

    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private final SearchComponent search = SearchComponent.byClass(PagesConstants.HOME_SEARCH_COMPONENT_CLASS);

    public HomePage() {
        super($x(PagesConstants.HOME_PAGE_XPATH), HomePage.class);
    }

    public HomePage openPage() {
        logger.info(PagesConstants.OPEN_HOMEPAGE_LOG_MESSAGE);
        open(PagesConstants.HOME_PAGE_LINK);
        return this;
    }

    public SearchResultsPage searchFor(String text) {
    logger.info(PagesConstants.DO_SEARCH_LOG_MESSAGE, text);
        search.search(text);
        return page(SearchResultsPage.class);
    }
}