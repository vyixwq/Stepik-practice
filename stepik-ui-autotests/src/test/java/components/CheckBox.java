package components;

import com.codeborne.selenide.SelenideElement;

public class CheckBox extends BasePageComponent implements ClickableComponent {
    private static final String XPATH = "//label[@data-qa-value='%s']";

    protected CheckBox(String xpath, String attribute) { super(xpath, attribute); }

    protected CheckBox(SelenideElement element) { super(element); }

    public static CheckBox byValue(String value) {
        return new CheckBox(XPATH, value);
    }

    public String getValue() {
        return baseElement.getCssValue("data-qa-value");
    }
}
