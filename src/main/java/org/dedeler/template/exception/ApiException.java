package org.dedeler.template.exception;

import java.util.Locale;

import org.dedeler.template.context.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ErrorCode errorCode;
	private String message;
	
	@Autowired
	private MessageHelper messageHelper;

	public ApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = messageHelper.getMessage(errorCode.toString(), Locale.ENGLISH);
	}

	public ApiException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.message = messageHelper.getMessage(errorCode.toString(), Locale.ENGLISH);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}