package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasePage<T extends BasePage<T>> {

    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    protected final SelenideElement basePage;
    protected final Class<T> pageClass;

    protected BasePage(SelenideElement basePage, Class<T> pageClass) {
        this.basePage = basePage;
        this.pageClass = pageClass;
        logger.debug("Инициализация страницы: {}", pageClass.getSimpleName());
    }

    public static <P extends BasePage<P>> P page(Class<P> pageClass) {
        logger.debug("Переход на страницу: {}", pageClass.getSimpleName());
        return Selenide.page(pageClass);
    }
}