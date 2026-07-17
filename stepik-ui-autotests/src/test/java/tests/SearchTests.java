package tests;

import helpers.WindowHandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.ProfilePage;
import pages.SearchResultsPage;
import pages.CoursePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

import static helpers.TestsConstants.*;

public class SearchTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(SearchTests.class);

    @Test
    @DisplayName("1. Текстовый поиск и навигация по страницам")
    void testSearchAndPagination() {
        logger.info("=== ТЕСТ 1: Текстовый поиск и навигация по страницам ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor(SEARCH_QUERY_PYTHON);

        resultsPage.clickNext();

        resultsPage.openFirstCourse();

        logger.info("Проверка заголовка открывшегося курса на содержание слова «Python»");
        WindowHandler.switchToWindow(TARGET_WINDOW_INDEX); 
        String actualTitle = new CoursePage().getCourseTitle();

        Assertions.assertTrue(
                actualTitle.toLowerCase().contains(EXPECTED_TXT_PYTHON),
                String.format(ASSERT_MSG_PYTHON_TITLE, actualTitle));

        logger.info("=== ТЕСТ 1 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("5. Сброс текстового поиска «крестиком»")
    void testResetSearchWithClearButton() {
        logger.info("=== ТЕСТ 5: Сброс текстового поиска «крестиком» ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor(SEARCH_QUERY_CPP);

        resultsPage.clickNext();

        resultsPage.clickClear();

        logger.info("Проверка, что поле поиска очищено, а выдача сбросилась");
        String searchInputValue = resultsPage.getSearchInputValue();

        Assertions.assertTrue(searchInputValue == null || searchInputValue.isEmpty(), ASSERT_MSG_SEARCH_EMPTY);

        String firstCourseTitle = resultsPage.getFirstCourseTitle();
        Assertions.assertFalse(firstCourseTitle.isEmpty(), ASSERT_MSG_CATALOG_NOT_EMPTY);

        logger.info("=== ТЕСТ 5 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("8. Поиск по автору с переходом в профиль")
    void testSearchByAuthorAndProfile() {
        logger.info("=== ТЕСТ 8: Поиск по автору с переходом в профиль ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor(SEARCH_QUERY_AUTHOR);

        resultsPage.getCourseCard(FIRST_ITEM).clickFirstAuthor();

        ProfilePage profilePage = new ProfilePage();
        profilePage.openReviews();

        logger.info("Проверка наличия заголовка рейтинга и отзывов в профиле");
        profilePage.reviewsHeader.shouldBe(com.codeborne.selenide.Condition.visible, Duration.ofSeconds(DEFAULT_WAIT_TIME_SEC));

        logger.info("=== ТЕСТ 8 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

}