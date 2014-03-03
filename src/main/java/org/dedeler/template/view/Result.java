package org.dedeler.template.view;

import java.util.Locale;

import org.dedeler.template.context.ApplicationContextProvider;
import org.dedeler.template.context.MessageHelper;
import org.dedeler.template.exception.ApiException;
import org.dedeler.template.exception.ErrorCode;

/**
 * This class is a wrapper class for any object that is intended to be returned.
 * All controllers returns a Result object.
 * 
 * @param <T>
 */
public class Result {
	
	public static class Builder {
		private ErrorCode errorCode;
		private final boolean success;
		private String message;
		private Object resultObject;
		private static MessageHelper messageHelper;

		public Builder(boolean success) {
			this.success = success;
		}

		public Builder(ApiException e, Locale locale) {
			this.success = false;
			
			this.message = getMessageHelper().getMessage(e.getErrorCode(), locale);
			this.errorCode = e.getErrorCode();
			this.resultObject = null;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder errorCode(ErrorCode errorCode) {
			this.errorCode = errorCode;
			return this;
		}

		public Builder resultObject(Object resultObject) {
			this.resultObject = resultObject;
			return this;
		}

		public Result build() {
			if (this.errorCode == null) {
				if (success) {
					this.errorCode = null;
				}
				else {
					this.errorCode = ErrorCode.UNKNOWN_ERROR;
				}
			}
			return new Result(this);
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

	private Result(Builder resultBuilder) {
		this.message = resultBuilder.message;
		this.success = resultBuilder.success;
		this.resultObject = resultBuilder.resultObject;
		this.errorCode = resultBuilder.errorCode;
	}

	/**
	 * Only valid if success is false
	 */
	private ErrorCode errorCode;
	private boolean success;
	private String message;

	/**
	 * null if success is false
	 */
	private Object resultObject;

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

	/**
	 * null if success is false
	 */
	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}
}
