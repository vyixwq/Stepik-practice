package components;

import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import helpers.ComponentsConstants;
/**
* Реализует функционал взаимодействия с фильтрами
* */
public class FilterComponent extends BaseComponent {
    private static final Logger logger = LogManager.getLogger(FilterComponent.class);
    private static final String XPATH = "//div[contains(@class, '%s')]";

    // Чекбоксы
    private final CheckBoxFilterElement difficultyFilter = CheckBoxFilterElement.byName("difficulty");
    private final CheckBoxFilterElement languageFilter = CheckBoxFilterElement.byName("language");
    private final CheckBoxFilterElement typeFilter = CheckBoxFilterElement.byName("type");

    // Тумблеры
    private final ToggleFilterElement onlyDiscountToggle = ToggleFilterElement.byName("discount");
    private final ToggleFilterElement onlyCertificateToggle = ToggleFilterElement.byName("certificate");

    // Ценовой фильтр
    private final PriceFilterElement priceFilter = PriceFilterElement.byName("price");

    protected FilterComponent(String xpath, String attribute) {
        super(xpath, attribute);
    }

    /**
     * Создает FilterComponent по CSS классу
     * */
    public static FilterComponent byClass(String className) {
        logger.info(ComponentsConstants.BY_CLASS_LOG_MSG, FilterComponent.class.getSimpleName(), className);
        return new FilterComponent(XPATH, className);
    }

    /**
     * Фильтрация с помощью чекбокса выбранного типа по выбранному значению
     * */
    public void filterByCheckBox(String checkBoxType, String value) {
        logger.info(ComponentsConstants.FILTER_BY_CHECKBOX_LOG_MSG, checkBoxType, value);

        if (!checkBoxType.equals("difficulty") && !checkBoxType.equals("language") && !checkBoxType.equals("type")) {
            logger.error(ComponentsConstants.FILTER_BY_CHECKBOX_ERR_MSG, checkBoxType);
            return;
        }

        CheckBoxFilterElement usingCheckBox = checkBoxType.equals("difficulty") ? difficultyFilter :
                checkBoxType.equals("language") ? languageFilter : typeFilter;

        usingCheckBox.getElement().scrollTo().shouldBe(visible);

        usingCheckBox.getCheckBoxByValue(value).click();
    }

    /**
     * Фильтрация по наименьшей возможной цене
     * */
    public void filterByMinPrice(String minPrice) {
        priceFilter.getminValueInput().fill(minPrice);
    }

    /**
     * Фильтрация по наибольшей возможной цене
     * */
    public void filterByMaxPrice(String maxPrice) {
        priceFilter.getmaxValueInput().fill(maxPrice);
    }

    /**
     * Фильтрация с помощью тумблера выбранного типа
     * */
    public void filterByToggler(String togglerType) {
        if (!togglerType.equals("discount") && !togglerType.equals("certificate")) {
            logger.error(ComponentsConstants.UNKNOWN_TOGGLER_ERR_MSG, togglerType);
            return;
        }

        ToggleFilterElement usingToggler = togglerType.equals("discount") ? onlyDiscountToggle : onlyCertificateToggle;

        if (!usingToggler.isActive()) {
            usingToggler.toggle();
            logger.info(ComponentsConstants.FILTER_BY_TOGGLER_LOG_MSG, togglerType);
        } else {
            logger.info(ComponentsConstants.TOGGLER_HAS_ALREADY__ACTIVE_LOG_MSG, togglerType);
        }
    }

    /**
     * Проверяет, что чекбокс выбран (фильтр по данному чекбоксу активен)
     * */
    public boolean isCheckBoxSelected(String checkBoxType, String value) {
        if (!checkBoxType.equals("difficulty") && !checkBoxType.equals("language") && !checkBoxType.equals("type")) {
            logger.error(ComponentsConstants.UNKNOWN_SELECTED_CHECKBOX, checkBoxType);
            return false;
        }

        CheckBoxFilterElement filter = checkBoxType.equals("difficulty") ? difficultyFilter :
                checkBoxType.equals("language") ? languageFilter : typeFilter;

        SelenideElement checkBoxInput = filter.getCheckBoxByValue(value).getElement().$x(".//input");

        return checkBoxInput.isSelected();
    }
}
