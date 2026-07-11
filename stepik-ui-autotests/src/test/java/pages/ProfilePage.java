package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage extends BasePage<ProfilePage> {

    public final SelenideElement reviewsTab = $x("//a[contains(@href, '/reviews')]");
    public final SelenideElement reviewsHeader = $x("//h2[contains(@class, 'course-review-sort__title')]");

    public ProfilePage() {
        super($x("//div[contains(@class, 'user-profile')]"), ProfilePage.class);
        logger.debug("Страница профиля инициализирована");
    }

    public void openReviews() {
        logger.info("Открытие вкладки 'Отзывы'");
        reviewsTab.shouldBe(Condition.visible).click();
        logger.info("Вкладка 'Отзывы' открыта");
    }
}