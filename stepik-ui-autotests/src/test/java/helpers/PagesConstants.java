package helpers;

public final class PagesConstants {
    // CoursePage
    public static final String COURSE_PAGE_TITLE_XPATH = "//h1";
    public static final String GET_TITLE_LOG_MESSAGE = "Заголовок курса: '{}'";

    // HomePage
    public static final String HOME_SEARCH_COMPONENT_CLASS = "catalog__search-form";
    public static final String HOME_PAGE_XPATH = "//h2[@class='catalog-block__title']";
    public static final String HOME_PAGE_LINK = "https://stepik.org/catalog";
    public static final String OPEN_HOMEPAGE_LOG_MESSAGE = "Открытие главной страницы Stepik";
    public static final String DO_SEARCH_LOG_MESSAGE = "Поиск по запросу: '{}'";

    // ProfilePage
    public static final String REVIEWS_TAB_XPATH = "//a[contains(@href, '/reviews')]";
    public static final String REVIEWS_HEADER_XPATH = "//h2[contains(@class, 'course-review-sort__title')]";
    public static final String PROFILE_PAGE_XPATH = "//div[contains(@class, 'user-profile')]";
    public static final String OPEN_REVIEWS_LOG_MESSAGE = "Открытие вкладки 'Отзывы'";

    // SearchResultsPage
    public static final String CLEAR_BUTTON_CLASS = "search-form__reset";
    public static final String SEARCH_CLASS = "catalog-w__search-form";
    public static final String FILTER_CLASS = "search-form-filters catalog-w__filters";
    public static final String NEXT_BUTTON_NAME = "Далее";
    public static final String COOKIE_BUTTON_XPATH = "//button[contains(text(), 'Хорошо')]";
    public static final String PAGINATION_BLOCK_CSS_SELECTOR = ".catalog__paging";
    public static final String RESULTS_PAGE_XPATH = "//h1[text()='Поиск']";
    public static final String GET_COURSE_CARD_ERR_MSG = "Карточка курса с индексом {} не найдена: {}";
    public static final String CLICK_NEXT_ERR_MSG = "Ошибка при переходе на следующую страницу: {}";
    public static final String OPEN_FIRST_COURSE_LOG_MSG = "Открытие первого курса";
    public static final String OPEN_FIRST_COURSE_ERR_MSG = "Ошибка при переходе на следующую страницу: {}";
    public static final String APPLY_MIN_PRICE_FILTER_LOG_MSG = "Применение минимальной цены: {}";
    public static final String APPLY_MAX_PRICE_FILTER_LOG_MSG = "Применение максимальной цены: {}";
    public static final String GET_FIRST_TITLE_ERR_MSG = "Не удалось получить заголовок первого курса: {}";
    public static final String SEARCH_AGAIN_LOG_MSG = "Повторный поиск по запросу: '{}'";
    public static final String APPLY_TOGGLE_FILTER_LOG_MSG = "Применение тумблера: {}";
    public static final String PAGE_BODY_XPATH = "//body";
    public static final String NOTHING_RESULTS_STRING = "ничего не найдено";
    public static final String RESET_BUTTON_XPATH = "//*[contains(text(), 'Сбросить фильтры')]";
    public static final String IS_NOTHING_FOUND_ERR_MSG = "Текст не найден. Видна ли кнопка 'Сбросить фильтры': {}";
    public static final String COURSE_CARD_CSS_SELECTOR = ".course-card";
}
