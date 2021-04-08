package com.dumbdogdiner.warrior;

import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TranslationUtilTest {

    @Test
    public void testColorGradient() {
        String text = "Lorem ipsum dolor sit amet.";
        String colorStart = "#181818";
        String colorEnd = "#FFFFFF";

        Assertions.assertDoesNotThrow(() -> {
            System.out.println(TranslationUtil.applyColorGradient(text, colorStart, colorEnd, 8));
        });
    }

    @Test
    public void testCapitalize() {
        Assertions.assertDoesNotThrow(() -> {
            String s = "lOrEm iPsum doLOr SIT amET.";

            System.out.println(TranslationUtil.capitalize(s));
        });
    }

}
