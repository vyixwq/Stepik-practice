package pages;

import components.Button;
import components.CourseCardItem;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverConditions;
import components.SearchComponent;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SearchResultsPage extends BasePage<SearchResultsPage> {
    private final ElementsCollection courseCards = $$x("//div[contains(@class, 'course-card')]");

    private final Button clearButton = Button.byClass("search-form__reset");

    private final Button freeFilter = Button.byName("Бесплатно");

    public SearchResultsPage() {
        super($x("//h1[text()='Поиск']"), SearchResultsPage.class);
    }

    // Второе поле поиска
    private final SearchComponent centralSearch = components.SearchComponent.byClass("catalog-w__search-form");

    // Для Тестов 1, 2, 4, 5
    public String getFirstCourseTitle() {
        webdriver().shouldHave(WebDriverConditions.urlContaining("search"), Duration.ofSeconds(15));
        courseCards.shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(15));
        return new CourseCardItem(courseCards.first()).getTitle().getText();
    }

    // Для Теста 3
    public String getFirstCourseAuthor() {
        webdriver().shouldHave(WebDriverConditions.urlContaining("search"), Duration.ofSeconds(15));
        
        courseCards.shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(15));
        
        courseCards.first().$x(".//a[contains(@class, 'course-card__author')]")
                .shouldBe(visible, Duration.ofSeconds(10));
        
        return new CourseCardItem(courseCards.first()).getAuthors().get(0).getText();
    }

    // Для Теста 7
    public SearchResultsPage clickClear() {
        clearButton.click();
        return this;
    }

    // Для Теста 8
    public CoursePage openFirstCourse() {
        new CourseCardItem(courseCards.first()).getTitle().click();
        return page(CoursePage.class);
    }

    // Для Теста 9
    public SearchResultsPage applyFreeFilter() {
        freeFilter.click();
        courseCards.first().shouldBe(visible);
        return this;
    }

    // Для Теста 10
    public SearchResultsPage searchAgain(String text) {
        centralSearch.search(text);
        return this;
    }
}