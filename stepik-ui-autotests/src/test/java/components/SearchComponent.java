package components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
* Составной элемент страницы, состоящий из строки и кнопки поиска
* */

public class SearchComponent extends BaseComponent {
    private static final String XPATH = "//div[@class='%s']";
    private static final Logger logger = LogManager.getLogger(SearchComponent.class);

    private final Input searchInput = Input.byPlaceHolder("Название курса, автор или предмет");
    private final Button searchButton = Button.byName("Искать");

    protected SearchComponent(String xpath, String attributeValue) {
        super(xpath, attributeValue);
        logger.info("Создан SearchComponent: {}", attributeValue);
    }

    public static SearchComponent byClass(String className) {
        logger.info("Создание SearchComponent по классу: {}", className);
        return new SearchComponent(XPATH, className);
    }

    public void search(String value) {
        logger.info("Поиск по запросу: '{}'", value);
        searchInput.fill(value);
        searchButton.click();
        logger.info("Поиск выполнен");
    }

    public String getCurrentInputValue() {
        String value = searchInput.getValue();
        logger.info("Текущее содержимое поля ввода для поиска: '{}'", value);
        return value;
    }
}