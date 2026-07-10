package components;

public class ToggleFilterElement extends BasePageComponent {
    private static final String XPATH = "//div[@data-name='%s']//div[@class='ui-toggler']";
    //    private final Input toggleInput = Input.byType("checkbox");

    protected ToggleFilterElement(String xpath, String attribute) { super(xpath, attribute); }

    public static ToggleFilterElement byName(String name) {
        return new ToggleFilterElement(XPATH, name);
    }

    public void toggle() {
        baseElement.click();
    }

    public boolean isActive() {
        return baseElement.$x(".//input[@type='checkbox']").isSelected();
    }
}