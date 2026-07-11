package components;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;

/**
* Интерфейс для кликабельных объектов с соответствующим методом click
* */

public interface ClickableComponent {
    Logger logger = LogManager.getLogger(ClickableComponent.class);
    SelenideElement getElement();

    /**
     * Метод для клика по объекту
     * */
    default void click() {
        logger.info("Клик по элементу");
        try {
            getElement().shouldBe(visible, Duration.ofSeconds(30)).click();
            logger.info("Клик выполнен");
        }
        catch (ElementNotFound e) {
            logger.warn("Ошибка: нельзя кликнуть по элементу, которого нет на странице либо он не успел прогрузиться");
        }
    }

}
