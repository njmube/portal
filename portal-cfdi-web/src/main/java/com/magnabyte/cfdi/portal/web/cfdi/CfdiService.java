package com.magnabyte.cfdi.portal.web.cfdi;

import javax.servlet.http.HttpServletRequest;

import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface CfdiService {
	
	void generarDocumento(Documento documento, HttpServletRequest request);
	
}
