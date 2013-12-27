package com.magnabyte.cfdi.portal.dao.documento;

import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface DocumentoDao {
	
	public void save(Documento documento);

	public void insertDocumentoFolio(Documento documento);

	public void insertDocumentoCfdi(Documento documento);
}
