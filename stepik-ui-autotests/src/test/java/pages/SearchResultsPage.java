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
        } else {
        }
    }

    /**
     * Получает карточку курса по индексу.
     */
    public CourseCardItem getCourseCard(int index) {
        try {
            ElementsCollection cards = $$(".course-card");
            cards.shouldHave(CollectionCondition.sizeGreaterThan(index), Duration.ofSeconds(20));
            return new CourseCardItem(cards.get(index));
        } catch (AssertionError e) {
            logger.warn("Карточка курса с индексом {} не найдена: {}", index, e.getMessage());
            return null;
        }
    }

    /**
     * Переходит на следующую страницу результатов.
     */
    public SearchResultsPage clickNext() {
        logger.info("Переход на следующую страницу результатов");
        closeCookieBanner();

        try {
            paginationBlock.scrollTo();

            nextButton.getElement().shouldBe(visible, Duration.ofSeconds(10));
            nextButton.click();

            $$(".course-card").shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));
        } catch (AssertionError e) {
            logger.warn("Ошибка при переходе на следующую страницу: {}", e.getMessage());
        }

        return this;
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
            logger.warn("Не удалось открыть первый курс: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Применяет фильтр по чекбоксу.
     */
    public SearchResultsPage applyCheckBoxFilter(String filterType, String value) {
        filter.filterByCheckBox(filterType, value);
        return this;
    }

    /**
     * Применяет фильтр по минимальной цене.
     */
    public SearchResultsPage applyMinPriceFilter(String minValue) {
        logger.info("Применение минимальной цены: {}", minValue);
        filter.filterByMinPrice(minValue);
        return this;
    }

    /**
     * Применяет фильтр по максимальной цене.
     */
    public SearchResultsPage applyMaxPriceFilter(String maxValue) {
        logger.info("Применение максимальной цены: {}", maxValue);
        filter.filterByMaxPrice(maxValue);
        return this;
    }

    /**
     * Возвращает заголовок первого курса.
     */
    public String getFirstCourseTitle() {
        try {
            webdriver().shouldHave(WebDriverConditions.urlContaining("search"), Duration.ofSeconds(15));
            String title = getCourseCard(0).getTitle().getText();
            return title;
        } catch (AssertionError e) {
            logger.warn("Не удалось получить заголовок первого курса: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Нажимает на кнопку очистки поиска ("крестик").
     */
    public SearchResultsPage clickClear() {
        clearButton.getElement().click(ClickOptions.usingJavaScript());
        return this;
    }

    /**
     * Выполняет новый поиск на странице результатов.
     */
    public SearchResultsPage searchAgain(String text) {
        logger.info("Повторный поиск по запросу: '{}'", text);
        centralSearch.search(text);
        return this;
    }

    /**
     * Применяет тумблер-фильтр.
     */
    public SearchResultsPage applyTogglerFilter(String togglerType) {
        logger.info("Применение тумблера: {}", togglerType);
        filter.filterByToggler(togglerType);
        return this;
    }

    /**
     * Возвращает текущее значение в поле поиска.
     */
    public String getSearchInputValue() {
        String value = centralSearch.getCurrentInputValue();
        return value;
    }

    /**
     * Проверяет, отображается ли сообщение "ничего не найдено".
     */
    public boolean isNothingFoundMessageVisible() {
        try {
            $x("//body").shouldHave(Condition.text("ничего не найдено"), Duration.ofSeconds(15));
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