package components;

import java.util.List;

public class PriceFilterElement extends BasePageComponent {
    private static final String XPATH = "//div[@data-name='%s']";

    private final Input minValueInput = Input.byPlaceHolder("0");
    private final Input maxValueInput = Input.byPlaceHolder("250000");
    private final Button freeButton = Button.byName("Бесплатно");

    protected PriceFilterElement(String xpath, String attribute) { super(xpath, attribute); }

    public static PriceFilterElement byName(String name) {
        return new PriceFilterElement(XPATH, name);
    }

    public void setPriceRange(String minPrice, String maxPrice) {
        minValueInput.fill(minPrice);
        maxValueInput.fill(maxPrice);
    }

    public void clickFree() {
        freeButton.click();
    }

    public Input getminValueInput() { return minValueInput; }

    public Input getmaxValueInput() { return maxValueInput; }
}