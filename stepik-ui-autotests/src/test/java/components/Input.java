package components;


public class Input extends BasePageComponent {
    private static final String PLACEHOLDER_XPATH = "//input[@placeholder='%s']";

    protected Input(String xpath, String attributeValue) { super(xpath, attributeValue); };

    public static Input byPlaceHolder(String placeHolder) {
        return new Input(PLACEHOLDER_XPATH, placeHolder);
    }

    public void fill(String text) {
        baseElement.clear();
        baseElement.sendKeys(text);
    }
}
