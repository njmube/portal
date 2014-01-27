package com.magnabyte.cfdi.portal.dao.establecimiento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el acceso a datos de establecimiento
 */
public interface EstablecimientoDao {

	Establecimiento findByClave(Establecimiento establecimiento);
	
	String getRoles(Establecimiento establecimiento);

	Establecimiento readByClave(Establecimiento establecimiento);

	Establecimiento readRutaById(Establecimiento establecimiento);
	
	Establecimiento readFechaCierreById(Establecimiento establecimiento);

	List<Establecimiento> readAll();

	Establecimiento readAllById(Establecimiento establecimiento);

	void update(Establecimiento establecimiento);

	void save(Establecimiento establecimiento);

	Establecimiento read(Establecimiento establecimiento);

	Establecimiento findbyName(Establecimiento establecimiento);
	
}
