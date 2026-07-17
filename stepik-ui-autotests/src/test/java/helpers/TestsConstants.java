package helpers;

public final class TestsConstants {
    // FilterTests
    public static final String MIN_PRICE_VAL = "5000";
    public static final String MAX_PRICE_VAL = "7000";
    public static final int PROMO_SEARCH_LIMIT = 20;
    public static final int FIRST_ITEM = 0;

    public static final String SEARCH_QUERY_LINUX = "Linux";
    public static final String SEARCH_QUERY_JAVA = "Java";
    public static final String SEARCH_QUERY_QA_AUTO = "QA Automation";
    public static final String SEARCH_QUERY_ML = "Machine Learning";
    public static final String SEARCH_QUERY_DS = "Data Science";
    public static final String SEARCH_QUERY_INVALID = "zxcvbnm123";
    public static final String SEARCH_QUERY_BACKEND = "Backend";
    public static final String SEARCH_QUERY_WEB_DESIGN = "Web Design";

    public static final String FILTER_LANGUAGE = "language";
    public static final String FILTER_TYPE = "type";
    public static final String FILTER_DIFFICULTY = "difficulty";
    public static final String TOGGLER_CERTIFICATE = "certificate";
    public static final String TOGGLER_DISCOUNT = "discount";

    public static final String VAL_LANG_EN = "en";
    public static final String VAL_LANG_RU = "ru";
    public static final String VAL_TYPE_SPEC = "spec";
    public static final String VAL_DIFF_EASY = "easy";
    public static final String VAL_DIFF_NORMAL = "normal";
    public static final String VAL_DIFF_HARD = "hard";

    public static final String EXPECTED_TXT_LINUX = "linux";
    public static final String EXPECTED_TXT_JAVA = "java";
    public static final String EXPECTED_TXT_QA = "qa";
    public static final String EXPECTED_TXT_AUTO = "automation";
    public static final String EXPECTED_TXT_DATA = "data";
    public static final String EXPECTED_TXT_SCIENCE = "science";

    public static final String ASSERT_MSG_LINUX = "Первый найденный курс '%s' должен содержать ключевое слово 'Linux'";
    public static final String ASSERT_MSG_JAVA = "Первый найденный курс в ценовом диапазоне '%s' должен соответствовать запросу 'Java'";
    public static final String ASSERT_MSG_QA_AUTO = "Найденный курс '%s' должен соответствовать тематике 'QA Automation'";
    public static final String ASSERT_MSG_DS_TITLE = "Заголовок должен содержать 'Data Science'";
    public static final String ASSERT_MSG_LANG_ACTIVE = "Фильтр 'Английский' должен остаться активным";
    public static final String ASSERT_MSG_NOTHING_FOUND = "На странице должно быть сообщение 'ничего не найдено'";
    public static final String ASSERT_MSG_PROMO = "В результатах фильтрации должен быть хотя бы один курс с отображаемой старой ценой";
    public static final String ASSERT_MSG_FAVORITE = "Иконка сердечка должна изменить состояние на активное";

    // SearchTests
    public static final int TARGET_WINDOW_INDEX = 1;
    public static final int DEFAULT_WAIT_TIME_SEC = 5;

    public static final String SEARCH_QUERY_PYTHON = "Python";
    public static final String SEARCH_QUERY_CPP = "C++";
    public static final String SEARCH_QUERY_AUTHOR = "Оксана Еськова";

    public static final String EXPECTED_TXT_PYTHON = "python";

    public static final String ASSERT_MSG_PYTHON_TITLE = "Заголовок курса '%s' должен содержать ключевое слово 'Python'";
    public static final String ASSERT_MSG_SEARCH_EMPTY = "Поле поиска должно быть пустым после нажатия на крестик!";
    public static final String ASSERT_MSG_CATALOG_NOT_EMPTY = "Каталог общего поиска должен успешно отображаться после сброса параметров";

}
