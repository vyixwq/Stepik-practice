package components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Реализует функционал работы с полем ввода
 * */
public class Input extends BasePageComponent {
    private static final Logger logger = LogManager.getLogger(Input.class);

    private static final String PLACEHOLDER_XPATH = "//input[@placeholder='%s']";
    private static final String TYPE_XPATH = "//input[@type='%s']";

    protected Input(String xpath, String attributeValue) {
        super(xpath, attributeValue);
        logger.info("Создан Input: {}", attributeValue);
    };

    /**
    * Создает поле ввода по его заполнителю (атрибут placeHolder)
    * */
    public static Input byPlaceHolder(String placeHolder) {
        return new Input(PLACEHOLDER_XPATH, placeHolder);
    }

    /**
    * Создает поле ввода по его типу
    * */
    public static Input byType(String type) {
        logger.info("Создан Input по type: {}", type);
        return new Input(TYPE_XPATH, type);
    }

    /**
    * Метод заполнения поля ввода
    * */
    public void fill(String text) {
        logger.info("Заполнение поля значением: {}", text);
        baseElement.clear();
        baseElement.sendKeys(text);
        logger.info("Поле заполнено");
    }

    /*
    * Метод получения текущего значения поля
    * */
    public String getValue() {
        String value = baseElement.getValue();
        logger.info("Текущее значение поля: '{}'", value);
        return value;
    }
}
