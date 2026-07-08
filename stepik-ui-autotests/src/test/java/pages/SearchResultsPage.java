package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.WebDriverConditions;
import components.Button;
import components.CourseCardItem;
import com.codeborne.selenide.ElementsCollection;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;

public class SearchResultsPage extends BasePage<SearchResultsPage> {

    public SearchResultsPage() {
        super($x("//h1[text()='Поиск']"), SearchResultsPage.class);
    }

    private final ElementsCollection courseCards = $$x("//div[contains(@class, 'course-card')]");

    private final Button clearButton = new Button("//button[contains(@class, 'search-form__reset')]", "Крестик очистки");

    private final Button freeFilter = new Button("//span[contains(text(),'Бесплатно')]", "Бесплатно");


    // Для Тестов 1, 2, 4, 5
    public String getFirstCourseTitle() {
        webdriver().shouldHave(WebDriverConditions.urlContaining("search"));

        // Ждем, пока первая карточка станет видимой
        courseCards.first().shouldBe(visible);

        return new CourseCardItem(courseCards.first()).getTitle().getText();
    }

    // Для Теста 3 (Автор)
    public String getFirstCourseAuthor() {
        return new CourseCardItem(courseCards.first()).getAuthors().get(0).getText();
    }

    // Для Теста 7 (Крестик)
    public SearchResultsPage clickClear() {
        clearButton.click();
        return this;
    }

    // Для Теста 9 (Фильтр)
    public SearchResultsPage applyFreeFilter() {
        freeFilter.click();
        return this;
    }

    // Для Теста 8 (Переход на страницу курса)
    public CoursePage openFirstCourse() {
        new CourseCardItem(courseCards.first()).getTitle().click();
        return page(CoursePage.class);
    }
}