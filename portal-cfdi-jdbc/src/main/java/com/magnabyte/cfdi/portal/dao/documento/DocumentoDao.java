package com.magnabyte.cfdi.portal.dao.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.documento.EstadoDocumentoPendiente;

public interface DocumentoDao {
	
	void save(Documento documento);

	void insertDocumentoFolio(Documento documento);

	void insertDocumentoCfdi(Documento documento);

	void insertDocumentoPendiente(Documento documento, EstadoDocumentoPendiente estadoDocumento);

	List<Documento> obtenerAcusesPendientes();
	
	List<Documento> getNombreDocumentoFacturado(List<Integer> idDocumentos);
	
	List<Documento> getDocumentoByCliente(Cliente cliente);

	void deleteFromAcusePendiente(Documento documento);

	void updateDocumentoCliente(DocumentoSucursal documento);

	List<Documento> obtenerDocumentosTimbrePendientes();

	Documento read(Documento documento);

	void deletedDocumentoPendiente(Documento documento);

}
