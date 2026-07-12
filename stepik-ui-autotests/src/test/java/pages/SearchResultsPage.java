package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ClickOptions;
import components.Button;
import components.CourseCardItem;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverConditions;
import components.FilterComponent;
import components.SearchComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SearchResultsPage extends BasePage<SearchResultsPage> {

    private static final Logger logger = LogManager.getLogger(SearchResultsPage.class);

    // Кнопка очистки поиска ("крестик")
    private final Button clearButton = Button.byClass("search-form__reset");

    // Кнопка "Бесплатно"
    private final Button freeFilter = Button.byName("Бесплатно");

    // Компонент центрального поля поиска
    private final SearchComponent centralSearch = SearchComponent.byClass("catalog-w__search-form");

    // Компонент всех фильтров
    private final FilterComponent filter = FilterComponent.byClass("search-form-filters catalog-w__filters");

    // Кнопка "Далее" для пагинации
    private final Button nextButton = Button.byName("Далее");

    public SearchResultsPage() {
        super($x("//h1[text()='Поиск']"), SearchResultsPage.class);
        logger.debug("Страница результатов поиска инициализирована");
    }

    public void closeCookieBanner() {
        logger.debug("Проверка наличия баннера cookie");
        com.codeborne.selenide.SelenideElement cookieButton = com.codeborne.selenide.Selenide.$x("//button[contains(text(), 'Хорошо')]");
        if (cookieButton.exists()) {
            cookieButton.click();
            logger.info("Баннер cookie закрыт");
        } else {
            logger.debug("Баннер cookie не найден");
        }
    }

    /**
     * Получает карточку курса по индексу
     */
    public CourseCardItem getCourseCard(int index) {
        logger.debug("Получение карточки курса с индексом: {}", index);
        try {
            ElementsCollection cards = $$(".course-card");
            cards.shouldHave(CollectionCondition.sizeGreaterThan(index), Duration.ofSeconds(20));
            logger.debug("Карточка курса с индексом {} получена", index);
            return new CourseCardItem(cards.get(index));
        } catch (AssertionError e) {
            logger.warn("Карточка курса с индексом {} не найдена: {}", index, e.getMessage());
            return null;
        }
    }

    /**
     * Переход на следующую страницу результатов
     */
    public SearchResultsPage clickNext() {
        logger.info("Переход на следующую страницу результатов");
        closeCookieBanner();

        try {
            $(".catalog__paging").scrollTo();
            logger.debug("Прокрутка к пагинации");

            nextButton.getElement().shouldBe(visible, Duration.ofSeconds(10));
            nextButton.click();
            logger.info("Кнопка 'Далее' нажата");

            $$(".course-card").shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));
            logger.info("Переход на следующую страницу выполнен");
        } catch (AssertionError e) {
            logger.warn("Ошибка при переходе на следующую страницу: {}", e.getMessage());
        }

        return this;
    }

    /**
     * Открывает первый курс из результатов поиска
     */
    public CoursePage openFirstCourse() {
        logger.info("Открытие первого курса");
        try {
            getCourseCard(0).getTitle().click();
            logger.info("Первый курс открыт");
            return page(CoursePage.class);
        } catch (AssertionError e) {
            logger.warn("Не удалось открыть первый курс: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Применяет фильтр по чекбоксу
     */
    public SearchResultsPage applyCheckBoxFilter(String filterType, String value) {
        logger.info("Применение чекбокса: {} = {}", filterType, value);
        filter.filterByCheckBox(filterType, value);
        logger.info("Чекбокс {} = {} применен", filterType, value);
        return this;
    }

    /**
     * Применяет фильтр по минимальной цене
     */
    public SearchResultsPage applyMinPriceFilter(String minValue) {
        logger.info("Применение минимальной цены: {}", minValue);
        filter.filterByMinPrice(minValue);
        logger.info("Минимальная цена применилась");
        return this;
    }

    /**
     * Применяет фильтр по максимальной цене
     */
    public SearchResultsPage applyMaxPriceFilter(String maxValue) {
        logger.info("Применение максимальной цены: {}", maxValue);
        filter.filterByMaxPrice(maxValue);
        logger.info("Максимальная цена применилась");
        return this;
    }

    /**
     * Возвращает заголовок первого курса
     */
    public String getFirstCourseTitle() {
        logger.debug("Получение заголовка первого курса");
        try {
            webdriver().shouldHave(WebDriverConditions.urlContaining("search"), Duration.ofSeconds(15));
            String title = getCourseCard(0).getTitle().getText();
            logger.debug("Заголовок первого курса: '{}'", title);
            return title;
        } catch (AssertionError e) {
            logger.warn("Не удалось получить заголовок первого курса: {}", e.getMessage());
            return "";
        }
    }

    // Возвращает имя первого автора из первой карточки курса
    public String getFirstCourseAuthor() {
        logger.debug("Получение имени первого автора");
        try {
            webdriver().shouldHave(WebDriverConditions.urlContaining("search"), Duration.ofSeconds(15));
            return getCourseCard(0).getAuthors().get(0).getText();
        } catch (AssertionError e) {
            logger.warn("Не удалось получить имя первого автора: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Нажимает на кнопку очистки поиска ("крестик").
     */
    public SearchResultsPage clickClear() {
        logger.info("Очистка поля поиска (нажатие на крестик через JS)");
        clearButton.getElement().click(ClickOptions.usingJavaScript());
        
        logger.info("Поле поиска очищено");
        return this;
    }
    /**
     * Выполняет поиск по новому запросу на странице результатов.
     * Заполняет центральное поле поиска и нажимает кнопку "Искать".
     */
    public SearchResultsPage searchAgain(String text) {
        logger.info("Повторный поиск по запросу: '{}'", text);
        centralSearch.search(text);
        logger.info("Повторный поиск выполнен");
        return this;
    }

    /**
     * Применяет тумблер-фильтр.
     */
    public SearchResultsPage applyTogglerFilter(String togglerType) {
        logger.info("Применение тумблера: {}", togglerType);
        filter.filterByToggler(togglerType);
        logger.info("Тумблер {} применен", togglerType);
        return this;
    }

    /**
     * Возвращает текущее значение в поле поиска.
     * Используется для проверки, что поле было очищено или содержит нужный текст.
     */
    public String getSearchInputValue() {
        String value = centralSearch.getCurrentInputValue();
        logger.debug("Текущее значение поля поиска: '{}'", value);
        return value;
    }

    public SearchResultsPage applyOnlyFreeFilter() {
        logger.info("Применение фильтра 'Бесплатно' через FilterComponent");
        filter.filterOnlyFree();
        logger.info("Фильтр 'Бесплатно' применен");
        return this;
    }

    public boolean isNothingFoundMessageVisible() {
        logger.info("Ожидание появления текста 'ничего не найдено' в теле страницы...");
        try {
            $x("//body").shouldHave(com.codeborne.selenide.Condition.text("ничего не найдено"), Duration.ofSeconds(15));
            
            logger.info("Текст успешно найден в теле страницы.");
            return true;
        } catch (Throwable e) {
            boolean resetLinkVisible = $x("//*[contains(text(), 'Сбросить фильтры')]").exists();
            logger.error("Текст не найден. Видна ли кнопка 'Сбросить фильтры': {}", resetLinkVisible);
            return resetLinkVisible;
        }
    }

    public boolean isFilterSelected(String type, String value) {
        return filter.isCheckBoxSelected(type, value);
    }

    public boolean firstCourseHasOldPrice() {
        return getCourseCard(0).hasOldPrice();
    }

    public boolean firstCourseIsFavorite() {
        return getCourseCard(0).isFavorite();
    }

    public void switchToNewWindow() {
        try {
            Selenide.Wait().until(d -> d.getWindowHandles().size() > 1);
            Selenide.switchTo().window(1);
        } catch (Exception e) {
            logger.warn("Новое окно не открылось, остаемся в текущем");
        }
    }
}