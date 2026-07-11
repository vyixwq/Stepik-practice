package tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.CoursePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(SearchTests.class);

    @Test
    @DisplayName("1. Текстовый поиск и навигация по страницам")
    void testSearchAndPagination() {
        logger.info("=== ТЕСТ 1: Текстовый поиск и навигация по страницам ===");

        //ДЕЙСТВИЕ 1: Открыта главная страница Stepik
        HomePage homePage = new HomePage().openPage();

        //ДЕЙСТВИЕ 2: Выполнен поиск по значению «Python»
        SearchResultsPage resultsPage = homePage.searchFor("Python");

        //ДЕЙСТВИЕ 3: Выполнен переход на вторую страницу результатов
        resultsPage.clickNext();

        //ДЕЙСТВИЕ 4: Выбран первый курс на второй странице
        resultsPage.openFirstCourse();

        logger.info("Проверка заголовка открывшегося курса на содержание слова «Python»");
        Selenide.switchTo().window(1);
        String actualTitle = new CoursePage().getCourseTitle();
        
        Assertions.assertTrue(actualTitle.toLowerCase().contains("python"), 
                "Заголовок курса '" + actualTitle + "' должен содержать ключевое слово 'Python'");
        
        logger.info("=== ТЕСТ 1 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("2. Поиск с фильтрами")
    void testSearchWithFilters() {
        logger.info("=== ТЕСТ 2: Поиск с фильтрами ===");

        //ДЕЙСТВИЕ 1: Открыта главная страница Stepik
        HomePage homePage = new HomePage().openPage();

        //ДЕЙСТВИЕ 2: Выполнен поиск по значению «Linux»
        SearchResultsPage resultsPage = homePage.searchFor("Linux");

        //ДЕЙСТВИЕ 3: Отмечен чекбокс «Английский» в блоке «Язык»
        resultsPage.applyCheckBoxFilter("language", "en");

        //ДЕЙСТВИЕ 4: Отмечен чекбокс «Программа» в блоке «Тип обучения»"
        resultsPage.applyCheckBoxFilter("type", "spec");

        logger.info("Проверка отображения результатов фильтрации по Linux");
        String firstTitle = resultsPage.getFirstCourseTitle();
        
        Assertions.assertTrue(firstTitle.toLowerCase().contains("linux"), 
                "Первый найденный курс '" + firstTitle + "' должен содержать ключевое слово 'Linux'");
        
        logger.info("=== ТЕСТ 2 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("3. Фильтрация по ценовому диапазону")
    void testPriceFilter() {
        logger.info("=== ТЕСТ 3: Фильтрация по ценовому диапазону ===");

        //ДЕЙСТВИЕ 1: Открыта главная страница Stepik"
        HomePage homePage = new HomePage().openPage();

        //ДЕЙСТВИЕ 2: Выполнен поиск по значению «Java»"
        SearchResultsPage resultsPage = homePage.searchFor("Java");

        //ДЕЙСТВИЕ 3: Заполнено поле «Цена От» значением «5000»"
        resultsPage.applyMinPriceFilter("5000");

        //ДЕЙСТВИЕ 4: Заполнено поле «Цена До» значением «7000»"
        resultsPage.applyMaxPriceFilter("7000");

        logger.info("Проверка отображения результатов в ценовом диапазоне");
        String firstTitle = resultsPage.getFirstCourseTitle();
        
        Assertions.assertTrue(firstTitle.toLowerCase().contains("java"), 
                "Первый найденный курс в ценовом диапазоне '" + firstTitle + "' должен соответствовать запросу 'Java'");
        
        logger.info("=== ТЕСТ 3 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("4. Поиск для продвинутых с сертификатом")
    void testAdvancedSearchWithCertificate() {
        logger.info("=== ТЕСТ 4: Поиск для продвинутых с сертификатом ===");

        //ДЕЙСТВИЕ 1: Открыта главная страница Stepik
        HomePage homePage = new HomePage().openPage();

        //ДЕЙСТВИЕ 2: Выполнен поиск по значению «QA Automation»
        SearchResultsPage resultsPage = homePage.searchFor("QA Automation");

        //ДЕЙСТВИЕ 3: Отмечен чекбокс «Для профи» в блоке «Уровень сложности»
        resultsPage.applyCheckBoxFilter("difficulty", "hard");

        //ДЕЙСТВИЕ 4: Тумблер «Только с сертификатом» переведен в активное положение
        resultsPage.applyTogglerFilter("certificate");

        logger.info("Проверка отображения профессиональных курсов с сертификатами");
        String firstTitle = resultsPage.getFirstCourseTitle();
        
        Assertions.assertTrue(firstTitle.toLowerCase().contains("qa") || firstTitle.toLowerCase().contains("automation"),
                "Найденный курс '" + firstTitle + "' должен соответствовать тематике 'QA Automation'");
        
        logger.info("=== ТЕСТ 4 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("5. Сброс текстового поиска «крестиком»")
    void testResetSearchWithClearButton() {
        logger.info("=== ТЕСТ 5: Сброс текстового поиска «крестиком» ===");

        //ДЕЙСТВИЕ 1: Открыта главная страница Stepik
        HomePage homePage = new HomePage().openPage();

        //ДЕЙСТВИЕ 2: Выполнен поиск по значению «C++»
        SearchResultsPage resultsPage = homePage.searchFor("C++");

        //ДЕЙСТВИЕ 3: Выполнен переход на вторую страницу результатов
        resultsPage.clickNext();

        //ДЕЙСТВИЕ 4: Нажата иконка «крестик» внутри поля поиска
        resultsPage.clickClear();

        logger.info("Проверка, что поле поиска очищено, а выдача сбросилась");
        String searchInputValue = resultsPage.getSearchInputValue();
        
        Assertions.assertTrue(searchInputValue == null || searchInputValue.isEmpty(), 
                "Поле поиска должно быть пустым после нажатия на крестик!");
        
        String firstCourseTitle = resultsPage.getFirstCourseTitle();
        Assertions.assertFalse(firstCourseTitle.isEmpty(), 
                "Каталог общего поиска должен успешно отображаться после сброса параметров");
        
        logger.info("=== ТЕСТ 5 УСПЕШНО ЗАВЕРШЕН ===\n");
    }
}