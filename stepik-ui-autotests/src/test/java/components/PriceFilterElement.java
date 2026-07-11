package components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PriceFilterElement extends BasePageComponent {
    private static final Logger logger = LogManager.getLogger(PriceFilterElement.class);

    private static final String XPATH = "//div[@data-name='%s']";

    private final Input minValueInput = Input.byPlaceHolder("0");
    private final Input maxValueInput = Input.byPlaceHolder("250000");
    private final Button freeButton = Button.byName("Бесплатно");

    protected PriceFilterElement(String xpath, String attribute) {
        super(xpath, attribute);
        logger.info("Создан PriceFilterElement: {}", attribute);
    }

    /**
     * Создает ценовой фильтр по его имени
     * */
    public static PriceFilterElement byName(String name) {
        return new PriceFilterElement(XPATH, name);
    }

    /**
     * Метод клика по кнопке 'Бесплатно'
     * */
    public void clickFree() {
        logger.info("Нажатие кнопки 'Бесплатно'");
        freeButton.click();
        logger.info("Кнопка 'Бесплатно' нажата");
    }

    /**
     * Метод получения поля 'Цена от'
     * */
    public Input getminValueInput() { return minValueInput; }

    /**
     * Метод получения поля 'Цена до'
     * */
    public Input getmaxValueInput() { return maxValueInput; }
}