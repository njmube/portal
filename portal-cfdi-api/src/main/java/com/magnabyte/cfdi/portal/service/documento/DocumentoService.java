package com.magnabyte.cfdi.portal.service.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Interfáz que representa el servicio de documento
 */
public interface DocumentoService {
	
	void insertDocumentoCfdi(Documento documento);

	void insertDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumento);

	void guardarDocumento(Documento documento);

	List<Documento> obtenerAcusesPendientes();

	List<Documento> getDocumentos(Cliente cliente, String fechaInicial, String fechaFinal);

	byte[] recuperarDocumentoArchivo(String fileName, Integer idEstablecimiento,
			String extension);
	
	void envioDocumentosFacturacion(String para, String fileName,
			Integer idEstablecimiento);

	boolean isArticuloSinPrecio(String claveArticulo);

	List<Documento> obtenerDocumentosTimbrePendientes();

	Documento read(Documento documento);

	void deleteDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumentoPendiente);

	void updateDocumentoStatusAndXml(Documento documento);

	byte[] recuperarDocumentoXml(Documento documento);

	Documento findById(Documento documento);

	void envioDocumentosFacturacionPorXml(String para, String fileName,
			Integer idDocumento);

	byte[] recuperarDocumentoPdf(Documento documento);

	void saveAcuseCfdiXmlFile(Documento documento);

	Documento findByEstadoTicket(String archivoOrigen, Establecimiento establecimiento, 
			List<Ticket> devoluciones);

	Cliente readClienteFromDocumento(Documento documento);

	void findBySerieFolioImporte(Documento documento);

	void updateDocumentoStatus(Documento documento);

	void guardarDocumentoRefacturado(Documento documento);

}
