package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTests extends BaseTest {

    @Test
    @DisplayName("1. Поиск по полному названию курса")
    void searchByFullCourseNameTest() {
        
        String searchQuery = "Основы теории графов";
        String expectedTitle = "Основы теории графов";

        
        SearchResultsPage searchResultsPage = new HomePage()
                .openPage()
                .closeCookieBanner()
                .searchFor(searchQuery);

        
        String actualTitle = searchResultsPage.getFirstCourseTitle();
        assertEquals(expectedTitle, actualTitle, 
                "Название первого курса не совпадает с ожидаемым: " + expectedTitle);
    }

    @Test
    @DisplayName("2. Поиск по ключевому слову (частичное совпадение)")
    void searchByKeywordTest() {
        String searchQuery = "Pyth";
        String expectedKeyword = "Python";

        SearchResultsPage searchResultsPage = new HomePage()
                .openPage()
                .closeCookieBanner()
                .searchFor(searchQuery);

        String actualTitle = searchResultsPage.getFirstCourseTitle();
        assertTrue(actualTitle.contains(expectedKeyword), 
                "В названии курса отсутствует ожидаемое слово: " + expectedKeyword);
    }

    @Test
    @DisplayName("3. Поиск по автору")
    void searchByAuthorTest() {
        String searchQuery = "Оксана Еськова";
        String expectedAuthor = "Оксана Еськова";

        SearchResultsPage searchResultsPage = new HomePage()
                .openPage()
                .closeCookieBanner()
                .searchFor(searchQuery);

        String actualAuthor = searchResultsPage.getFirstCourseAuthor();

        assertEquals(expectedAuthor, actualAuthor, 
                "Имя автора курса не совпадает с ожидаемым: " + expectedAuthor);
    }

    @Test
    @DisplayName("4. Поиск в разном регистре")
    void searchIgnoreCaseTest() {
        String searchQuery = "PYTHON";
        String expectedKeyword = "python"; 

        SearchResultsPage searchResultsPage = new HomePage()
                .openPage()
                .closeCookieBanner()
                .searchFor(searchQuery);

        String actualTitle = searchResultsPage.getFirstCourseTitle();
        assertTrue(actualTitle.toLowerCase().contains(expectedKeyword), 
                "Система не нашла курс, содержащий: " + expectedKeyword);
    }

    @Test
    @DisplayName("5. Поиск с лишними пробелами")
    void searchWithExtraSpacesTest() {
        String searchQuery = " Java ";
        String expectedKeyword = "Java";

        SearchResultsPage searchResultsPage = new HomePage()
                .openPage()
                .closeCookieBanner()
                .searchFor(searchQuery);

        String actualTitle = searchResultsPage.getFirstCourseTitle();
        assertTrue(actualTitle.contains(expectedKeyword), 
                "Результаты поиска не соответствуют очищенному от пробелов запросу: " + expectedKeyword);
    }
}