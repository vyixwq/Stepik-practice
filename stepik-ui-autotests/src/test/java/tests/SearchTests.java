package tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;

import components.CourseCardItem;

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

    @Test
    @DisplayName("6. Смена запроса с сохранением фильтра")
    void testChangeQueryKeepFilter() {
        logger.info("=== ТЕСТ 6: Смена запроса с сохранением фильтра ===");

        //ДЕЙСТВИЕ 1: Открыта главная страница Stepik
        HomePage homePage = new HomePage().openPage();

        //ДЕЙСТВИЕ 2: Выполнен поиск по значению «Machine Learning»
        SearchResultsPage resultsPage = homePage.searchFor("Machine Learning");

        //ДЕЙСТВИЕ 3: Отмечен чекбокс «Английский» в блоке «Язык»
        resultsPage.applyCheckBoxFilter("language", "en");

        //ДЕЙСТВИЕ 4: Нажата иконка «крестик» внутри поля поиска
        resultsPage.clickClear();

        //ДЕЙСТВИЕ 5: Выполнен повторный поиск по значению «Data Science»
        resultsPage.searchAgain("Data Science");

        //ДЕЙСТВИЕ 6: Проверка, что выдача соответствует новому запросу и фильтр активен
        logger.info("Проверка сохранения фильтра и смены текста");
        String firstTitle = resultsPage.getFirstCourseTitle();
        Assertions.assertTrue(firstTitle.toLowerCase().contains("data") || firstTitle.toLowerCase().contains("science"), 
                "Заголовок должен содержать 'Data Science'");
        
        Assertions.assertTrue(resultsPage.isFilterSelected("language", "en"), 
                "Фильтр 'Английский' должен остаться активным");

        logger.info("=== ТЕСТ 6 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("7. Несуществующий запрос с фильтрами")
    void testNonExistentSearchWithFilters() {
        logger.info("=== ТЕСТ 7: Несуществующий запрос с фильтрами ===");

        // ДЕЙСТВИЕ 1-2: Поиск
        SearchResultsPage resultsPage = new HomePage().openPage().searchFor("zxcvbnm123");

        // ДЕЙСТВИЕ 3-4: Фильтры
        resultsPage.applyCheckBoxFilter("language", "ru");
        resultsPage.applyCheckBoxFilter("difficulty", "easy");

        // ДЕЙСТВИЕ 5: Проверка (теперь ищем слово 'найдено')
        logger.info("Проверка отображения заглушки...");
        Assertions.assertTrue(resultsPage.isNothingFoundMessageVisible(),"На странице должно быть сообщение 'ничего не найдено'");

        logger.info("=== ТЕСТ 7 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("8. Поиск по автору с переходом в профиль")
    void testSearchByAuthorAndProfile() {
        logger.info("=== ТЕСТ 8: Поиск по автору с переходом в профиль ===");

        //ДЕЙСТВИЕ 1: Открыта главная страница Stepik
        HomePage homePage = new HomePage().openPage();

        //ДЕЙСТВИЕ 2: Выполнен поиск по значению «Оксана Еськова»
        SearchResultsPage resultsPage = homePage.searchFor("Оксана Еськова");

        //ДЕЙСТВИЕ 3: Нажата ссылка с именем автора в карточке первого курса
        resultsPage.getCourseCard(0).clickFirstAuthor();

        //ДЕЙСТВИЕ 4: В боковом меню открывшейся страницы нажать на пункт «Отзывы»
        ProfilePage profilePage = new ProfilePage();
        profilePage.openReviews();

        //ДЕЙСТВИЕ 5: Проверить, что на странице отображается заголовок «Рейтинг и отзывы»
        logger.info("Проверка наличия заголовка рейтинга и отзывов в профиле");
        profilePage.reviewsHeader.shouldBe(com.codeborne.selenide.Condition.visible, Duration.ofSeconds(5));

        logger.info("=== ТЕСТ 8 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("9. Фильтрация акционных курсов")
    void testFilteringPromoCourses() {
        logger.info("=== ТЕСТ 9: Фильтрация акционных курсов ===");

        //ДЕЙСТВИЕ 1-2: Поиск «Backend»
        SearchResultsPage resultsPage = new HomePage().openPage().searchFor("Backend");

        //ДЕЙСТВИЕ 3: Переключить тумблер «Только со скидкой»
        resultsPage.applyTogglerFilter("discount");

        //ДЕЙСТВИЕ 4: Отметить чекбокс «Программа» (специализация)
        resultsPage.applyCheckBoxFilter("type", "spec");
        logger.info("Ожидание обновления результатов фильтрации...");
        com.codeborne.selenide.Selenide.sleep(2000);

        //ДЕЙСТВИЕ 5: Проверить, что отображается перечеркнутая цена
        logger.info("Проверка наличия старой цены на карточках результатов");
        
        boolean foundPromo = false;
        for (int i = 0; i < 3; i++) {
            CourseCardItem card = resultsPage.getCourseCard(i);
            if (card != null && card.hasOldPrice()) {
                foundPromo = true;
                logger.info("Акционный курс найден на позиции {}", i + 1);
                break;
            }
        }

        Assertions.assertTrue(foundPromo, "В результатах фильтрации должен быть хотя бы один курс с отображаемой старой ценой");

        logger.info("=== ТЕСТ 9 УСПЕШНО ЗАВЕРШЕН ===\n");
    }

    @Test
    @DisplayName("10. Добавление курса в избранное из поиска")
    void testAddToFavoritesFromSearch() {
        logger.info("=== ТЕСТ 10: Добавление курса в избранное из поиска ===");

        //ДЕЙСТВИЕ 1-2: Поиск «Web Design»
        SearchResultsPage resultsPage = new HomePage().openPage().searchFor("Web Design");

        //ДЕЙСТВИЕ 3: Отметить «Для продолжающих»
        resultsPage.applyCheckBoxFilter("difficulty", "normal");

        //ДЕЙСТВИЕ 4: Нажать на иконку «Сердечко» на первой карточке
        resultsPage.getCourseCard(0).addToWishlist();

        //ДЕЙСТВИЕ 5: Проверить, что иконка изменила цвет (стала активной)
        logger.info("Проверка, что курс добавлен в избранное");
        Assertions.assertTrue(resultsPage.firstCourseIsFavorite(), "Иконка сердечка должна изменить состояние на активное");

        logger.info("=== ТЕСТ 10 УСПЕШНО ЗАВЕРШЕН ===\n");
    }
}