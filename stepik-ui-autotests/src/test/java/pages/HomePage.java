package pages;

import components.SearchComponent;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class HomePage extends BasePage<HomePage> {

    private final SearchComponent search = SearchComponent.byClass("catalog__search-form");

    public HomePage() {
        super($x("//h2[@class='catalog-block__title']"), HomePage.class);
    }

    public HomePage openPage() {
        open("https://stepik.org/catalog");
        return this;
    }

    public SearchResultsPage searchFor(String text) {
        search.search(text);
        return page(SearchResultsPage.class);
    }

    public HomePage closeCookieBanner() {
        com.codeborne.selenide.SelenideElement cookieButton = com.codeborne.selenide.Selenide.$x("//button[contains(text(), 'Хорошо')]");
        if (cookieButton.exists()) {
            cookieButton.click();
        }
        return this;
    }
}