package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class CoursePage extends BasePage<CoursePage> {

    public CoursePage() {
        super($x("//h1"), CoursePage.class);
        logger.debug("Страница курса инициализирована");
    }

    public String getCourseTitle() {
        logger.debug("Получение заголовка курса");
        String title = basePage.shouldBe(visible).getText();
        logger.info("Заголовок курса: '{}'", title);
        return title;
    }
}