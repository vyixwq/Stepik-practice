package components;


public class Button extends BasePageComponent implements ClickableComponent {
    private static final String XPATH = "//button[contains(., '%s')]";
    private static final String CLASS_XPATH = "//button[contains(@class, '%s')]"; // Добавили класс

    protected Button(String xpath, String buttonName) { super(xpath, buttonName); }

    public static Button byName(String name) {
        return new Button(XPATH, name);
    }

    public static Button byClass(String className) {
        return new Button(String.format(CLASS_XPATH, className), className);
    }
}