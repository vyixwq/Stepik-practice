package components;

import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import helpers.ComponentsConstants;
import tests.SearchTests;
/*
* Составной элемент страницы, состоящий из строки и кнопки поиска
* */

public class SearchComponent extends BaseComponent {
    private static final String XPATH = "//div[@class='%s']";
    private static final Logger logger = LogManager.getLogger(SearchComponent.class);

    private final Input searchInput = Input.byPlaceHolder(ComponentsConstants.INPUT_PLACEHOLDER);
    private final Button searchButton = Button.byName(ComponentsConstants.BUTTON_NAME);

    protected SearchComponent(String xpath, String attributeValue) {
        super(xpath, attributeValue);
    }

    public static SearchComponent byClass(String className) {
        logger.info(ComponentsConstants.BY_CLASS_LOG_MSG, SearchComponent.class.getSimpleName(), className);
        return new SearchComponent(XPATH, className);
    }

    public void search(String value) {
        logger.info(ComponentsConstants.SEARCH_LOG_MSG, value);
        searchInput.fill(value);
        searchButton.click();
    }

    public String getCurrentInputValue() {
        String value = searchInput.getValue();
        logger.info(ComponentsConstants.GET_INPUT_VALUE_LOG_MSG, value);
        return value;
    }
}