package com.magnabyte.cfdi.portal.web.cfdi;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.ListaTickets;

public interface CfdiService {
	
	void generarDocumento(Documento documento);

	void closeOfDay(Establecimiento establecimiento, ListaTickets tickets);

	void sellarYTimbrarComprobante(Documento documento, int idServicio, CertificadoDigital certificado);

	void recuperarTimbreDocumentosPendientes();
	
}
