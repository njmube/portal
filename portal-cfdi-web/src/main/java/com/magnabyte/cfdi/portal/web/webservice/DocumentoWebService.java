package com.magnabyte.cfdi.portal.web.webservice;

import com.magnabyte.cfdi.portal.model.documento.Documento;

import mx.gob.sat.cfd._3.Comprobante;

public interface DocumentoWebService {

	Documento timbrarDocumento(Comprobante comprobante);

}