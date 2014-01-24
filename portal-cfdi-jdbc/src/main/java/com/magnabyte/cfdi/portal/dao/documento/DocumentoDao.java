package com.magnabyte.cfdi.portal.dao.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;

public interface DocumentoDao {
	
	void save(Documento documento);

	void insertDocumentoFolio(Documento documento);

	void insertDocumentoCfdi(Documento documento);

	void insertDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumento);

	List<Documento> obtenerAcusesPendientes();
	
	List<Documento> getNombreDocumentoFacturado(List<Integer> idDocumentos);
	
	List<Documento> getDocumentoByCliente(Cliente cliente);

	void updateDocumentoCliente(DocumentoSucursal documento);

	List<Documento> obtenerDocumentosTimbrePendientes();

	Documento read(Documento documento);

	void deletedDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumentoPendiente);

	Documento readDocumentoFolio(Documento documento);

	void updateDocumentoXmlCfdi(Documento documento);

	Documento readDocumentoCfdiById(Documento documento);

	void saveAcuseCfdiXmlFile(Documento documento);

	Documento readDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumento);

	Documento readDocumentoFolioById(Documento documento);

	Cliente readClienteFromDocumento(Documento documentoOrigen);

}
