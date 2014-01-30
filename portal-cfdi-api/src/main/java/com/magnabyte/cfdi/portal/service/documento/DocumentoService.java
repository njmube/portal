package com.magnabyte.cfdi.portal.service.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface DocumentoService {
	
	void insertDocumentoCfdi(Documento documento);

	void insertDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumento);

	void guardarDocumento(Documento documento);

	List<Documento> obtenerAcusesPendientes();

	List<Documento> getDocumentos(Cliente cliente);

	byte[] recuperarDocumentoArchivo(String fileName, Integer idEstablecimiento,
			String extension);
	
	void envioDocumentosFacturacion(String para, String fileName,
			Integer idEstablecimiento);

	boolean isArticuloSinPrecio(String claveArticulo);

	List<Documento> obtenerDocumentosTimbrePendientes();

	Documento read(Documento documento);

	void deleteDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumentoPendiente);

	void updateDocumentoXmlCfdi(Documento documento);

	byte[] recuperarDocumentoXml(Documento documento);

	Documento findById(Documento documento);

	void envioDocumentosFacturacionPorXml(String para, String fileName,
			Integer idDocumento);

	byte[] recuperarDocumentoPdf(Documento documento);

	void saveAcuseCfdiXmlFile(Documento documento);

	Documento findByEstadoTicket(String archivoOrigen, Establecimiento establecimiento, 
			List<Ticket> devoluciones);

	Cliente readClienteFromDocumento(Documento documento);

}
