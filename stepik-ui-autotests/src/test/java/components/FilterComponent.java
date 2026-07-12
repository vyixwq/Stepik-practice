package components;

import static com.codeborne.selenide.Condition.visible;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
* Реализует функционал взаимодействия с фильтрами
* */
public class FilterComponent extends BasePageComponent {
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
     * Создает чекбокс по CSS классу
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
            logger.warn("Неизвестный тип фильтра: {}", checkBoxType);
            return;
        }

        CheckBoxFilterElement usingCheckBox = checkBoxType.equals("difficulty") ? difficultyFilter : checkBoxType.equals("language") ? languageFilter : typeFilter;

    // 1. Сначала скроллим к блоку фильтра и ждем его видимости
        usingCheckBox.getElement().scrollTo().shouldBe(visible);
    
    // 2. Затем кликаем по конкретному чекбоксу (используем метод .click() из нашего интерфейса, так как там есть логгер)
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
     * Фильтр по бесплатным курсам
     * */
    public void filterOnlyFree() {
        logger.info("Применение фильтра 'Бесплатно'");
        priceFilter.clickFree();
        logger.info("Фильтр 'Бесплатно' применен");
    }

    /**
     * Фильтрация с помощью тумблера выбранного типа
     * */
    public void filterByToggler(String togglerType) {
        logger.info("Применение тумблера: {}", togglerType);

        if (!togglerType.equals("discount") && !togglerType.equals("certificate")) {
            logger.warn("Неизвестный тип тумблера: {}", togglerType);
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

    public boolean isCheckBoxSelected(String checkBoxType, String value) {
        CheckBoxFilterElement filter = checkBoxType.equals("difficulty") ? difficultyFilter : checkBoxType.equals("language") ? languageFilter : typeFilter;
        return filter.getCheckBoxByValue(value).getElement().$x(".//input").isSelected();
    }

}
