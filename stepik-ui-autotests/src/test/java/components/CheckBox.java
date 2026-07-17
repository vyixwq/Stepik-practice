package components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Реализует функционал работы с чекбоксами
 * */
public class CheckBox extends BaseComponent implements ClickableComponent {
    private static final Logger logger = LogManager.getLogger(CheckBox.class);
    private static final String XPATH = "//label[@data-qa-value='%s']";

    protected CheckBox(String xpath, String attribute) {
        super(xpath, attribute);
    }

    /**
     * Создает чекбокс по его имени
     * */
    public static CheckBox byValue(String value) {
        return new CheckBox(XPATH, value);
    }
}
