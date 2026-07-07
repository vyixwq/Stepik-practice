package components;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import java.lang.reflect.UndeclaredThrowableException;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/*
* Базовый класс для элементом страницы - обертка для SelenideElement для работы с этим элементом
* */

public abstract class BasePageComponent {
    protected static final int WAIT_SECONDS = 15;

    protected final SelenideElement baseElement;

    protected BasePageComponent(String xpath, Object... attributes) {
        baseElement = $x(String.format(xpath, attributes));
    }

    protected BasePageComponent(SelenideElement element) { baseElement = element; }

    public SelenideElement getElement() { return baseElement; }

    public boolean isDisplayed() {
        try {
            baseElement.shouldBe(visible, Duration.ofSeconds(WAIT_SECONDS));
            return true;
        }
        catch (ElementNotFound e) {
            return false;
        }
    }
}