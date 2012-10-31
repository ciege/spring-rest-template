package org.dedeler.template.controller;

import java.util.Locale;

import org.dedeler.template.context.MessageHelper;
import org.dedeler.template.exception.ApiException;
import org.dedeler.template.exception.ErrorCode;
import org.dedeler.template.view.Result;
import org.dedeler.template.view.Result.Builder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AbstractController {

	@ExceptionHandler(ApiException.class)
	public @ResponseBody
	Result handleApiException(ApiException exception, Locale locale) {
		return (new Builder(exception, locale)).build();
	}

	@ExceptionHandler(AccessDeniedException.class)
	public @ResponseBody
	Result handleException(AccessDeniedException exception, Locale locale) {
		ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
		return (new Builder(false)).message(MessageHelper.getMessage(errorCode, locale)).errorCode(errorCode).build();
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody
	Result handleException(Exception exception, Locale locale) {
		ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
		return (new Builder(false)).message(MessageHelper.getMessage(errorCode, locale)).errorCode(errorCode).build();
	}

}
