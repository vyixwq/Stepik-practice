package helpers;

import com.codeborne.selenide.Selenide;

public class WindowHandler {
    /**
     * Переключает фокус драйвера на окно по его индексу.
     */
    public static void switchToWindow(int index) {
        Selenide.switchTo().window(index);
    }
}