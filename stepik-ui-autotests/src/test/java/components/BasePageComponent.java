package components;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
* Базовый класс для элементом страницы - обертка SelenideElement для работы с этим элементом
* */

public abstract class BasePageComponent {
    private static final Logger logger = LogManager.getLogger(BasePageComponent.class);

    protected static final int WAIT_SECONDS = 15;

    protected final SelenideElement baseElement;

    protected BasePageComponent(String xpath, Object... attributes) {
        baseElement = $x(String.format(xpath, attributes));
    }

    protected BasePageComponent(SelenideElement element) { baseElement = element; }

    public SelenideElement getElement() { return baseElement; }

    /**
    * Проверка, отображается ли элемент на странице
    * */
    public boolean isDisplayed() {
        try {
            baseElement.shouldBe(visible, Duration.ofSeconds(WAIT_SECONDS));
            logger.info("Элемент отображается на странице");
            return true;
        }
        catch (ElementNotFound e) {
            logger.info("Элемент не отображается на странице");
            return false;
        }
    }
}