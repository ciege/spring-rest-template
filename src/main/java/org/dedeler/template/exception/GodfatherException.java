package org.dedeler.template.exception;

import org.dedeler.template.context.MessageHelper;
import org.dedeler.template.view.Result;

public class GodfatherException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String message;

	public GodfatherException(int errorCode) {
		this.errorCode = errorCode;
		this.message = MessageHelper.getMessage(errorCode);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Result<Object> toResult() {
		final Result<Object> result = new Result<Object>(false, this.getErrorCode());
		result.setMessage(this.getMessage());
		return result;
	}
}