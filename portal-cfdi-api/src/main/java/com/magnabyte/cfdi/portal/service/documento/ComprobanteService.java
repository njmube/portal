package com.magnabyte.cfdi.portal.service.documento;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 * 
 * Interfáz que representa el servicio de comprobante 
 */
public interface ComprobanteService {

	boolean sellarComprobante(Comprobante comprobante,
			CertificadoDigital certificado);

	Comprobante obtenerComprobantePor(Cliente cliente, Ticket ticket,
			Integer idDomicilioFiscal, Establecimiento establecimiento,
			TipoDocumento tipoDocumento);

	Cliente obtenerClienteDeComprobante(Comprobante comprobante);

	Comprobante obtenerComprobantePor(Documento documento, Cliente cliente,
			Integer idDomicilioFiscal, Establecimiento establecimiento);

	void createFechaDocumento(Comprobante comprobante);

	void depurarReceptor(Documento facturaDocumentoNuevo);

}
