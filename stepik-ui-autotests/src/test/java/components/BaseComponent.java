package components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

/**
* Базовый класс для элементом страницы - обертка SelenideElement для работы с этим элементом
* */

public abstract class BaseComponent {
    protected final static int WAIT_SECONDS = 20;

    protected final SelenideElement baseElement;

    protected BaseComponent(String xpath, Object... attributes) {
        baseElement = $x(String.format(xpath, attributes));
    }

    protected BaseComponent(SelenideElement element) {
        baseElement = element;
    }

    public SelenideElement getElement() {
        return baseElement;
    }
}