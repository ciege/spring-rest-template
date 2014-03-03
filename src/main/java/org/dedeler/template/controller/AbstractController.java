package org.dedeler.template.controller;

import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.dedeler.template.context.MessageHelper;
import org.dedeler.template.exception.ApiException;
import org.dedeler.template.exception.ErrorCode;
import org.dedeler.template.view.Result;
import org.dedeler.template.view.Result.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AbstractController {
	
	@Autowired
	private MessageHelper messageHelper;
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

	@ExceptionHandler(ApiException.class)
	public @ResponseBody
	Result handleApiException(ApiException exception, Locale locale) {
		return (new Builder(exception, locale)).build();
	}

	@ExceptionHandler(AccessDeniedException.class)
	public @ResponseBody
	Result handleException(AccessDeniedException exception, Locale locale) {
		exception.printStackTrace();
		ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
		logger.error("AccessDeniedException occured", exception);
		return (new Builder(false)).message(messageHelper.getMessage(errorCode, locale)).errorCode(errorCode).build();
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	Result handleException(Exception exception, Locale locale) {
		exception.printStackTrace();
		ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
		
		String message = ExceptionUtils.getRootCauseMessage(exception);
		logger.error(message, exception);
		
		return (new Builder(false)).message(messageHelper.getMessage(errorCode, locale) + ": " + message).errorCode(errorCode).build();
	}

}
