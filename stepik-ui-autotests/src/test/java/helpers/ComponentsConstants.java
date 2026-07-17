package helpers;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import components.CheckBoxFilterElement;
import components.CourseCardText;
import components.ToggleFilterElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;

public final class ComponentsConstants {
    public static final String BY_NAME_LOG_MSG = "Создание {} по имени: {}";
    public static final String BY_CLASS_LOG_MSG = "Создание {} по классу: {}";

    // CheckBoxFilterElement
    public static final String GET_CHECKBOX_BY_VALUE_LOG_MSG = "Поиск чекбокса со значением: '{}' в фильтре: {}";
    public static final String GET_CHECKBOX_BY_VALUE_ERR_MSG = "Ошибка получения чекбокса по значению: {}";

    //ClickableComponent
    public static final String CLICK_LOG_MSG = "Клик по элементу";
    public static final String CLICK_ERR_MSG = "Ошибка: не удалось выполнить клик по элементу: {}";

    // CourseCardItem
    public static final String TITLE_XPATH = ".//a[contains(@class,'course-card__title')]";
    public static final String FAVORITE_BUTTON_XPATH = ".//button[contains(@class, 'course-card__bookmark')]";
    public static final String FIRST_AUTHOR_XPATH = ".//a[contains(@class,'course-card__author')]";
    public static final String OLD_PRICE_XPATH = ".//span[contains(@class, 'only-price-regular')]";
    public static final String FAVORITE_FILLED_ICON_XPATH = ".//*[contains(@class, 'favorite-filled')]";
    public static final String ADD_TO_WISHLIST_LOG_MSG = "Добавление курса в избранное (JS-клик)";
    public static final String FIRST_AUTHOR_CLICK_LOG_MSG = "Клик по первому автору (JS-клик)";
    public static final String FIRST_AUTHOR_CLICK_ERR_MSG = "Ошибка при клике по автору: {}";
    public static final String HAS_OLD_PRICE_LOG_MSG = "Старая цена обнаружена на карточке";
    public static final String HAS_OLD_PRICE_ERR_MSG = "Старая цена НЕ найдена";
    public static final String COURSE_IN_WISHLIST_LOG_MSG = "Успех: внутри кнопки найдена иконка заполненного сердечка";
    public static final String COURSE_IN_WISHLIST_ERR_MSG = "Иконка 'favorite-filled' не найдена";

    // FilterComponent
    public static final String FILTER_BY_CHECKBOX_LOG_MSG = "Применение фильтра по чекбоксу: {} = {}";
    public static final String FILTER_BY_CHECKBOX_ERR_MSG = "Неизвестный тип чекбокс-фильтра: {}";
    public static final String UNKNOWN_TOGGLER_ERR_MSG = "Неизвестный тип тумблера: {}";
    public static final String FILTER_BY_TOGGLER_LOG_MSG = "Тумблер {} включен";
    public static final String TOGGLER_HAS_ALREADY__ACTIVE_LOG_MSG = "Тумблер {} уже активен";
    public static final String UNKNOWN_SELECTED_CHECKBOX = "Выбран чекбокс неизвестного типа: {}";

    // Input
    public static final String FILL_LOG_MSG = "Заполнение поля значением: {}";

    // SearchComponent
    public static final String INPUT_PLACEHOLDER = "Название курса, автор или предмет";
    public static final String BUTTON_NAME = "Искать";
    public static final String SEARCH_LOG_MSG = "Поиск по запросу: '{}'";
    public static final String GET_INPUT_VALUE_LOG_MSG = "Текущее содержимое поля ввода для поиска: '{}'";

    // ToggleFilterElement
    public static final String START_TOGGLE_LOG_MSG = "Переключение тумблера";
    public static final String END_TOGGLE_LOG_MSG = "Тумблер переключен";

}