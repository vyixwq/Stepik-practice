package components;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/**
 * Элемент списка результатов - реализует функционал работы с
 * названием и авторами курса, а также добавления курса в 'Избранное'
 * */
public class CourseCardItem extends BaseComponent {
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
    }

    /**
    * Метод клика по первому автору
    * */
    public void clickFirstAuthor() {
        logger.info("Клик по первому автору (JS-клик)");
        try {
            SelenideElement firstAuthor = baseElement.$x(".//a[contains(@class,'course-card__author')]");
            firstAuthor
                    .shouldBe(visible, Duration.ofSeconds(WAIT_SECONDS))
                    .click(com.codeborne.selenide.ClickOptions.usingJavaScript());
            
            logger.info("Клик по первому автору выполнен через JS");
        }
        catch(Exception e) {
            logger.error("Ошибка при клике по автору: {}", e.getMessage());
        }
    }

    /**
    * Метод получения заголовка курса
    * */
    public CourseCardText getTitle() {
        return title;
    }

    /**
     * Проверяет, есть ли акционные курсы (карточки со старой ценой)
     * */
    public boolean hasOldPrice() {
        try {
            SelenideElement oldPrice = baseElement.$x(".//span[contains(@class, 'only-price-regular')]");

            oldPrice.should(Condition.exist, Duration.ofSeconds(WAIT_SECONDS));
            
            logger.info("Старая цена обнаружена на карточке");
            return true;
        } catch (Throwable e) {
            logger.error("Старая цена НЕ найдена");
            return false;
        }
    }

    /**
     * Проверяет, находится ли курс в списке избранного (по наличию активного класса у кнопки)
     * */
    public boolean isFavorite() {
        try {
            SelenideElement favoriteFilledIcon = favoriteButton
                    .getElement()
                    .$x(".//*[contains(@class, 'favorite-filled')]");

            favoriteFilledIcon.should(Condition.exist, Duration.ofSeconds(WAIT_SECONDS));
            
            logger.info("Успех: внутри кнопки найдена иконка заполненного сердечка (favorite-filled)");
            return true;
        } catch (Throwable e) {
            logger.error(
                    "Иконка 'favorite-filled' не найдена. Текущий HTML: {}",
                    favoriteButton.getElement().innerHtml()
            );
            return false;
        }
    }
}