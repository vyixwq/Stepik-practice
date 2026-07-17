package pages;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import components.Button;
import components.CourseCardItem;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverConditions;

import components.FilterComponent;
import components.SearchComponent;
import helpers.PagesConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Страница результатов поиска на Stepik.
 * Содержит методы для работы с поиском, фильтрами, пагинацией и карточками курсов.
 */

public class SearchResultsPage extends BasePage<SearchResultsPage> {

    private static final Logger logger = LogManager.getLogger(SearchResultsPage.class);


    // Кнопка очистки поиска ("крестик")
    private final Button clearButton = Button.byClass(PagesConstants.CLEAR_BUTTON_CLASS);

    // Компонент центрального поля поиска
    private final SearchComponent centralSearch = SearchComponent.byClass(PagesConstants.SEARCH_CLASS);

    // Компонент всех фильтров
    private final FilterComponent filter = FilterComponent.byClass(PagesConstants.FILTER_CLASS);

    // Кнопка "Далее" для пагинации
    private final Button nextButton = Button.byName(PagesConstants.NEXT_BUTTON_NAME);

    // Кнопка закрытия баннера cookie
    private final SelenideElement cookieButton = $x(PagesConstants.COOKIE_BUTTON_XPATH);

    // Блок пагинации
    private final SelenideElement paginationBlock = $(PagesConstants.PAGINATION_BLOCK_CSS_SELECTOR);

    public SearchResultsPage() {
        super($x(PagesConstants.RESULTS_PAGE_XPATH), SearchResultsPage.class);
    }

    /**
     * Закрывает баннер cookie, если он появился на странице.
     */
    public void closeCookieBanner() {
        if (cookieButton.exists()) {
            cookieButton.click();
        }
    }

    /**
     * Получает карточку курса по индексу.
     */
    public CourseCardItem getCourseCard(int index) {
        try {
            ElementsCollection cards = $$(PagesConstants.COURSE_CARD_CSS_SELECTOR);
            cards.shouldHave(CollectionCondition.sizeGreaterThan(index), Duration.ofSeconds(WAIT_SECONDS));
            return new CourseCardItem(cards.get(index));
        } catch (AssertionError e) {
            logger.error(PagesConstants.GET_COURSE_CARD_ERR_MSG, index, e.getMessage());
            return null;
        }
    }

    /**
     * Переходит на следующую страницу результатов.
     */
    public void clickNext() {
        logger.info("Переход на следующую страницу результатов");
        closeCookieBanner();

        try {
            paginationBlock.scrollTo();

            nextButton.getElement().shouldBe(visible, Duration.ofSeconds(WAIT_SECONDS));
            nextButton.click();

            ElementsCollection cards = $$(PagesConstants.COURSE_CARD_CSS_SELECTOR);

            cards.shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(WAIT_SECONDS));
        } catch (AssertionError e) {
            logger.error(PagesConstants.CLICK_NEXT_ERR_MSG, e.getMessage());
        }
    }

    /**
     * Открывает первый курс из результатов поиска.
     */
    public CoursePage openFirstCourse() {
        logger.info(PagesConstants.OPEN_FIRST_COURSE_LOG_MSG);
        try {
            getCourseCard(0).getTitle().click();
            return page(CoursePage.class);
        } catch (AssertionError e) {
            logger.error(PagesConstants.OPEN_FIRST_COURSE_ERR_MSG, e.getMessage());
            return null;
        }
    }

    /**
     * Применяет фильтр по чекбоксу.
     */
    public void applyCheckBoxFilter(String filterType, String value) {
        filter.filterByCheckBox(filterType, value);
    }

    /**
     * Применяет фильтр по минимальной цене.
     */
    public void applyMinPriceFilter(String minValue) {
        logger.info(PagesConstants.APPLY_MIN_PRICE_FILTER_LOG_MSG, minValue);
        filter.filterByMinPrice(minValue);
    }

    /**
     * Применяет фильтр по максимальной цене.
     */
    public void applyMaxPriceFilter(String maxValue) {
        logger.info(PagesConstants.APPLY_MAX_PRICE_FILTER_LOG_MSG, maxValue);
        filter.filterByMaxPrice(maxValue);
    }

    /**
     * Возвращает заголовок первого курса.
     */
    public String getFirstCourseTitle() {
        try {
            webdriver().shouldHave(
                    WebDriverConditions.urlContaining("search"),
                    Duration.ofSeconds(WAIT_SECONDS)
            );
            return getCourseCard(0).getTitle().getText();
        } catch (AssertionError e) {
            logger.error(PagesConstants.GET_FIRST_TITLE_ERR_MSG, e.getMessage());
            return "";
        }
    }

    /**
     * Нажимает на кнопку очистки поиска ("крестик").
     */
    public void clickClear() {
        clearButton.getElement().click(ClickOptions.usingJavaScript());
    }

    /**
     * Выполняет новый поиск на странице результатов.
     */
    public void searchAgain(String text) {
        logger.info(PagesConstants.SEARCH_AGAIN_LOG_MSG, text);
        centralSearch.search(text);
    }

    /**
     * Применяет тумблер-фильтр.
     */
    public void applyTogglerFilter(String togglerType) {
        logger.info(PagesConstants.APPLY_TOGGLE_FILTER_LOG_MSG, togglerType);
        filter.filterByToggler(togglerType);
    }

    /**
     * Возвращает текущее значение в поле поиска.
     */
    public String getSearchInputValue() {
        return centralSearch.getCurrentInputValue();
    }

    /**
     * Проверяет, отображается ли сообщение "ничего не найдено".
     */
    public boolean isNothingFoundMessageVisible() {
        try {
            SelenideElement bodyOfPage = $x(PagesConstants.PAGE_BODY_XPATH);
            bodyOfPage.shouldHave(Condition.text(PagesConstants.NOTHING_RESULTS_STRING), Duration.ofSeconds(WAIT_SECONDS));
            return true;
        } catch (Throwable e) {
            boolean resetLinkVisible = $x(PagesConstants.RESET_BUTTON_XPATH).exists();
            logger.error(PagesConstants.IS_NOTHING_FOUND_ERR_MSG, resetLinkVisible);
            return resetLinkVisible;
        }
    }

    /**
     * Проверяет, активен ли указанный фильтр.
     */
    public boolean isFilterSelected(String type, String value) {
        return filter.isCheckBoxSelected(type, value);
    }

    /**
     * Проверяет, добавлен ли первый курс в избранное.
     */
    public boolean firstCourseIsFavorite() {
        return getCourseCard(0).isFavorite();
    }
}