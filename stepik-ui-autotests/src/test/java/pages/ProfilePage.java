package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница профиля пользователя на Stepik.
 * Содержит методы для работы с профилем и вкладкой "Отзывы".
 */

public class ProfilePage extends BasePage<ProfilePage> {

    private static final Logger logger = LogManager.getLogger(ProfilePage.class);

    private final SelenideElement reviewsTab = $x("//a[contains(@href, '/reviews')]");
    public final SelenideElement reviewsHeader = $x("//h2[contains(@class, 'course-review-sort__title')]");

    public ProfilePage() {
        super($x("//div[contains(@class, 'user-profile')]"), ProfilePage.class);
    }

    public void openReviews() {
        logger.info("Открытие вкладки 'Отзывы'");
        reviewsTab.shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS)).click();
        reviewsHeader.shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS));
    }
}