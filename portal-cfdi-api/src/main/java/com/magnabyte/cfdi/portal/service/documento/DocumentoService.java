package com.magnabyte.cfdi.portal.service.documento;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.cfd._3.Comprobante;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface DocumentoService {

	boolean sellarComprobante(Comprobante comprobante, CertificadoDigital certificado);
	
	Comprobante obtenerComprobantePor(Cliente cliente, Ticket ticket, 
			Integer domicilioFiscal, Establecimiento establecimiento, TipoDocumento tipoDocumento);
	
	void insertDocumentoCfdi(Documento documento);

	Cliente obtenerClienteDeComprobante(Comprobante comprobante);

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
