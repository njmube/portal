package com.magnabyte.cfdi.portal.model.exception;

public class PortalException extends RuntimeException {

	public PortalException() {
		super();
	}

	public PortalException(String message, Throwable cause) {
		super(message, cause);
	}

	public PortalException(String message) {
		super(message);
	}

	public PortalException(Throwable cause) {
		super(cause);
	}

}
