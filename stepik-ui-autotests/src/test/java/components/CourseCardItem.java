package components;

import com.codeborne.selenide.SelenideElement;

import java.util.List;

/*
* Элемент списка результатов - хранит базисные элементы Название и авторы курса
* */

public class CourseCardItem extends BasePageComponent {

    private final CourseCardText title;
    private final List<CourseCardText> authors;

    public CourseCardItem(SelenideElement element) {
        super(element);

        title = new CourseCardText(
                baseElement.$x(".//a[contains(@class,'course-card__text')]")
        );

        authors = baseElement
                .$$x(".//a[contains(@class,'course-card__author')]")
                .stream()
                .map(CourseCardText::new)
                .toList();
    }

    public CourseCardText getTitle() {
        return title;
    }

    public List<CourseCardText> getAuthors() {
        return authors;
    }

    public boolean hasTitle(String text, boolean isFullMatch) {
        return isFullMatch ? title.getText().equalsIgnoreCase(text) : title.getText().contains(text);
    }

    public boolean hasAuthor(String name, boolean isFullMatch) {
        return authors.stream().anyMatch(
                author -> isFullMatch ? author.getText().equalsIgnoreCase(name) : author.getText().contains(name));
    }


}