package components;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import helpers.ComponentsConstants;
/**
 * Класс тумблера - реализует функционал работы с тумблерами
 * */
public class ToggleFilterElement extends BaseComponent {
    private static final Logger logger = LogManager.getLogger(ToggleFilterElement.class);
    private static final String XPATH = "//div[@data-name='%s']//div[@class='ui-toggler']";
    private static final String CHECKBOX_XPATH = ".//input[@type='checkbox']";
    private final SelenideElement checkboxInput;

    protected ToggleFilterElement(String xpath, String attribute) {
        super(xpath, attribute);
        this.checkboxInput = baseElement.$x(CHECKBOX_XPATH);
    }

    /**
    * Создает тумблер по его имени
    * */
    public static ToggleFilterElement byName(String name) {
        return new ToggleFilterElement(XPATH, name);
    }

    /**
    * Метод переключения тумблера
    * */
    public void toggle() {
        logger.info(ComponentsConstants.START_TOGGLE_LOG_MSG);
        baseElement.click();
        logger.info(ComponentsConstants.END_TOGGLE_LOG_MSG);
    }

    /**
    * Метод проверки активен ли тумблер
    * */
    public boolean isActive() {
        return checkboxInput.isSelected();
    }
}