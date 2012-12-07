package org.dedeler.template.exception;

public class ApiDaoLayerException extends ApiException {

	private static final long serialVersionUID = 1L;

	public ApiDaoLayerException(Throwable cause) {
		super(ErrorCode.DAO_LAYER_EXCEPTION, cause);
	}
}
