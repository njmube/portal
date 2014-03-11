package com.magnabyte.cfdi.portal.service.xml;

import java.io.InputStream;

import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;

public interface DocumentoXmlService {

	DocumentoCorporativo convertXmlSapToCfdi(InputStream xmlSap);

	InputStream convierteComprobanteAStream(Comprobante comprobante);
	
	Comprobante convierteByteArrayAComprobante(byte[] xmlCfdi);

	String obtenerNumCertificado(byte[] xmlCfdi);

	byte[] convierteComprobanteAByteArray(Comprobante comprobante, String encoding);

}
