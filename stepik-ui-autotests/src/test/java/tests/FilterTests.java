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

    private static final String MIN_PRICE_VAL = "5000";
    private static final String MAX_PRICE_VAL = "7000";
    private static final int PROMO_SEARCH_LIMIT = 20;
    private static final int FIRST_ITEM = 0;

    private static final String SEARCH_QUERY_LINUX = "Linux";
    private static final String SEARCH_QUERY_JAVA = "Java";
    private static final String SEARCH_QUERY_QA_AUTO = "QA Automation";
    private static final String SEARCH_QUERY_ML = "Machine Learning";
    private static final String SEARCH_QUERY_DS = "Data Science";
    private static final String SEARCH_QUERY_INVALID = "zxcvbnm123";
    private static final String SEARCH_QUERY_BACKEND = "Backend";
    private static final String SEARCH_QUERY_WEB_DESIGN = "Web Design";

    private static final String FILTER_LANGUAGE = "language";
    private static final String FILTER_TYPE = "type";
    private static final String FILTER_DIFFICULTY = "difficulty";
    private static final String TOGGLER_CERTIFICATE = "certificate";
    private static final String TOGGLER_DISCOUNT = "discount";

    private static final String VAL_LANG_EN = "en";
    private static final String VAL_LANG_RU = "ru";
    private static final String VAL_TYPE_SPEC = "spec";
    private static final String VAL_DIFF_EASY = "easy";
    private static final String VAL_DIFF_NORMAL = "normal";
    private static final String VAL_DIFF_HARD = "hard";

    private static final String EXPECTED_TXT_LINUX = "linux";
    private static final String EXPECTED_TXT_JAVA = "java";
    private static final String EXPECTED_TXT_QA = "qa";
    private static final String EXPECTED_TXT_AUTO = "automation";
    private static final String EXPECTED_TXT_DATA = "data";
    private static final String EXPECTED_TXT_SCIENCE = "science";

    private static final String ASSERT_MSG_LINUX = "Первый найденный курс '%s' должен содержать ключевое слово 'Linux'";
    private static final String ASSERT_MSG_JAVA = "Первый найденный курс в ценовом диапазоне '%s' должен соответствовать запросу 'Java'";
    private static final String ASSERT_MSG_QA_AUTO = "Найденный курс '%s' должен соответствовать тематике 'QA Automation'";
    private static final String ASSERT_MSG_DS_TITLE = "Заголовок должен содержать 'Data Science'";
    private static final String ASSERT_MSG_LANG_ACTIVE = "Фильтр 'Английский' должен остаться активным";
    private static final String ASSERT_MSG_NOTHING_FOUND = "На странице должно быть сообщение 'ничего не найдено'";
    private static final String ASSERT_MSG_PROMO = "В результатах фильтрации должен быть хотя бы один курс с отображаемой старой ценой";
    private static final String ASSERT_MSG_FAVORITE = "Иконка сердечка должна изменить состояние на активное";

    @Test
    @DisplayName("2. Поиск с фильтрами")
    void testSearchWithFilters() {
        logger.info("=== ТЕСТ 2: Поиск с фильтрами ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor(SEARCH_QUERY_LINUX);

        resultsPage.applyCheckBoxFilter(FILTER_LANGUAGE, VAL_LANG_EN);

        resultsPage.applyCheckBoxFilter(FILTER_TYPE, VAL_TYPE_SPEC);

        logger.info("Проверка отображения результатов фильтрации по Linux");
        String firstTitle = resultsPage.getFirstCourseTitle();

        Assertions.assertTrue(
                firstTitle.toLowerCase().contains(EXPECTED_TXT_LINUX),
                String.format(ASSERT_MSG_LINUX, firstTitle));

        logger.info("=== ТЕСТ 2 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("3. Фильтрация по ценовому диапазону")
    void testPriceFilter() {
        logger.info("=== ТЕСТ 3: Фильтрация по ценовому диапазону ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor(SEARCH_QUERY_JAVA);

        resultsPage.applyMinPriceFilter(MIN_PRICE_VAL);

        resultsPage.applyMaxPriceFilter(MAX_PRICE_VAL);

        logger.info("Проверка отображения результатов в ценовом диапазоне");
        String firstTitle = resultsPage.getFirstCourseTitle();

        Assertions.assertTrue(
                firstTitle.toLowerCase().contains(EXPECTED_TXT_JAVA),
                String.format(ASSERT_MSG_JAVA, firstTitle));

        logger.info("=== ТЕСТ 3 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("4. Поиск для продвинутых с сертификатом")
    void testAdvancedSearchWithCertificate() {
        logger.info("=== ТЕСТ 4: Поиск для продвинутых с сертификатом ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor(SEARCH_QUERY_QA_AUTO);

        resultsPage.applyCheckBoxFilter(FILTER_DIFFICULTY, VAL_DIFF_HARD);

        resultsPage.applyTogglerFilter(TOGGLER_CERTIFICATE);

        logger.info("Проверка отображения профессиональных курсов с сертификатами");
        String firstTitle = resultsPage.getFirstCourseTitle();

        Assertions.assertTrue(
                firstTitle.toLowerCase().contains(EXPECTED_TXT_QA) || firstTitle.toLowerCase().contains(EXPECTED_TXT_AUTO),
                String.format(ASSERT_MSG_QA_AUTO, firstTitle));

        logger.info("=== ТЕСТ 4 УСПЕШНО ЗАВЕРШЕН ===\n");
    }
    
    @Test
    @DisplayName("6. Смена запроса с сохранением фильтра")
    void testChangeQueryKeepFilter() {
        logger.info("=== ТЕСТ 6: Смена запроса с сохранением фильтра ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor(SEARCH_QUERY_ML);

        resultsPage.applyCheckBoxFilter(FILTER_LANGUAGE, VAL_LANG_EN);

        resultsPage.clickClear();

        resultsPage.searchAgain(SEARCH_QUERY_DS);

        logger.info("Проверка сохранения фильтра и смены текста");
        String firstTitle = resultsPage.getFirstCourseTitle();
        Assertions.assertTrue(
                firstTitle.toLowerCase().contains(EXPECTED_TXT_DATA) || firstTitle.toLowerCase().contains(EXPECTED_TXT_SCIENCE),
                ASSERT_MSG_DS_TITLE);

        Assertions.assertTrue(
                resultsPage.isFilterSelected(FILTER_LANGUAGE, VAL_LANG_EN), ASSERT_MSG_LANG_ACTIVE);

        logger.info("=== ТЕСТ 6 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("7. Несуществующий запрос с фильтрами")
    void testNonExistentSearchWithFilters() {
        logger.info("=== ТЕСТ 7: Несуществующий запрос с фильтрами ===");

        SearchResultsPage resultsPage = new HomePage().openPage().searchFor(SEARCH_QUERY_INVALID);

        resultsPage.applyCheckBoxFilter(FILTER_LANGUAGE, VAL_LANG_RU);
        resultsPage.applyCheckBoxFilter(FILTER_DIFFICULTY, VAL_DIFF_EASY);

        logger.info("Проверка отображения заглушки...");
        Assertions.assertTrue(resultsPage.isNothingFoundMessageVisible(),ASSERT_MSG_NOTHING_FOUND);

        logger.info("=== ТЕСТ 7 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("9. Фильтрация акционных курсов")
    void testFilteringPromoCourses() {
        logger.info("=== ТЕСТ 9: Фильтрация акционных курсов ===");

        SearchResultsPage resultsPage = new HomePage().openPage().searchFor(SEARCH_QUERY_BACKEND);

        resultsPage.applyCheckBoxFilter(FILTER_TYPE, VAL_TYPE_SPEC);

        resultsPage.applyTogglerFilter(TOGGLER_DISCOUNT);

        logger.info("Проверка наличия старой цены на карточках результатов");

        boolean foundPromo = false;
        for (int i = 0; i < PROMO_SEARCH_LIMIT; i++) {
            CourseCardItem card = resultsPage.getCourseCard(i);
            if (card != null && card.hasOldPrice()) {
                foundPromo = true;
                logger.info("Акционный курс найден на позиции {}", i + 1);
                break;
            }
        }

        Assertions.assertTrue(foundPromo,ASSERT_MSG_PROMO);

        logger.info("=== ТЕСТ 9 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("10. Добавление курса в избранное из поиска")
    void testAddToFavoritesFromSearch() {
        logger.info("=== ТЕСТ 10: Добавление курса в избранное из поиска ===");

        SearchResultsPage resultsPage = new HomePage().openPage().searchFor(SEARCH_QUERY_WEB_DESIGN);

        resultsPage.applyCheckBoxFilter(FILTER_DIFFICULTY, VAL_DIFF_NORMAL);

        resultsPage.getCourseCard(FIRST_ITEM).addToWishlist();

        logger.info("Проверка, что курс добавлен в избранное");
        Assertions.assertTrue(resultsPage.firstCourseIsFavorite(), ASSERT_MSG_FAVORITE);

        logger.info("=== ТЕСТ 10 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

}
