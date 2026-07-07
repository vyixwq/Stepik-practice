package components;

/*
* Составной элемент страницы, состоящий из строки и кнопки поиска
* */

public class SearchComponent extends BasePageComponent {
    private static final String XPATH = "//div[@class='%s']";

    private final Input searchInput = Input.byPlaceHolder("Название курса, автор или предмет");
    private final Button searchButton = Button.byName("Искать");

    protected SearchComponent(String xpath, String attributeValue) { super(xpath, attributeValue); }

    public static SearchComponent byClass(String className) {
        return new SearchComponent(XPATH, className);
    }

    public void search(String value) {
        searchInput.fill(value);
        searchButton.click();
    }
}