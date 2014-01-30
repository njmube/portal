package com.magnabyte.cfdi.portal.dao.emisor;

import com.magnabyte.cfdi.portal.model.cfdi.v32.TUbicacion;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el acceso a datos de emisor
 */
public interface EmisorDao {

	EmpresaEmisor read(EmpresaEmisor empresa);

	TUbicacion readLugarExpedicion(Establecimiento establecimiento);
}
