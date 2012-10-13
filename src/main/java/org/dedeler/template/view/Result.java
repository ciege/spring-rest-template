package org.dedeler.template.view;

import org.dedeler.template.exception.ErrorCode;

/**
 * This class is a wrapper class for any object that is intended to be returned.
 * All controllers returns a Result object.
 * 
 * @param <T>
 */
public class Result<T> {
	/**
	 * Only valid if success if false
	 */
	private ErrorCode errorCode;
	private boolean success;
	private String message;

	public Result(boolean success, T resultObject) {
		this.success = success;
		this.resultObject = resultObject;
	}

	public Result(boolean success, ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.success = success;
	}

	/**
	 * null if success is false
	 */
	private T resultObject;

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResultObject() {
		return resultObject;
	}

	public void setResultObject(T resultObject) {
		this.resultObject = resultObject;
	}
}
