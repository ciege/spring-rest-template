package org.dedeler.template.exception;

import java.util.Locale;

import org.dedeler.template.context.ApplicationContextProvider;
import org.dedeler.template.context.MessageHelper;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ErrorCode errorCode;
	private String message;
	
	private MessageHelper messageHelper;

	public ApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = getMessageHelper().getMessage(errorCode.toString(), Locale.ENGLISH);
	}

	public ApiException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.message = getMessageHelper().getMessage(errorCode.toString(), Locale.ENGLISH);
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
	
	/**
	 * lazy initialization of messageHelper which can't be autowired because this is not a @component
	 * @return
	 */
	private MessageHelper getMessageHelper(){
		if(messageHelper == null){
			messageHelper = ApplicationContextProvider.getContext().getBean(MessageHelper.class);
		}
		return messageHelper;
	}

}