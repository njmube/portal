package com.magnabyte.cfdi.portal.service.documento;

import mx.gob.sat.cfd._3.Comprobante;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface DocumentoService {

	boolean sellarDocumento(Comprobante comprobante);
	
	Comprobante obtenerComprobantePor(Cliente cliente, Ticket ticket, Integer domicilioFiscal);

}
