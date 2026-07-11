package components;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.ex.ElementNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;

import java.time.Duration;
import java.util.List;

/**
 * Элемент списка результатов - реализует функционал работы с
 * названием и авторами курса, а также добавления курса в 'Избранное'
 * */
public class CourseCardItem extends BasePageComponent {
    private static final Logger logger = LogManager.getLogger(CourseCardItem.class);

    private final CourseCardText title;
    private final Button favoriteButton;

    public CourseCardItem(SelenideElement element) {
        super(element);

        title = new CourseCardText(
                baseElement.$x(".//a[contains(@class,'course-card__title')]")
        );

        favoriteButton = new Button(baseElement.$x(".//button[contains(@class, 'course-card__bookmark')]"));
    }

    /**
    * Метод добавления курса в избранное
    * */
    public void addToWishlist() {
        logger.info("Добавление курса в избранное");
        favoriteButton.click();
        logger.info("Курс добавлен в избранное");
    }

    /**
    * Метод клика по первому автору
    * */
    public void clickFirstAuthor() {
        logger.info("Клик по первому автору");
        try {
            baseElement.$x(".//a[contains(@class,'course-card__author')]")
                    .shouldBe(visible, Duration.ofSeconds(30))
                    .click();
            logger.info("Клик по первому автору выполнен");
        }
        catch(ElementNotFound e) {
            logger.warn("Ошибка: Автор не успел прогрузиться");
        }
    }

    /**
    * Метод получения заголовка курса
    * */
    public CourseCardText getTitle() {
        logger.debug("Получение заголовка курса");
        return title;
    }

    /**
    * Метод получения списка авторов
    * */
    public List<CourseCardText> getAuthors() {
        List<CourseCardText> authors = baseElement
                .$$x(".//a[contains(@class,'course-card__author')]")
                .stream()
                .map(CourseCardText::new)
                .toList();
        logger.debug("Получение списка авторов, найдено: {}", authors.size());
        return authors;
    }
}