package components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import helpers.ComponentsConstants;
/**
 * Реализует функционал работы с полем ввода
 * */
public class Input extends BaseComponent {
    private static final Logger logger = LogManager.getLogger(Input.class);

    private static final String PLACEHOLDER_XPATH = "//input[@placeholder='%s']";

    protected Input(String xpath, String attributeValue) {
        super(xpath, attributeValue);
    }

    /**
    * Создает поле ввода по его заполнителю (атрибут placeHolder)
    * */
    public static Input byPlaceHolder(String placeHolder) {
        return new Input(PLACEHOLDER_XPATH, placeHolder);
    }

    /**
    * Метод заполнения поля ввода
    * */
    public void fill(String text) {
        logger.info(ComponentsConstants.FILL_LOG_MSG, text);
        baseElement.clear();
        baseElement.sendKeys(text);
    }

    /*
    * Метод получения текущего значения поля
    * */
    public String getValue() { return baseElement.getValue(); }
}
