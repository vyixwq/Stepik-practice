package components;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;

/*
* Интерфейс для кликабельных объектов с соответствующим методом click
* */

public interface ClickableComponent {
    SelenideElement getElement();

    default void click() {
        getElement().shouldBe(visible, Duration.ofSeconds(20)).click();
    }

}
