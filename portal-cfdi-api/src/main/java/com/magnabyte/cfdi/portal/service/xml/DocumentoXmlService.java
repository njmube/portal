package com.magnabyte.cfdi.portal.service.xml;

import java.io.InputStream;

import mx.gob.sat.cfd._3.Comprobante;

public interface DocumentoXmlService {

	Comprobante convertXmlSapToCfdi(InputStream xmlSap);

	InputStream convierteComprobanteAStream(Comprobante comprobante);
	
	Comprobante convierteByteArrayAComprobante(byte[] xmlCfdi);

	String obtenerNumCertificado(byte[] xmlCfdi);

	byte[] convierteComprobanteAByteArray(Comprobante comprobante, String encoding);

}
