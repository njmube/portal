package com.magnabyte.cfdi.portal.service.documento;

import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Conceptos;
import com.magnabyte.cfdi.portal.model.documento.Documento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 * 
 * Interfáz que representa el servicio de documento detalle
 */
public interface DocumentoDetalleService {

	void save (Documento documento);
	
	Conceptos read(Documento documento);
}
