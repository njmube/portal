package com.magnabyte.cfdi.portal.model.exception;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el manejo de excepciones de la aplicación
 */
public class PortalException extends RuntimeException {
	
	private static final long serialVersionUID = -6635985921959942246L;

	/**
	 * Constructor por default
	 */
	public PortalException() {
		super();
	}

	/**
	 * Metódo que lanza la excepción con su respectivo mensaje y causa
	 * @param message
	 * @param cause
	 */
	public PortalException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Métedo que lanza la excepción con un mensaje
	 * @param message
	 */
	public PortalException(String message) {
		super(message);
	}

	/**
	 * Métedo que lanza la excepción con un cause
	 * @param cause
	 */
	public PortalException(Throwable cause) {
		super(cause);
	}

}
