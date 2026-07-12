package components;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
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

        title = new CourseCardText(baseElement.$x(".//a[contains(@class,'course-card__title')]"));

        favoriteButton = new Button(baseElement.$x(".//button[contains(@class, 'course-card__bookmark')]"));
    }

    /**
    * Метод добавления курса в избранное
    * */
    public void addToWishlist() {
        logger.info("Добавление курса в избранное (JS-клик)");
        favoriteButton.getElement().click(ClickOptions.usingJavaScript());
        
        com.codeborne.selenide.Selenide.sleep(1000);
    }

    /**
    * Метод клика по первому автору
    * */
    public void clickFirstAuthor() {
        logger.info("Клик по первому автору (JS-клик)");
        try {
            baseElement.$x(".//a[contains(@class,'course-card__author')]")
                    .shouldBe(visible, Duration.ofSeconds(10))
                    .click(com.codeborne.selenide.ClickOptions.usingJavaScript());
            
            logger.info("Клик по первому автору выполнен через JS");
        }
        catch(Exception e) {
            logger.warn("Ошибка при клике по автору: {}", e.getMessage());
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

    public boolean hasOldPrice() {
        try {
            baseElement.$x(".//*[contains(@class, 'price-old') or contains(@class, 'discount') or self::del or self::s or contains(@class, 'old-price')]").should(Condition.exist, Duration.ofSeconds(5));
            
            logger.info("Старая цена обнаружена на карточке");
            return true;
        } catch (Throwable e) {
            String cardHtml = baseElement.innerHtml();
            logger.error("Старая цена НЕ найдена. Структура карточки: {}", cardHtml);
            return false;
        }
    }

    /**
     * Проверяет, находится ли курс в списке избранного (по наличию активного класса у кнопки)
     * */
    public boolean isFavorite() {
        try {
            favoriteButton.getElement().$x(".//*[contains(@class, 'favorite-filled')]").should(Condition.exist, Duration.ofSeconds(7));
            
            logger.info("Успех: внутри кнопки найдена иконка заполненного сердечка (favorite-filled)");
            return true;
        } catch (Throwable e) {
            logger.error("Иконка 'favorite-filled' не найдена. Текущий HTML: {}", favoriteButton.getElement().innerHtml());
            return false;
        }
    }
}