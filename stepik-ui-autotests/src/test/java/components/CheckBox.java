package components;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Реализует функционал работы с чекбоксами
 * */
public class CheckBox extends BasePageComponent implements ClickableComponent {
    private static final Logger logger = LogManager.getLogger(CheckBox.class);
    private static final String XPATH = "//label[@data-qa-value='%s']";

    protected CheckBox(String xpath, String attribute) {
        super(xpath, attribute);
        logger.info("Создан чекбокс со значением атрибута: {}", attribute);
    }

    protected CheckBox(SelenideElement element) { super(element); }

    /**
     * Создает чекбокс по его имени
     * */
    public static CheckBox byValue(String value) {
        return new CheckBox(XPATH, value);
    }

    /**
     * Метод получения значения чекбокса
     * */
    public String getValue() {
        String value = baseElement.getCssValue("data-qa-value");
        logger.info("Значение чекбокса: '{}'", value);
        return value;
    }
}
