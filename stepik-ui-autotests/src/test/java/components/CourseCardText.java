package components;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
* Текстовый элемент одного из результатов поиска
* */

public class CourseCardText
        extends BasePageComponent
        implements ClickableComponent
{
    private static final Logger logger = LogManager.getLogger(CourseCardText.class);
    private static final String XPATH =
            "//a[contains(@class,'course-card__%s') and contains(., '%s')]";

    protected CourseCardText(SelenideElement parent, String type, String text) {
        super(parent.$x(String.format(XPATH, type, text)));
    }

    protected CourseCardText(SelenideElement element) {
        super(element);
    }

    /**
     * Метод получения текста данного элемента
     * */
    public String getText() {
        String text = baseElement.getText();
        logger.debug("Получен текст: '{}'", text);
        return text;
    }
}