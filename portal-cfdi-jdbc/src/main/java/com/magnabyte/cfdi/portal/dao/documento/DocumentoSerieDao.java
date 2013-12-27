package com.magnabyte.cfdi.portal.dao.documento;

import java.util.Map;

import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface DocumentoSerieDao {

	public Map<String, Object> readSerieAndFolio(Documento documento);

	public void updateFolioSerie(Documento documento);

}
