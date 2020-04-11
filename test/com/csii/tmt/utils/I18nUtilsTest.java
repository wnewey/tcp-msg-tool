package com.csii.tmt.utils;

public class I18nUtilsTest {

    public void testGetMessage() {
        I18nUtils.getMessage("menu.file.message");
        System.out.println(I18nUtils.getMessage("menu.file.message"));
    }
}
