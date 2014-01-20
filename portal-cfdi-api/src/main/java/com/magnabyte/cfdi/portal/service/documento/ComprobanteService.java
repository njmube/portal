package com.magnabyte.cfdi.portal.service.documento;

import mx.gob.sat.cfd._3.Comprobante;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface ComprobanteService {

	boolean sellarComprobante(Comprobante comprobante,
			CertificadoDigital certificado);

	Comprobante obtenerComprobantePor(Cliente cliente, Ticket ticket,
			Integer idDomicilioFiscal, Establecimiento establecimiento,
			TipoDocumento tipoDocumento);

	Cliente obtenerClienteDeComprobante(Comprobante comprobante);

}
