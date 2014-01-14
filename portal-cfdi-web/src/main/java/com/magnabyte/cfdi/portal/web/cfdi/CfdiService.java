package com.magnabyte.cfdi.portal.web.cfdi;

import javax.servlet.http.HttpServletRequest;

import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

public interface CfdiService {
	
	void generarDocumento(Documento documento, HttpServletRequest request, boolean isVentasMostrador);

	void closeOfDay(String fechaCierre, Establecimiento establecimiento, HttpServletRequest request);
	
}
