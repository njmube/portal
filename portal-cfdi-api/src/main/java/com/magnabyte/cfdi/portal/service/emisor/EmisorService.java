package com.magnabyte.cfdi.portal.service.emisor;

import mx.gob.sat.cfd._3.Comprobante.Emisor;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el servicio de emisor
 */
public interface EmisorService {

	Cliente readClienteVentasMostrador(Establecimiento establecimiento);

	Emisor getEmisorPorEstablecimiento(Establecimiento establecimiento);

}
