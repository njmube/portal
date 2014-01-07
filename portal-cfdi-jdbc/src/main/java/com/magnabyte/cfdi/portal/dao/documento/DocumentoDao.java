package com.magnabyte.cfdi.portal.dao.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;

public interface DocumentoDao {
	
	void save(Documento documento);

	void insertDocumentoFolio(Documento documento);

	void insertDocumentoCfdi(Documento documento);

	void insertAcusePendiente(Documento documento);

	List<Documento> obtenerAcusesPendientes();
	
	List<Documento> getNombreDocumentoFacturado(List<Integer> idDocumentos);
	
	List<Documento> getDocumentoByCliente(Cliente cliente);

	void deleteFromAcusePendiente(Documento documento);

	void updateDocumentoTicket(DocumentoSucursal documento);

	Integer readIdByTicket(DocumentoSucursal documento);
}
