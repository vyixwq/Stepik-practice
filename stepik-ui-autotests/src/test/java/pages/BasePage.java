package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.refresh;

public abstract class BasePage<T extends BasePage<T>> {

    protected final SelenideElement basePage;

    protected final Class<T> pageClass;

    protected BasePage(SelenideElement basePage, Class<T> pageClass) {
        this.basePage = basePage;
        this.pageClass = pageClass;
    }

    public static <P extends BasePage<P>> P page(Class<P> pageClass) { return Selenide.page(pageClass); }

    public T refreshPage() {
        refresh();
        return page(pageClass);
    }

    public boolean isOpened() {
        return basePage.exists();
    }
}