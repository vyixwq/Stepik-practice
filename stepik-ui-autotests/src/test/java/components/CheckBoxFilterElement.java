package components;

import com.codeborne.selenide.ElementsCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.anyMatch;
import static com.codeborne.selenide.Selenide.$$x;

/**
* Реализует фильтрацию с помощью чекбоксов
* */
public class CheckBoxFilterElement extends BaseComponent {
    private static final Logger logger = LogManager.getLogger(CheckBoxFilterElement.class);

    private static final String XPATH = "//div[@data-name='%s']";

    private final String checkBoxType;

    protected CheckBoxFilterElement(String xpath, String attribute) {
        super(xpath, attribute);
        checkBoxType = attribute;
        logger.info("Создан CheckBoxFilterElement: {}", attribute);
    }

    /**
     * Создает фильтр по имени чекбокса
     * */
    public static CheckBoxFilterElement byName(String name) {
        logger.info("Создание CheckBoxFilterElement по имени: {}", name);
        return new CheckBoxFilterElement(XPATH, name);
    }

    /**
     * Метод получения чекбокса по значению
     * */
    public CheckBox getCheckBoxByValue(String value) {
        logger.info("Поиск чекбокса со значением: '{}' в фильтре: {}", value, checkBoxType);

        try {
            ElementsCollection checkBoxes = $$x(String.format(".//label[@data-qa-field='%s']", checkBoxType));
            checkBoxes.shouldHave(
                    anyMatch(
                            String.format("CheckBox = %s", value),
                            checkBox -> value.equals(checkBox.getAttribute("data-qa-value"))
                    ), Duration.ofSeconds(WAIT_SECONDS));

            logger.info("Чекбокс со значением '{}' найден в фильтре '{}'", value, checkBoxType);
        }
        catch(Exception e) {
            logger.error("Ошибка получения чекбокса по значению: {}", e.getMessage());
        }

        return CheckBox.byValue(value);
    }
}