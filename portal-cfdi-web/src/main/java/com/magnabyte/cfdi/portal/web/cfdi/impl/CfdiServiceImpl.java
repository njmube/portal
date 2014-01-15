package com.magnabyte.cfdi.portal.web.cfdi.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.cfd._3.Comprobante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.documento.EstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;
import com.magnabyte.cfdi.portal.service.certificado.CertificadoService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.service.emisor.EmisorService;
import com.magnabyte.cfdi.portal.web.cfdi.CfdiService;
import com.magnabyte.cfdi.portal.web.webservice.DocumentoWebService;

@Service("cfdiService")
public class CfdiServiceImpl implements CfdiService {
	
	private static final Logger logger = LoggerFactory.getLogger(CfdiServiceImpl.class);
	
	@Autowired
	private DocumentoWebService documentoWebService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private CertificadoService certificadoService;
	
	@Autowired
	private EmisorService emisorService;
	
	@Value("${hora.inicio}")
	private int horaInicio;
	
	@Value("${hora.cierre}")
	private int horaCierre;
	
	@Value("${generic.rfc.ventas.mostrador}")
	private String rfcVentasMostrador;
	
	@Override
	public void generarDocumento(Documento documento, HttpServletRequest request) {
		logger.debug("cfdiService...");
		int idServicio = documentoWebService.obtenerIdServicio();
		CertificadoDigital certificado = certificadoService.readVigente(documento.getComprobante());
		documento.setFechaFacturacion(new Date());
		documentoService.guardarDocumento(documento);
		if(documento.isVentasMostrador()) {
			ticketService.guardarTicketsCierreDia(documento);
		}
		sellarYTimbrarComprobante(documento, request, idServicio, certificado);
	}

	@Override
	public void sellarYTimbrarComprobante(Documento documento,
			HttpServletRequest request, int idServicio,
			CertificadoDigital certificado) {
		if (documentoService.sellarComprobante(documento.getComprobante(), certificado)) {
			if (documentoWebService.timbrarDocumento(documento, request, idServicio)) {
				documentoService.insertDocumentoCfdi(documento);
				documentoService.insertDocumentoPendiente(documento, EstadoDocumentoPendiente.ACUSE_PENDIENTE);
				if(documento instanceof DocumentoSucursal) {
					ticketService.updateEstadoFacturado((DocumentoSucursal) documento);
					if (((DocumentoSucursal) documento).isRequiereNotaCredito()) {
						generarDocumentoNcr(documento, request, idServicio);
					}
				}
			}
		}
	}
	
	public void recuperarTimbreDocumentosPendientes() {
		List<Documento> documentosTimbrePendientes = new ArrayList<Documento>();
		documentosTimbrePendientes = documentoService.obtenerDocumentosTimbrePendientes();		
		
		if(!documentosTimbrePendientes.isEmpty()) {
			int idServicio = documentoWebService.obtenerIdServicio();
			
			for(Documento documento : documentosTimbrePendientes) {
				documentoService.read(documento);
				CertificadoDigital certificado = certificadoService.readVigente(documento.getComprobante());
				sellarYTimbrarComprobante(documento, null, idServicio, certificado);
			}
		}
	}
	
	private void generarDocumentoNcr(Documento documento,
			HttpServletRequest request, int idServicio) {
		DocumentoSucursal documentoNcr = new DocumentoSucursal();
		documentoNcr.setTicket(((DocumentoSucursal) documento).getTicket());
		documentoNcr.getTicket().setTipoEstadoTicket(TipoEstadoTicket.GUARDADO_NCR);
		
		Cliente cliente = emisorService.readClienteVentasMostrador(documento.getEstablecimiento());
		cliente.setRfc(rfcVentasMostrador);
		int domicilioFiscal = cliente.getDomicilios().get(0).getId();
		Comprobante comprobante = documentoService.obtenerComprobantePor(cliente, documentoNcr.getTicket(), 
				domicilioFiscal, documento.getEstablecimiento(), TipoDocumento.NOTA_CREDITO);
		
		documentoNcr.setCliente(cliente);
		documentoNcr.setComprobante(comprobante);
		documentoNcr.setEstablecimiento(documento.getEstablecimiento());
		documentoNcr.setTipoDocumento(TipoDocumento.NOTA_CREDITO);
		documentoNcr.setRequiereNotaCredito(((DocumentoSucursal) documento).isRequiereNotaCredito());
		documentoNcr.setFechaFacturacion(new Date());
		CertificadoDigital certificado = certificadoService.readVigente(documento.getComprobante());
		documentoService.guardarDocumento(documentoNcr);
		if (documentoService.sellarComprobante(documentoNcr.getComprobante(), certificado)) {
			if (documentoWebService.timbrarDocumento(documentoNcr, request, idServicio)) {
				documentoService.insertDocumentoCfdi(documentoNcr);
				documentoService.insertDocumentoPendiente(documentoNcr, EstadoDocumentoPendiente.ACUSE_PENDIENTE);
				if(documentoNcr instanceof DocumentoSucursal) {
					ticketService.updateEstadoNcr((DocumentoSucursal) documentoNcr);
				}
			}
		}
	}

