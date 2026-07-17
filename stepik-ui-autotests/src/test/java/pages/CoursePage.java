package pages;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import helpers.PagesConstants;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница отдельного курса на Stepik.
 * Содержит методы для работы с информацией о курсе.
 */

public class CoursePage extends BasePage<CoursePage> {

    private static final Logger logger = LogManager.getLogger(CoursePage.class);
    private static final SelenideElement courseTitle = $x(PagesConstants.COURSE_PAGE_TITLE_XPATH);

    private final String title;

    public CoursePage() {
        super(courseTitle, CoursePage.class);
        title = courseTitle.shouldBe(visible).getText();
    }

    public String getCourseTitle() {
        logger.info(PagesConstants.GET_TITLE_LOG_MESSAGE, title);
        return title;
    }
}