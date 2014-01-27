package com.magnabyte.cfdi.portal.dao.establecimiento;

import java.util.Date;
import java.util.List;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;


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

	void updateFechaCierre(Establecimiento establecimiento, Date fechaUltimoCierre, Date fechaCierreSiguiente);
	
}
