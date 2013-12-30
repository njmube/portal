package com.magnabyte.cfdi.portal.service.establecimiento;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;


public interface EstablecimientoService {

	Establecimiento findByClave(Establecimiento establecimiento);
	
	Establecimiento readByClave(Establecimiento establecimiento);

	Establecimiento readById(Establecimiento establecimiento);
	
}
