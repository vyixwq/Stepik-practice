package components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс тумблера - реализует функционал работы с тумблерами
 * */
public class ToggleFilterElement extends BaseComponent {
    private static final Logger logger = LogManager.getLogger(ToggleFilterElement.class);
    private static final String XPATH = "//div[@data-name='%s']//div[@class='ui-toggler']";

    protected ToggleFilterElement(String xpath, String attribute) {
        super(xpath, attribute);
        logger.info("Создан ToggleFilterElement: {}", attribute);
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
        logger.info("Переключение тумблера");
        baseElement.click();
        logger.info("Тумблер переключен");
    }

    /**
    * Метод проверки активен ли тумблер
    * */
    public boolean isActive() {
        boolean active = baseElement.$x(".//input[@type='checkbox']").isSelected();
        logger.info("Тумблер активен: {}", active);
        return active;
    }
}