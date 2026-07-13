package pages;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница отдельного курса на Stepik.
 * Содержит методы для работы с информацией о курсе.
 */

public class CoursePage extends BasePage<CoursePage> {

    private static final Logger logger = LogManager.getLogger(CoursePage.class);

    private final SelenideElement courseTitle = $x("//h1");

    public CoursePage() {
        super($x("//h1"), CoursePage.class);
    }

    public String getCourseTitle() {
        String title = courseTitle.shouldBe(visible).getText();
        logger.info("Заголовок курса: '{}'", title);
        return title;
    }
}