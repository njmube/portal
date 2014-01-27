package com.magnabyte.cfdi.portal.dao.establecimiento;

import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el acceso a datos de ruta de establecimiento
 */
public interface RutaEstablecimientoDao {

	void save(RutaRepositorio rutaRepositorio);

	void update(RutaRepositorio rutaRepositorio);

	RutaRepositorio readById(RutaRepositorio rutaRepositorio);

}
