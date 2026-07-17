package components;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;

import helpers.ComponentsConstants;
/**
* Интерфейс для кликабельных объектов с соответствующим методом click
* */

public interface ClickableComponent {
    Logger logger = LogManager.getLogger(ClickableComponent.class);
    SelenideElement getElement();
    int WAIT_SECONDS = 15;
    /**
     * Метод для клика по объекту
     * */
    default void click() {
        logger.info(ComponentsConstants.CLICK_LOG_MSG);
        try {
            getElement().shouldBe(visible, Duration.ofSeconds(WAIT_SECONDS)).click();
        }
        catch (ElementNotFound e) {
            logger.error(ComponentsConstants.CLICK_ERR_MSG, e.getMessage());
        }
    }
}
