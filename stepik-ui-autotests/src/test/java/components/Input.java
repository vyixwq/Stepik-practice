package components;


public class Input extends BasePageComponent {
    private static final String PLACEHOLDER_XPATH = "//input[@placeholder='%s']";
    private static final String TYPE_XPATH = "//input[@type='%s']";

    protected Input(String xpath, String attributeValue) { super(xpath, attributeValue); };

    public static Input byPlaceHolder(String placeHolder) {
        return new Input(PLACEHOLDER_XPATH, placeHolder);
    }

    public static Input byType(String type) {
        return new Input(TYPE_XPATH, type);
    }

    public void fill(String text) {
        baseElement.clear();
        baseElement.sendKeys(text);
    }

    public String getValue() { return baseElement.getValue(); }
}
