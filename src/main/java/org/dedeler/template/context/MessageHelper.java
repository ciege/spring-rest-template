package org.dedeler.template.context;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * @author destan
 */
public class MessageHelper {
	private static MessageSource messageSource;

	static {
		messageSource = ProjectContext.getMessageSource();
	}

	public static String getMessage(String key) {
		return messageSource.getMessage(key, null, "", Locale.ENGLISH);
	}

	public static String getMessage(String key, Locale locale) {
		return messageSource.getMessage(key, null, "", locale);
	}

	public static String getMessage(int errorCode) {
		return messageSource.getMessage(errorCode + "", null, "", new Locale("tr"));
	}

	public static String getMessage(int errorCode, Locale locale) {
		return messageSource.getMessage(errorCode + "", null, "", locale);
	}

}