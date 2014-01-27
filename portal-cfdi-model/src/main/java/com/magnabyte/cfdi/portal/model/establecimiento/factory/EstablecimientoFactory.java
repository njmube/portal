package com.magnabyte.cfdi.portal.model.establecimiento.factory;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa la factoria de establecimiento
 */
public class EstablecimientoFactory {
	
	/**
	 * Metodo que devuelve la instancia vacia de establecimiento
	 * @return establecimiento {@link Establecimiento}
	 */
	public static Establecimiento newInstance () {
		return new Establecimiento();
	}
	
	/**
	 * Metodo que devuelve una nueva instancia de establcimiento que
	 * recibe un parametro de tipo entero y asigna el identificador único
	 * al establecimiento
	 * @param id
	 * @return establecimiento {@link Establecimiento}
	 */
	public static Establecimiento newInstance(int id) {
		Establecimiento establecimiento = newInstance();
		establecimiento.setId(id);
		return establecimiento;
	}
	
	/**
	 * Metodo que devuelve una nueva instancia de establcimiento que
	 * recibe un parametro de tipo cadena y asigna el identificador único
	 * al establecimiento
	 * @param id
	 * @return establecimiento {@link Establecimiento}
	 */
	public static Establecimiento newInstance (String id) {
		return newInstance(Integer.parseInt(id));
	}
	
	/**
	 * Metodo que devuelve una nueva instancia de establcimiento que
	 * recibe un parametro de tipo cadena y asigna la calve
	 * al establecimiento
	 * @param id
	 * @return establecimiento {@link Establecimiento}
	 */
	public static Establecimiento newInstanceClave(String clave) {
		Establecimiento establecimiento = newInstance();
		establecimiento.setClave(clave);
		return establecimiento;
	}

}
