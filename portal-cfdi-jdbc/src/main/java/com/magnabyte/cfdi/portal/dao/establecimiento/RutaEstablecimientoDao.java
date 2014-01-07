package com.magnabyte.cfdi.portal.dao.establecimiento;

import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;


public interface RutaEstablecimientoDao {

	void save(RutaRepositorio rutaRepositorio);

	void update(RutaRepositorio rutaRepositorio);

	RutaRepositorio readById(RutaRepositorio rutaRepositorio);

}
