package com.magnabyte.cfdi.portal.dao.documento;

import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Conceptos;
import com.magnabyte.cfdi.portal.model.documento.Documento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el acceso a datos de detalle de documento
 */
public interface DocumentoDetalleDao {
	void save(Documento documento);

	Conceptos read(Documento documento);
}
