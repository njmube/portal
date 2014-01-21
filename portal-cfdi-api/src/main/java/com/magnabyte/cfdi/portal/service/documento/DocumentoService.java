package com.magnabyte.cfdi.portal.service.documento;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;

public interface DocumentoService {
	
	void insertDocumentoCfdi(Documento documento);

	void insertDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumento);

	void guardarDocumento(Documento documento);

	List<Documento> obtenerAcusesPendientes();

	void deleteFromAcusePendiente(Documento documento);
	
	List<Documento> getDocumentos(Cliente cliente);

	byte[] recuperarDocumentoArchivo(String fileName, Integer idEstablecimiento,
			String extension);
	
	void envioDocumentosFacturacion(String para, String fileName,
			Integer idEstablecimiento);

	boolean isArticuloSinPrecio(String claveArticulo);

	List<Documento> obtenerDocumentosTimbrePendientes();

	Documento read(Documento documento);

	void deleteDocumentoPendiente(Documento documento);

	void updateDocumentoXmlCfdi(Documento documento);

	byte[] recuperarDocumentoXml(Documento documento);

	Documento findById(Documento documento);

	void envioDocumentosFacturacionPorXml(String para, String fileName,
			Integer idDocumento, HttpServletRequest request);

	byte[] recuperarDocumentoPdf(Documento documento, ServletContext context);

	void saveAcuseCfdiXmlFile(Documento documento);

}
