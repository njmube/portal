package com.magnabyte.cfdi.portal.dao.documento;

import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface DocumentoDao {
	
	void save(Documento documento);

	void insertDocumentoFolio(Documento documento);

	void insertDocumentoCfdi(Documento documento);

	void insertAcusePendiente(Documento documento);
}
