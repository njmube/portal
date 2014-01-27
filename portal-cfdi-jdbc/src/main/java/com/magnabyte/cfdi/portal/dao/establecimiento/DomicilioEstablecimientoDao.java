package com.magnabyte.cfdi.portal.dao.establecimiento;

import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el acceso a datos de domicilio de establecimiento
 */
public interface DomicilioEstablecimientoDao {

	void save(DomicilioEstablecimiento domicilioEstablecimiento);

	Estado readEstado(Estado estado);

	void update(DomicilioEstablecimiento domicilio);

	DomicilioEstablecimiento readById(DomicilioEstablecimiento domicilioEstablecimiento);

	Estado findEstado(Estado estado);


}
