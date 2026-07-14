package tests;
import components.CourseCardItem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilterTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(FilterTests.class);

    @Test
    @DisplayName("2. Поиск с фильтрами")
    void testSearchWithFilters() {
        logger.info("=== ТЕСТ 2: Поиск с фильтрами ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor("Linux");

        resultsPage.applyCheckBoxFilter("language", "en");

        resultsPage.applyCheckBoxFilter("type", "spec");

        logger.info("Проверка отображения результатов фильтрации по Linux");
        String firstTitle = resultsPage.getFirstCourseTitle();

        Assertions.assertTrue(
                firstTitle.toLowerCase().contains("linux"),
                String.format("Первый найденный курс '%s' должен содержать ключевое слово 'Linux'", firstTitle));

        logger.info("=== ТЕСТ 2 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("3. Фильтрация по ценовому диапазону")
    void testPriceFilter() {
        logger.info("=== ТЕСТ 3: Фильтрация по ценовому диапазону ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor("Java");

        resultsPage.applyMinPriceFilter("5000");

        resultsPage.applyMaxPriceFilter("7000");

        logger.info("Проверка отображения результатов в ценовом диапазоне");
        String firstTitle = resultsPage.getFirstCourseTitle();

        Assertions.assertTrue(
                firstTitle.toLowerCase().contains("java"),
                String.format(
                        "Первый найденный курс в ценовом диапазоне '%s' должен соответствовать запросу 'Java'",
                        firstTitle));

        logger.info("=== ТЕСТ 3 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("4. Поиск для продвинутых с сертификатом")
    void testAdvancedSearchWithCertificate() {
        logger.info("=== ТЕСТ 4: Поиск для продвинутых с сертификатом ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor("QA Automation");

        resultsPage.applyCheckBoxFilter("difficulty", "hard");

        resultsPage.applyTogglerFilter("certificate");

        logger.info("Проверка отображения профессиональных курсов с сертификатами");
        String firstTitle = resultsPage.getFirstCourseTitle();

        Assertions.assertTrue(
                firstTitle.toLowerCase().contains("qa") || firstTitle.toLowerCase().contains("automation"),
                String.format(
                        "Найденный курс '%s' должен соответствовать тематике 'QA Automation'",
                        firstTitle));

        logger.info("=== ТЕСТ 4 УСПЕШНО ЗАВЕРШЕН ===\n");
    }
    
    @Test
    @DisplayName("6. Смена запроса с сохранением фильтра")
    void testChangeQueryKeepFilter() {
        logger.info("=== ТЕСТ 6: Смена запроса с сохранением фильтра ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor("Machine Learning");

        resultsPage.applyCheckBoxFilter("language", "en");

        resultsPage.clickClear();

        resultsPage.searchAgain("Data Science");

        logger.info("Проверка сохранения фильтра и смены текста");
        String firstTitle = resultsPage.getFirstCourseTitle();
        Assertions.assertTrue(
                firstTitle.toLowerCase().contains("data") || firstTitle.toLowerCase().contains("science"),
                "Заголовок должен содержать 'Data Science'");

        Assertions.assertTrue(
                resultsPage.isFilterSelected("language", "en"),
                "Фильтр 'Английский' должен остаться активным");

        logger.info("=== ТЕСТ 6 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("7. Несуществующий запрос с фильтрами")
    void testNonExistentSearchWithFilters() {
        logger.info("=== ТЕСТ 7: Несуществующий запрос с фильтрами ===");

        SearchResultsPage resultsPage = new HomePage().openPage().searchFor("zxcvbnm123");

        resultsPage.applyCheckBoxFilter("language", "ru");
        resultsPage.applyCheckBoxFilter("difficulty", "easy");

        logger.info("Проверка отображения заглушки...");
        Assertions.assertTrue(
                resultsPage.isNothingFoundMessageVisible(),
                "На странице должно быть сообщение 'ничего не найдено'");

        logger.info("=== ТЕСТ 7 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

        @Test
    @DisplayName("9. Фильтрация акционных курсов")
    void testFilteringPromoCourses() {
        logger.info("=== ТЕСТ 9: Фильтрация акционных курсов ===");

        SearchResultsPage resultsPage = new HomePage().openPage().searchFor("Backend");

        resultsPage.applyCheckBoxFilter("type", "spec");

        resultsPage.applyTogglerFilter("discount");

        logger.info("Ожидание обновления результатов фильтрации...");
        com.codeborne.selenide.Selenide.sleep(2000);

        logger.info("Проверка наличия старой цены на карточках результатов");

        boolean foundPromo = false;
        for (int i = 0; i < 20; i++) {
            CourseCardItem card = resultsPage.getCourseCard(i);
            if (card != null && card.hasOldPrice()) {
                foundPromo = true;
                logger.info("Акционный курс найден на позиции {}", i + 1);
                break;
            }
        }

        Assertions.assertTrue(
                foundPromo,
                "В результатах фильтрации должен быть хотя бы один курс с отображаемой старой ценой");

        logger.info("=== ТЕСТ 9 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("10. Добавление курса в избранное из поиска")
    void testAddToFavoritesFromSearch() {
        logger.info("=== ТЕСТ 10: Добавление курса в избранное из поиска ===");

        SearchResultsPage resultsPage = new HomePage().openPage().searchFor("Web Design");

        resultsPage.applyCheckBoxFilter("difficulty", "normal");

        resultsPage.getCourseCard(0).addToWishlist();

        logger.info("Проверка, что курс добавлен в избранное");
        Assertions.assertTrue(
                resultsPage.firstCourseIsFavorite(),
                "Иконка сердечка должна изменить состояние на активное");

        logger.info("=== ТЕСТ 10 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

}
