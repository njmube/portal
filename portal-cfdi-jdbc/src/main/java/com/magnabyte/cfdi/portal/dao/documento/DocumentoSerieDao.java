package com.magnabyte.cfdi.portal.dao.documento;

import java.util.Map;

import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface DocumentoSerieDao {

	Map<String, Object> readSerieAndFolio(Documento documento);

	void updateFolioSerie(Documento documento);

	Map<String, Object> readSerieAndFolioDocumento(Documento documento);

}
