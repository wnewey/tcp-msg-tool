package com.csii.tmt.utils;

import org.junit.Test;

public class I18nUtilsTest {
	
	@Test
	public void testGetMessage(){
		I18nUtils.getMessage("menu.file.message");
		System.out.println(I18nUtils.getMessage("menu.file.message"));
	}
}
