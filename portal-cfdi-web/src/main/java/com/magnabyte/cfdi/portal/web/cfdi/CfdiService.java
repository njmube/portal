package com.magnabyte.cfdi.portal.web.cfdi;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.ListaTickets;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el servicio de cfdi
 */
public interface CfdiService {
	
	void generarDocumento(Documento documento);

	void closeOfDay(Establecimiento establecimiento, ListaTickets tickets);

	void sellarYTimbrarComprobante(Documento documento, int idServicio, CertificadoDigital certificado);

	void recuperarTimbreDocumentosPendientes();

	void envioDocumentosFacturacion(String email, String fileName,
			Integer idDocumento);
	
	void recuperaTicketsRest(Establecimiento establecimiento, String fechaCierre);

	void generarDocumentoCorp(Documento documento);
	
}
