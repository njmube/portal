package com.magnabyte.cfdi.portal.service.documento;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface DocumentoService {

	boolean sellarComprobante(Comprobante comprobante, CertificadoDigital certificado);
	
	Comprobante obtenerComprobantePor(Cliente cliente, Ticket ticket, 
			Integer domicilioFiscal, Establecimiento establecimiento);
	
	void insertDocumentoCfdi(Documento documento);

	Cliente obtenerClienteDeComprobante(Comprobante comprobante);

	void insertAcusePendiente(Documento documento);

	void guardarDocumento(Documento documento);

	List<Documento> obtenerAcusesPendientes();

	void deleteFromAcusePendiente(Documento documento);
	
	List<Documento> getDocumentos(Cliente cliente);

	byte[] recuperarDocumentoArchivo(String fileName, Integer idEstablecimiento,
			String extension);
	
	void envioDocumentosFacturacion(String para, String fileName,
			Integer idEstablecimiento);

	boolean isArticuloSinPrecio(String claveArticulo);

}
