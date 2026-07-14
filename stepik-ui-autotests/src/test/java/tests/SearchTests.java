package tests;

import com.codeborne.selenide.Selenide;

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

public class SearchTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(SearchTests.class);

    @Test
    @DisplayName("1. Текстовый поиск и навигация по страницам")
    void testSearchAndPagination() {
        logger.info("=== ТЕСТ 1: Текстовый поиск и навигация по страницам ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor("Python");

        resultsPage.clickNext();

        resultsPage.openFirstCourse();

        logger.info("Проверка заголовка открывшегося курса на содержание слова «Python»");
        Selenide.switchTo().window(1);
        String actualTitle = new CoursePage().getCourseTitle();

        Assertions.assertTrue(actualTitle.toLowerCase().contains("python"),
                "Заголовок курса '" + actualTitle + "' должен содержать ключевое слово 'Python'");

        logger.info("=== ТЕСТ 1 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("5. Сброс текстового поиска «крестиком»")
    void testResetSearchWithClearButton() {
        logger.info("=== ТЕСТ 5: Сброс текстового поиска «крестиком» ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor("C++");

        resultsPage.clickNext();

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

    @Test
    @DisplayName("8. Поиск по автору с переходом в профиль")
    void testSearchByAuthorAndProfile() {
        logger.info("=== ТЕСТ 8: Поиск по автору с переходом в профиль ===");

        HomePage homePage = new HomePage().openPage();

        SearchResultsPage resultsPage = homePage.searchFor("Оксана Еськова");

        resultsPage.getCourseCard(0).clickFirstAuthor();

        ProfilePage profilePage = new ProfilePage();
        profilePage.openReviews();

        logger.info("Проверка наличия заголовка рейтинга и отзывов в профиле");
        profilePage.reviewsHeader.shouldBe(com.codeborne.selenide.Condition.visible, Duration.ofSeconds(5));

        logger.info("=== ТЕСТ 8 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

}