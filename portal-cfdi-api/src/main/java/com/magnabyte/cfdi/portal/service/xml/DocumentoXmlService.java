package com.magnabyte.cfdi.portal.service.xml;

import java.io.InputStream;

import mx.gob.sat.cfd._3.Comprobante;

public interface DocumentoXmlService {

	Comprobante convertXmlSapToCfdi(InputStream xmlSap);

	InputStream convierteComprobanteAStream(Comprobante comprobante);
	
	byte[] convierteComprobanteAByteArray(Comprobante comprobante);

	Comprobante convierteByteArrayAComprobante(byte[] xml);

}
