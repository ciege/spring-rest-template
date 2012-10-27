package org.dedeler.template.context;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.dedeler.template.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * @author destan featuring yasakbulut
 */
@Component
public class MessageHelper {
	private static MessageSource messageSource;

	private static String defaultLocale;

	static {
		messageSource = ProjectContext.getMessageSource();
	}

	public static String getMessage(String key) {
		return getMessage(key, new Locale(defaultLocale));
	}

	public static String getMessage(String key, Locale locale) {
		return messageSource.getMessage(key, null, "", locale);
	}

	public static String getMessage(ErrorCode errorCode) {
		return getMessage(errorCode, new Locale(defaultLocale));
	}

	public static String getMessage(ErrorCode errorCode, Locale locale) {
		return messageSource.getMessage(errorCode.name(), null, "", locale);
	}

	@Value("${app.defaultLocale}")
	// TODO remove ugly hack
	private String privateName;

	@PostConstruct
	public void init() {
		defaultLocale = privateName;
	}

}