package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс страницы конкретного курса
 */
public class CoursePage extends BasePage<CoursePage> {

    public CoursePage() {
        super($x("//h1"), CoursePage.class);
    }

    /**
     * Возвращает текст заголовка.
     */
    public String getCourseTitle() {
        return basePage.shouldBe(visible).getText();
    }
}