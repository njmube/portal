package com.magnabyte.cfdi.portal.service.xml;

import mx.gob.sat.cfd._3.Comprobante;

public interface XmlConverterService {

	Comprobante convertXmlSapToCfdi(String rutaRepositorio, String fileName);

}
