package components;

import com.codeborne.selenide.ElementsCollection;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.anyMatch;
import static com.codeborne.selenide.Selenide.$$x;

public class CheckBoxFilterElement extends BasePageComponent {
    private static final String XPATH = "//div[@data-name='%s']";

    private final String checkBoxType;

    protected CheckBoxFilterElement(String xpath, String attribute) {
        super(xpath, attribute);
        checkBoxType = attribute;
    }

    public static CheckBoxFilterElement byName(String name) {
        return new CheckBoxFilterElement(XPATH, name);
    }

    public CheckBox getCheckBoxByValue(String value) {
        ElementsCollection checkBoxes = $$x(String.format(".//label[@data-qa-field='%s']", checkBoxType));
        checkBoxes.shouldHave(
                anyMatch(
                        String.format("CheckBox = %s", value),
                        checkBox -> value.equals(checkBox.getAttribute("data-qa-value"))
                ), Duration.ofSeconds(15));

        return CheckBox.byValue(value);
    }
}