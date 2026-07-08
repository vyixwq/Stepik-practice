package components;


public class Button
        extends BasePageComponent
        implements ClickableComponent
{
    private static final String XPATH = "//button[contains(., '%s')]";

    public Button(String xpath, String buttonName) { super(xpath, buttonName); }

    public static Button byName(String name) {
        return new Button(XPATH, name);
    }
}
