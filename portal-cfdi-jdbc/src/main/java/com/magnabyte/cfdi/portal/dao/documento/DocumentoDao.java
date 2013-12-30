package com.magnabyte.cfdi.portal.dao.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface DocumentoDao {
	
	void save(Documento documento);

	void insertDocumentoFolio(Documento documento);

	void insertDocumentoCfdi(Documento documento);

	void insertAcusePendiente(Documento documento);

	List<Documento> obtenerAcusesPendientes();

	void deleteFromAcusePendiente(Documento documento);
}
