package components;

import com.codeborne.selenide.SelenideElement;

/**
* Текстовый элемент одного из результатов поиска
* */

public class CourseCardText
        extends BaseComponent
        implements ClickableComponent
{
    protected CourseCardText(SelenideElement element) {
        super(element);
    }

    /**
     * Метод получения текста данного элемента
     * */
    public String getText() {
        return baseElement.getText();
    }
}