package components;

import com.codeborne.selenide.SelenideElement;

/*
* Интерфейс для кликабельных объектов с соответствующим методом click
* */

public interface ClickableComponent {
    SelenideElement getElement();

    default void click() {
        getElement().click();
    }
}
