package com.magnabyte.cfdi.portal.service.establecimiento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;


public interface EstablecimientoService {

	Establecimiento findByClave(Establecimiento establecimiento);
	
	Establecimiento readByClave(Establecimiento establecimiento);

	Establecimiento readById(Establecimiento establecimiento);

	List<Establecimiento> readAll();

	Establecimiento readAllById(Establecimiento establecimiento);

	void update(Establecimiento establecimiento);

	void save(Establecimiento establecimiento);
	
}
