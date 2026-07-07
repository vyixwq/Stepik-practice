package components;

import com.codeborne.selenide.SelenideElement;

/*
* Текстовый элемент одного из результатов поиска
* */

public class CourseCardText
        extends BasePageComponent
        implements ClickableComponent
{

    private static final String XPATH =
            "//a[contains(@class,'course-card__%s') and contains(., '%s')]";

    protected CourseCardText(SelenideElement parent, String type, String text) {
        super(parent.$x(String.format(XPATH, type, text)));
    }

    protected CourseCardText(SelenideElement element) { super(element); }

    public String getText() {
        return baseElement.getText();
    }
}