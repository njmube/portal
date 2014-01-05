package com.magnabyte.cfdi.portal.service.establecimiento;

import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;

public interface RutaEstablecimientoService {

	void save(RutaRepositorio rutaRepositorio);

	void update(RutaRepositorio rutaRepositorio);

	RutaRepositorio readById(RutaRepositorio rutaRepositorio);

}
