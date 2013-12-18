package com.magnabyte.cfdi.portal.service.xml;

import java.io.InputStream;

import mx.gob.sat.cfd._3.Comprobante;

public interface XmlConverterService {

	Comprobante convertXmlSapToCfdi(InputStream xmlSap);

}
