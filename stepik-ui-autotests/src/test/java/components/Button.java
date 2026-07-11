package components;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Реализует функционал работы с кнопками
 * */
public class Button extends BasePageComponent implements ClickableComponent {
    private static final Logger logger = LogManager.getLogger(Button.class);

    private static final String XPATH = "//button[contains(., '%s')]";
    private static final String CLASS_XPATH = "//button[contains(@class, '%s')]"; // Добавили класс

    protected Button(String xpath, String attribute) {
        super(xpath, attribute);
        logger.info("Создана кнопка со значением атрибута: {}", attribute);
    }

    protected Button(SelenideElement element) { super(element); }

    /**
    * Создает кнопку по названию
    * */
    public static Button byName(String name) {
        return new Button(XPATH, name);
    }

    /**
    * Создает кнопку по CSS классу
    * */
    public static Button byClass(String className) {
        return new Button(String.format(CLASS_XPATH, className), className);
    }
}