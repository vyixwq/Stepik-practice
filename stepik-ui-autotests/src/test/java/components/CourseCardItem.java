package components;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import helpers.ComponentsConstants;
/**
 * Элемент списка результатов - реализует функционал работы с
 * названием и авторами курса, а также добавления курса в 'Избранное'
 * */
public class CourseCardItem extends BaseComponent {
    private static final Logger logger = LogManager.getLogger(CourseCardItem.class);

    private final CourseCardText title;
    private final Button favoriteButton;
    private final SelenideElement firstAuthor;
    private final SelenideElement oldPrice;
    private final SelenideElement favoriteFilledIcon;

    public CourseCardItem(SelenideElement element) {
        super(element);
        this.title = new CourseCardText(baseElement.$x(ComponentsConstants.TITLE_XPATH));
        this.favoriteButton = new Button(baseElement.$x(ComponentsConstants.FAVORITE_BUTTON_XPATH));
        this.firstAuthor = baseElement.$x(ComponentsConstants.FIRST_AUTHOR_XPATH);
        this.oldPrice = baseElement.$x(ComponentsConstants.OLD_PRICE_XPATH);
        this.favoriteFilledIcon = favoriteButton.getElement().$x(ComponentsConstants.FAVORITE_FILLED_ICON_XPATH);
    }

    /**
    * Метод добавления курса в избранное
    * */
    public void addToWishlist() {
        logger.info(ComponentsConstants.ADD_TO_WISHLIST_LOG_MSG);
        favoriteButton.getElement().click(ClickOptions.usingJavaScript());
    }

    /**
    * Метод клика по первому автору
    * */
    public void clickFirstAuthor() {
        logger.info(ComponentsConstants.FIRST_AUTHOR_CLICK_LOG_MSG);
        try {
            firstAuthor
                    .shouldBe(visible, Duration.ofSeconds(WAIT_SECONDS))
                    .click(com.codeborne.selenide.ClickOptions.usingJavaScript());
        }
        catch(Exception e) {
            logger.error(ComponentsConstants.FIRST_AUTHOR_CLICK_ERR_MSG, e.getMessage());
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
            oldPrice.should(Condition.exist, Duration.ofSeconds(WAIT_SECONDS));
            logger.info(ComponentsConstants.HAS_OLD_PRICE_LOG_MSG);
            return true;
        } catch (Throwable e) {
            logger.error(ComponentsConstants.HAS_OLD_PRICE_ERR_MSG);
            return false;
        }
    }

    /**
     * Проверяет, находится ли курс в списке избранного (по наличию активного класса у кнопки)
     * */
    public boolean isFavorite() {
        try {
            favoriteFilledIcon.should(Condition.exist, Duration.ofSeconds(WAIT_SECONDS));
            
            logger.info(ComponentsConstants.COURSE_IN_WISHLIST_LOG_MSG);
            return true;
        } catch (Throwable e) {
            logger.error(ComponentsConstants.COURSE_IN_WISHLIST_ERR_MSG);
            return false;
        }
    }
}