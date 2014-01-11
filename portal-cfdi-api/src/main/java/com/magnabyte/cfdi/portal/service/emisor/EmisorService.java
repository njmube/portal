package com.magnabyte.cfdi.portal.service.emisor;

import mx.gob.sat.cfd._3.Comprobante.Emisor;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

public interface EmisorService {

	Cliente readClienteVentasMostrador(Establecimiento establecimiento);

	Emisor getEmisorPorEstablecimiento(Establecimiento establecimiento);

}
