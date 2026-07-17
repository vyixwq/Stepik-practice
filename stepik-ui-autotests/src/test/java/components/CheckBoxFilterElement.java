package components;

import com.codeborne.selenide.ElementsCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.anyMatch;
import static com.codeborne.selenide.Selenide.$$x;

import helpers.ComponentsConstants;
/**
* Реализует фильтрацию с помощью чекбоксов
* */
public class CheckBoxFilterElement extends BaseComponent {
    private static final Logger logger = LogManager.getLogger(CheckBoxFilterElement.class);

    private static final String XPATH = "//div[@data-name='%s']";
    private static final String TYPE_XPATH = ".//label[@data-qa-field='%s']";

    private final String checkBoxType;
    private final ElementsCollection checkBoxes;

    protected CheckBoxFilterElement(String xpath, String attribute) {
        super(xpath, attribute);
        checkBoxType = attribute;
        checkBoxes = $$x(String.format(TYPE_XPATH, checkBoxType));
    }

    /**
     * Создает фильтр по имени чекбокса
     * */
    public static CheckBoxFilterElement byName(String name) {
        logger.info(ComponentsConstants.BY_NAME_LOG_MSG, CheckBoxFilterElement.class.getSimpleName(), name);
        return new CheckBoxFilterElement(XPATH, name);
    }

    /**
     * Метод получения чекбокса по значению
     * */
    public CheckBox getCheckBoxByValue(String value) {
        logger.info(ComponentsConstants.GET_CHECKBOX_BY_VALUE_LOG_MSG, value, checkBoxType);

        try {
            checkBoxes.shouldHave(
                    anyMatch(
                            String.format("CheckBox = %s", value),
                            checkBox -> value.equals(checkBox.getAttribute("data-qa-value"))
                    ), Duration.ofSeconds(WAIT_SECONDS));

        }
        catch(Exception e) {
            logger.error(ComponentsConstants.GET_CHECKBOX_BY_VALUE_ERR_MSG, e.getMessage());
        }

        return CheckBox.byValue(value);
    }
}