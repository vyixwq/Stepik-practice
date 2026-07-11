package components;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import java.util.List;

/*
 * Элемент списка результатов - хранит базисные элементы Название и авторы курса
 * */

public class CourseCardItem extends BasePageComponent {

    private final CourseCardText title;
    private final List<CourseCardText> authors;
    private final Button favoriteButton;

    public CourseCardItem(SelenideElement element) {
        super(element);

        title = new CourseCardText(
                baseElement.$x(".//a[contains(@class,'course-card__title')]")
        );

        authors = baseElement
                .$$x(".//a[contains(@class,'course-card__author')]")
                .stream()
                .map(CourseCardText::new)
                .toList();

        favoriteButton = new Button(baseElement.$x(".//button[contains(@class, 'course-card__bookmark')]"));
    }

    public void addToWishlist() { favoriteButton.click(); }

    public void clickFirstAuthor() {
        baseElement.$x(".//a[contains(@class,'course-card__author')]")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
    }

    public CourseCardText getTitle() {
        return title;
    }

    public List<CourseCardText> getAuthors() {
        return authors;
    }

}