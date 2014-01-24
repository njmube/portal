package com.magnabyte.cfdi.portal.model.establecimiento.factory;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

public class EstablecimientoFactory {
	
	public static Establecimiento newInstance () {
		return new Establecimiento();
	}
	
	public static Establecimiento newInstance(int id) {
		Establecimiento establecimiento = newInstance();
		establecimiento.setId(id);
		return establecimiento;
	}
	
	public static Establecimiento newInstanceClave(String clave) {
		Establecimiento establecimiento = newInstance();
		establecimiento.setClave(clave);
		return establecimiento;
	}
	
	public static Establecimiento newInstance (String id) {
		return newInstance(Integer.parseInt(id));
	}

}
