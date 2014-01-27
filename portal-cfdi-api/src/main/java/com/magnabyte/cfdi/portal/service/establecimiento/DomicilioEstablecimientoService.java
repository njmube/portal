package com.magnabyte.cfdi.portal.service.establecimiento;

import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el servicio de domicilio de establecimiento
 */
public interface DomicilioEstablecimientoService {

	void save(DomicilioEstablecimiento domicilioEstablecimiento);

	void update(DomicilioEstablecimiento domicilioEstablecimiento);

	Estado readEstado(Estado estado);

	DomicilioEstablecimiento readById(
			DomicilioEstablecimiento domicilioEstablecimiento);

	Estado findEstado(Estado estado);

}
