package com.magnabyte.cfdi.portal.service.documento;

import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Conceptos;
import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface DocumentoDetalleService {

	void save (Documento documento);
	
	Conceptos read(Documento documento);
}
