package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Базовый класс для всех страниц.
 * Содержит общую логику для работы со страницами:
 * - хранение элемента-маркера страницы
 * - переход между страницами
 */

public abstract class BasePage<T extends BasePage<T>> {
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected static final int WAIT_SECONDS = 20;

    protected final SelenideElement basePage;
    protected final Class<T> pageClass;

    protected BasePage(SelenideElement basePage, Class<T> pageClass) {
        this.basePage = basePage;
        this.pageClass = pageClass;
    }

    public static <P extends BasePage<P>> P page(Class<P> pageClass) {
        return Selenide.page(pageClass);
    }
}