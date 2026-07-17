package components;

import helpers.ComponentsConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PriceFilterElement extends BaseComponent {
    private static final Logger logger = LogManager.getLogger(PriceFilterElement.class);

    private static final String XPATH = "//div[@data-name='%s']";

    private final Input minValueInput = Input.byPlaceHolder("0");
    private final Input maxValueInput = Input.byPlaceHolder("250000");

    protected PriceFilterElement(String xpath, String attribute) {
        super(xpath, attribute);
    }

    /**
     * Создает ценовой фильтр по его имени
     * */
    public static PriceFilterElement byName(String name) {
        logger.info(ComponentsConstants.BY_NAME_LOG_MSG, PriceFilterElement.class.getSimpleName(), name);
        return new PriceFilterElement(XPATH, name);
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