package com.magnabyte.cfdi.portal.web.cfdi;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

public interface CfdiService {
	
	void generarDocumento(Documento documento);

	void closeOfDay(String fechaCierre, Establecimiento establecimiento);

	void sellarYTimbrarComprobante(Documento documento, int idServicio, CertificadoDigital certificado);

	void recuperarTimbreDocumentosPendientes();
	
}
