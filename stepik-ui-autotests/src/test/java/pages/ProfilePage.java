package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import helpers.PagesConstants;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница профиля пользователя на Stepik.
 * Содержит методы для работы с профилем и вкладкой "Отзывы".
 */

public class ProfilePage extends BasePage<ProfilePage> {

    private static final Logger logger = LogManager.getLogger(ProfilePage.class);

    private final SelenideElement reviewsTab = $x(PagesConstants.REVIEWS_TAB_XPATH);
    public final SelenideElement reviewsHeader = $x(PagesConstants.REVIEWS_HEADER_XPATH);

    public ProfilePage() {
        super($x(PagesConstants.PROFILE_PAGE_XPATH), ProfilePage.class);
    }

    public void openReviews() {
        logger.info(PagesConstants.OPEN_REVIEWS_LOG_MESSAGE);
        reviewsTab.shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS)).click();
        reviewsHeader.shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS));
    }
}