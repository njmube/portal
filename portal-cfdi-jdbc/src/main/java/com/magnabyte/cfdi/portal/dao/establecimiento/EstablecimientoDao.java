package com.magnabyte.cfdi.portal.dao.establecimiento;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;


public interface EstablecimientoDao {

	Establecimiento findByClave(Establecimiento establecimiento);
	
}
