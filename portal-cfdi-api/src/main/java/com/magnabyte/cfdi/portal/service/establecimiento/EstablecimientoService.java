package com.magnabyte.cfdi.portal.service.establecimiento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;


/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el servicio de establecimiento
 */
public interface EstablecimientoService {

	Establecimiento findByClave(Establecimiento establecimiento);
	
	Establecimiento readByClave(Establecimiento establecimiento);

	Establecimiento readRutaById(Establecimiento establecimiento);
	
	String readFechaCierreById(Establecimiento establecimiento);

	List<Establecimiento> readAll();

	Establecimiento readAllById(Establecimiento establecimiento);

	void update(Establecimiento establecimiento);

	void save(Establecimiento establecimiento);

	Establecimiento read(Establecimiento establecimiento);

	boolean exist(Establecimiento establecimiento);
	
}
