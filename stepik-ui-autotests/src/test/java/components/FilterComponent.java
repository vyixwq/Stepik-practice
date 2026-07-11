package components;

public class FilterComponent extends BasePageComponent {
    private static final String XPATH = "//div[contains(@class, '%s')]";

    /*
    / Чекбоксы
     */
    private final CheckBoxFilterElement difficultyFilter = CheckBoxFilterElement.byName("difficulty");
    private final CheckBoxFilterElement languageFilter = CheckBoxFilterElement.byName("language");
    private final CheckBoxFilterElement typeFilter = CheckBoxFilterElement.byName("type");

    /*
    / Тумблеры
     */
    private final ToggleFilterElement onlyDiscountToggle = ToggleFilterElement.byName("discount");
    private final ToggleFilterElement onlyCertificateToggle = ToggleFilterElement.byName("certificate");

    /*
    / Ценовой фильтр
     */
    private final PriceFilterElement priceFilter = PriceFilterElement.byName("price");

    protected FilterComponent(String xpath, String attribute) { super(xpath, attribute); }

    public static FilterComponent byClass(String className) {
        return new FilterComponent(XPATH, className);
    }

    public void filterByCheckBox(String checkBoxType, String value) {
        if (!checkBoxType.equals("difficulty") && !checkBoxType.equals("language") && !checkBoxType.equals("type"))
            return;

        CheckBoxFilterElement usingCheckBox = checkBoxType.equals("difficulty") ? difficultyFilter
                : checkBoxType.equals("language") ? languageFilter : typeFilter;

        usingCheckBox.getCheckBoxByValue(value).click();
    }

    public void filterByMinPrice(String minPrice) {
        priceFilter.getminValueInput().fill(minPrice);
    }

    public void filterByMaxPrice(String maxPrice) {
        priceFilter.getmaxValueInput().fill(maxPrice);
    }

    public void filterOnlyFree() {
        priceFilter.clickFree();
    }

    public void filterByToggler(String togglerType) {
        if (!togglerType.equals("discount") && !togglerType.equals("certificate"))
            return;

        ToggleFilterElement usingToggler = togglerType.equals("discount") ? onlyDiscountToggle : onlyCertificateToggle;

        if (! usingToggler.isActive())
            usingToggler.toggle();
    }

}
