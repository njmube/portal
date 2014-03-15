package com.magnabyte.cfdi.portal.service.cfdi.v32;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;

public interface CfdiV32Service {
	boolean sellarComprobante(Comprobante comprobante, CertificadoDigital certificado);

	boolean isValidComprobanteXml(Comprobante comprobante);

	String obtenerCadena(Comprobante comprobante, String xsltLocation);
	
}
