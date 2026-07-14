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
    private final Button clearButton = Button.byClass("search-form__reset");

    // Компонент центрального поля поиска
    private final SearchComponent centralSearch = SearchComponent.byClass("catalog-w__search-form");

    // Компонент всех фильтров
    private final FilterComponent filter = FilterComponent.byClass("search-form-filters catalog-w__filters");

    // Кнопка "Далее" для пагинации
    private final Button nextButton = Button.byName("Далее");

    // Кнопка закрытия баннера cookie
    private final SelenideElement cookieButton = $x("//button[contains(text(), 'Хорошо')]");

    // Блок пагинации
    private final SelenideElement paginationBlock = $(".catalog__paging");

    public SearchResultsPage() {
        super($x("//h1[text()='Поиск']"), SearchResultsPage.class);
    }

    /**
     * Закрывает баннер cookie, если он появился на странице.
     */
    public void closeCookieBanner() {
        if (cookieButton.exists()) {
            cookieButton.click();
            logger.info("Баннер cookie закрыт");
        }
    }

    /**
     * Получает карточку курса по индексу.
     */
    public CourseCardItem getCourseCard(int index) {
        try {
            ElementsCollection cards = $$(".course-card");
            cards.shouldHave(CollectionCondition.sizeGreaterThan(index), Duration.ofSeconds(WAIT_SECONDS));
            return new CourseCardItem(cards.get(index));
        } catch (AssertionError e) {
            logger.warn("Карточка курса с индексом {} не найдена: {}", index, e.getMessage());
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

            ElementsCollection cards = $$(".course-card");

            cards.shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(WAIT_SECONDS));
        } catch (AssertionError e) {
            logger.error("Ошибка при переходе на следующую страницу: {}", e.getMessage());
        }
    }

    /**
     * Открывает первый курс из результатов поиска.
     */
    public CoursePage openFirstCourse() {
        logger.info("Открытие первого курса");
        try {
            getCourseCard(0).getTitle().click();
            return page(CoursePage.class);
        } catch (AssertionError e) {
            logger.error("Не удалось открыть первый курс: {}", e.getMessage());
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
        logger.info("Применение минимальной цены: {}", minValue);
        filter.filterByMinPrice(minValue);
    }

    /**
     * Применяет фильтр по максимальной цене.
     */
    public void applyMaxPriceFilter(String maxValue) {
        logger.info("Применение максимальной цены: {}", maxValue);
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
            logger.error("Не удалось получить заголовок первого курса: {}", e.getMessage());
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
        logger.info("Повторный поиск по запросу: '{}'", text);
        centralSearch.search(text);
    }

    /**
     * Применяет тумблер-фильтр.
     */
    public void applyTogglerFilter(String togglerType) {
        logger.info("Применение тумблера: {}", togglerType);
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
            SelenideElement bodyOfPage = $x("//body");
            bodyOfPage.shouldHave(Condition.text("ничего не найдено"), Duration.ofSeconds(WAIT_SECONDS));
            return true;
        } catch (Throwable e) {
            boolean resetLinkVisible = $x("//*[contains(text(), 'Сбросить фильтры')]").exists();
            logger.error("Текст не найден. Видна ли кнопка 'Сбросить фильтры': {}", resetLinkVisible);
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