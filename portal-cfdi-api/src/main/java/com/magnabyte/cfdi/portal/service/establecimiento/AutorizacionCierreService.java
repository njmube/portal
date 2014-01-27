package com.magnabyte.cfdi.portal.service.establecimiento;

import com.magnabyte.cfdi.portal.model.commons.Usuario;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el servicio para autorización de cierre del día
 */
public interface AutorizacionCierreService {

	public boolean autorizar(Usuario usuario);
}
