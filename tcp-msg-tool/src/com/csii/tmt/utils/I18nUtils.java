package com.csii.tmt.utils;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csii.tmt.domain.AppConfig;

public abstract class I18nUtils {

	private static ResourceBundle srcBundle;

	private static Log log = LogFactory.getLog(I18nUtils.class);

	static {
		String laguage = AppConfig.getInstance().getLanguage();
		log.info("Current Language=>" + laguage);
		String[] region = laguage.split("_");
		Locale locale = new Locale(region[0], region[1]);
		srcBundle = ResourceBundle.getBundle("com.csii.tmt.msg.appmsg", locale);
	}

	public static String getMessage(String msgKey) {
		String message = srcBundle.getString(msgKey);
		// 解决资源文件乱码
		try {
			message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (log.isErrorEnabled()) {
				log.error("unsupported encoding " + e.getMessage());
			}
		}
		return message;
	}
}
