package components;

import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        logger.info("Создан FilterComponent: {}", attribute);
    }

    /**
     * Создает FilterComponent по CSS классу
     * */
    public static FilterComponent byClass(String className) {
        return new FilterComponent(XPATH, className);
    }

    /**
     * Фильтрация с помощью чекбокса выбранного типа по выбранному значению
     * */
    public void filterByCheckBox(String checkBoxType, String value) {
        logger.info("Применение фильтра по чекбоксу: {} = {}", checkBoxType, value);

        if (!checkBoxType.equals("difficulty") && !checkBoxType.equals("language") && !checkBoxType.equals("type")) {
            logger.error("Неизвестный тип фильтра: {}", checkBoxType);
            return;
        }

        CheckBoxFilterElement usingCheckBox = checkBoxType.equals("difficulty") ? difficultyFilter :
                checkBoxType.equals("language") ? languageFilter : typeFilter;

        usingCheckBox.getElement().scrollTo().shouldBe(visible);
    
        usingCheckBox.getCheckBoxByValue(value).click();

        logger.info("Фильтр {} = {} применен", checkBoxType, value);
    }

    /**
     * Фильтрация по наименьшей возможной цене
     * */
    public void filterByMinPrice(String minPrice) {
        logger.info("Применение ценового фильтра: от {}", minPrice);
        priceFilter.getminValueInput().fill(minPrice);
        logger.info("Фильтр 'Цена от: {}' применен", minPrice);
    }

    /**
     * Фильтрация по наибольшей возможной цене
     * */
    public void filterByMaxPrice(String maxPrice) {
        logger.info("Применение ценового фильтра: до {}", maxPrice);
        priceFilter.getmaxValueInput().fill(maxPrice);
        logger.info("Фильтр 'Цена до {}' применен", maxPrice);
    }

    /**
     * Фильтрация с помощью тумблера выбранного типа
     * */
    public void filterByToggler(String togglerType) {
        logger.info("Применение тумблера: {}", togglerType);

        if (!togglerType.equals("discount") && !togglerType.equals("certificate")) {
            logger.error("Неизвестный тип тумблера: {}", togglerType);
            return;
        }

        ToggleFilterElement usingToggler = togglerType.equals("discount") ? onlyDiscountToggle : onlyCertificateToggle;

        if (!usingToggler.isActive()) {
            usingToggler.toggle();
            logger.info("Тумблер {} включен", togglerType);
        } else {
            logger.info("Тумблер {} уже активен", togglerType);
        }
    }

    /**
     * Проверяет, что чекбокс выбран (фильтр по данному чекбоксу активен)
     * */
    public boolean isCheckBoxSelected(String checkBoxType, String value) {
        if (!checkBoxType.equals("difficulty") && !checkBoxType.equals("language") && !checkBoxType.equals("type")) {
            logger.error("Неизвестный тип чекбокса: {}", checkBoxType);
            return false;
        }

        CheckBoxFilterElement filter = checkBoxType.equals("difficulty") ? difficultyFilter :
                checkBoxType.equals("language") ? languageFilter : typeFilter;

        SelenideElement checkBoxInput = filter.getCheckBoxByValue(value).getElement().$x(".//input");

        return checkBoxInput.isSelected();
    }
}
