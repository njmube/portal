package com.magnabyte.cfdi.portal.service.establecimiento;

import com.magnabyte.cfdi.portal.model.commons.Usuario;

/**
 * Interfáz que representa el servicio de autorización de cierre del día
 * 
 * @author Edgar Pérez
 *
 */
public interface AutorizacionCierreService {

	public boolean autorizar(Usuario usuario);
}
