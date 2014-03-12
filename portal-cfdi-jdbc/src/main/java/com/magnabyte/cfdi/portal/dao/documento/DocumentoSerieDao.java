package com.magnabyte.cfdi.portal.dao.documento;

import java.util.Map;

import com.magnabyte.cfdi.portal.model.documento.Documento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el acceso a datos de documento serie
 */
public interface DocumentoSerieDao {

	Map<String, Object> readNextSerieAndFolio(Documento documento);

	void updateFolioSerie(Documento documento);

	Map<String, Object> readSerieAndFolioDocumento(Documento documento);

}
