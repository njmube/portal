package com.magnabyte.cfdi.portal.model.exception;

public class PortalException extends RuntimeException {
	
	private static final long serialVersionUID = -6635985921959942246L;

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
