package com.magnabyte.cfdi.portal.service.establecimiento;

import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;


/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el servicio de ruta de establecimiento
 */
public interface RutaEstablecimientoService {

	void save(RutaRepositorio rutaRepositorio);

	void update(RutaRepositorio rutaRepositorio);

	RutaRepositorio readById(RutaRepositorio rutaRepositorio);

}