	@Transactional
	@Override
	public void closeOfDay(String fechaCierre, Establecimiento establecimiento, HttpServletRequest request) {
		List<Ticket> ventas = new ArrayList<Ticket>();
		List<Ticket> ventasDevueltas = new ArrayList<Ticket>();
		List<Ticket> devoluciones = new ArrayList<Ticket>();
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (hora > horaCierre) {
			
			ticketService.closeOfDay(establecimiento, 
					FechasUtils.specificStringFormatDate(fechaCierre, "dd-MM-yyyy", "yyyyMMdd"), 
					ventas, devoluciones);
			
			logger.debug("devoluciones {}", devoluciones.size());
			
			logger.debug("Antes " + ventas.size());
			
			prepararDocumentoNcr(devoluciones);
			
			filtraDevolucionesVentas(ventas, devoluciones, ventasDevueltas);
			
			logger.debug("Despues " + ventas.size());
			logger.debug("ventasDevueltas " + ventasDevueltas.size());
			
			Ticket ticketVentasMostrador = ticketService.crearTicketVentasMostrador(ventas, establecimiento);
			Cliente cliente = emisorService.readClienteVentasMostrador(establecimiento);
			cliente.setRfc(rfcVentasMostrador);
			int domicilioFiscal = cliente.getDomicilios().get(0).getId();
			Comprobante comprobante = documentoService.obtenerComprobantePor(cliente, ticketVentasMostrador, domicilioFiscal, establecimiento, TipoDocumento.FACTURA);
			Documento documento = new Documento();
			documento.setEstablecimiento(establecimiento);
			documento.setCliente(cliente);
			documento.setComprobante(comprobante);
			documento.setTipoDocumento(TipoDocumento.FACTURA);
			documento.setVentas(ventas);
			documento.setVentasMostrador(true);
			
			generarDocumento(documento, request);
			
		} else {
			logger.error("El cierre del dia actual es posible realizarlo hasta despues del cierre de la tienda");
			throw new PortalException("El cierre del dia actual es posible realizarlo hasta despues del cierre de la tienda");
		}
	}
	
	private void prepararDocumentoNcr(List<Ticket> devoluciones) {
		Set<String> archivosOrigenDevolucion = new HashSet<String>();
		
		for(Ticket devolucion : devoluciones) {
			archivosOrigenDevolucion.add(devolucion.getTransaccion().
					getPartidasDevolucion().get(0).getTicketFileOrigen());
		}
		logger.debug(archivosOrigenDevolucion.toString());
		
		for (Iterator<String> it = archivosOrigenDevolucion.iterator(); it.hasNext(); ){
			if(ticketService.isTicketProcesado(it.next())){
				//FIXME Desarrollar generacion de ncr
			}
		}
	}	

	private void filtraDevolucionesVentas(List<Ticket> ventas,
			List<Ticket> devoluciones, List<Ticket> ventasDevueltas) {
	
		Set<String> archivosOrigenDevolucion = new HashSet<String>();
		
		for(Ticket devolucion : devoluciones) {
			archivosOrigenDevolucion.add(devolucion.getTransaccion().
					getPartidasDevolucion().get(0).getTicketFileOrigen());
		}
		logger.debug(archivosOrigenDevolucion.toString());
		
		for(String archivoOrigen : archivosOrigenDevolucion) {
			for(int i = 0; i < ventas.size(); i++) {
				if(ventas.get(i).getNombreArchivo().equals(archivoOrigen)) {
					ventasDevueltas.add(ventas.get(i));
					ventas.remove(i);
					break;
				}
			}				
		}
	}
}
