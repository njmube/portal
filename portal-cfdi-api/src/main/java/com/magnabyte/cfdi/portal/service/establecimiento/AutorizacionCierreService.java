package com.magnabyte.cfdi.portal.service.establecimiento;

/**
 * Interfáz que representa el servicio de autorización de cierre del día
 * 
 * @author Edgar Pérez
 *
 */
public interface AutorizacionCierreService {

	public boolean autorizar(String usuario, String password);
}
