package com.magnabyte.cfdi.portal.dao.establecimiento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;


public interface EstablecimientoDao {

	Establecimiento findByClave(Establecimiento establecimiento);
	
	String getRoles(Establecimiento establecimiento);

	Establecimiento readByClave(Establecimiento establecimiento);

	Establecimiento readById(Establecimiento establecimiento);

	List<Establecimiento> readAll();

	Establecimiento readAllById(Establecimiento establecimiento);
	
}
