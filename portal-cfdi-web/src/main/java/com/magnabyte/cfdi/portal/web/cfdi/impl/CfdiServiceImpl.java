package com.magnabyte.cfdi.portal.web.cfdi.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.ListaTickets;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;
import com.magnabyte.cfdi.portal.service.certificado.CertificadoService;
import com.magnabyte.cfdi.portal.service.documento.ComprobanteService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.service.emisor.EmisorService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
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
	private ComprobanteService comprobanteService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private CertificadoService certificadoService;
	
	@Autowired
	private EmisorService emisorService;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
	@Value("${hora.inicio}")
	private int horaInicio;
	
	@Value("${hora.cierre}")
	private int horaCierre;
	
	@Value("${generic.rfc.ventas.mostrador}")
	private String rfcVentasMostrador;
	
	@Override
	public void generarDocumento(Documento documento) {
		logger.debug("cfdiService...");
		
		int idServicio = documentoWebService.obtenerIdServicio();
		CertificadoDigital certificado = certificadoService.readVigente(documento.getComprobante());
		Calendar fechaFacturacion = Calendar.getInstance();
		documento.setFechaFacturacion(fechaFacturacion.getTime());
		documentoService.guardarDocumento(documento);
		if(documento.isVentasMostrador()) {
			ticketService.guardarTicketsCierreDia(documento);
		}
		sellarYTimbrarComprobante(documento, idServicio, certificado);
	}

	@Override
	public void sellarYTimbrarComprobante(Documento documento,
			int idServicio, CertificadoDigital certificado) {
		if (comprobanteService.sellarComprobante(documento.getComprobante(), certificado)) {
			if (documentoWebService.timbrarDocumento(documento, idServicio)) {
				documentoService.updateDocumentoXmlCfdi(documento);
				documentoService.insertDocumentoCfdi(documento);
				documentoService.insertDocumentoPendiente(documento, TipoEstadoDocumentoPendiente.ACUSE_PENDIENTE);
				if(documento instanceof DocumentoSucursal) {
//					ticketService.updateEstadoFacturado((DocumentoSucursal) documento);
					if (((DocumentoSucursal) documento).isRequiereNotaCredito()) {
						try {
							generarDocumentoNcr(documento, idServicio);
						} catch(PortalException ex) {
							logger.info("Ocurrio un error al generar la nota de credito {}", documento.getId());
						}
					}
				}
			}
		}
	}
	
	@Override
	public void recuperarTimbreDocumentosPendientes() {
		List<Documento> documentosTimbrePendientes = new ArrayList<Documento>();
		documentosTimbrePendientes = documentoService.obtenerDocumentosTimbrePendientes();		
		
		if(!documentosTimbrePendientes.isEmpty()) {
			logger.debug("iniciando proceso recuperacion de timbre");
			int idServicio = documentoWebService.obtenerIdServicio();
			
			for(Documento documentoPendiente : documentosTimbrePendientes) {
				try {
					documentoPendiente = documentoService.findById(documentoPendiente);
					CertificadoDigital certificado = certificadoService.readVigente(documentoPendiente.getComprobante());
					sellarYTimbrarComprobante(documentoPendiente, idServicio, certificado);
					logger.debug("Sello y timbre obtenidos correctamente");
					documentoService.deleteDocumentoPendiente(documentoPendiente, TipoEstadoDocumentoPendiente.TIMBRE_PENDIENTE);
				} catch(PortalException ex) {
					logger.info("Ocurrió un error al obtener el timbre pendiente del documento {}, se continua con el proceso."
							, documentoPendiente.getId());
				}
			}
		}
	}
	
	private void generarDocumentoNcr(Documento documento, int idServicio) {
		DocumentoSucursal documentoNcr = new DocumentoSucursal();
		documentoNcr.setTicket(((DocumentoSucursal) documento).getTicket());
		documentoNcr.getTicket().setTipoEstadoTicket(TipoEstadoTicket.NCR_GENERADA);
		
		Cliente cliente = emisorService.readClienteVentasMostrador(documento.getEstablecimiento());
		cliente.setRfc(rfcVentasMostrador);
		int domicilioFiscal = cliente.getDomicilios().get(0).getId();
		Comprobante comprobante = comprobanteService.obtenerComprobantePor(cliente, documentoNcr.getTicket(), 
				domicilioFiscal, documento.getEstablecimiento(), TipoDocumento.NOTA_CREDITO);
		
		documentoNcr.setCliente(cliente);
		documentoNcr.setComprobante(comprobante);
		documentoNcr.setEstablecimiento(documento.getEstablecimiento());
		documentoNcr.setTipoDocumento(TipoDocumento.NOTA_CREDITO);
		documentoNcr.setRequiereNotaCredito(((DocumentoSucursal) documento).isRequiereNotaCredito());
		documentoNcr.setFechaFacturacion(new Date());
		CertificadoDigital certificado = certificadoService.readVigente(documento.getComprobante());
		documentoService.guardarDocumento(documentoNcr);
		if (comprobanteService.sellarComprobante(documentoNcr.getComprobante(), certificado)) {
			if (documentoWebService.timbrarDocumento(documentoNcr, idServicio)) {
				documentoService.insertDocumentoCfdi(documentoNcr);
				documentoService.insertDocumentoPendiente(documentoNcr, TipoEstadoDocumentoPendiente.ACUSE_PENDIENTE);
//				if(documentoNcr instanceof DocumentoSucursal) {
//					ticketService.updateEstadoNcr((DocumentoSucursal) documentoNcr);
//				}
			}
		}
	}

	@Transactional
	@Override
	public void closeOfDay(Establecimiento establecimiento, ListaTickets tickets) {
		List<Ticket> ventas = new ArrayList<Ticket>();
		List<Ticket> ventasDevueltas = new ArrayList<Ticket>();
		List<Ticket> devoluciones = new ArrayList<Ticket>();
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (hora > horaCierre) {
			//FIXME quitar fecha dura 
			String fechaCierre = "07-12-2013";
			List<Documento> documentosAProcesar = new ArrayList<Documento>(); 
			
			establecimiento = establecimientoService.readByClave(establecimiento);
			
			ticketService.closeOfDay(establecimiento, 
					FechasUtils.specificStringFormatDate(fechaCierre, FechasUtils.formatddMMyyyyHyphen, 
					FechasUtils.formatyyyyMMdd), 
					ventas, devoluciones);
			
			//FIXME Logica para web service
//			ventas = tickets.getVentas();
//			devoluciones = tickets.getDevoluciones();
			
			logger.debug("devoluciones {}", devoluciones.size());
			
			logger.debug("Antes " + ventas.size());
			
			List<Documento> listaNotasDeCredito = prepararDocumentosNcr(devoluciones, establecimiento);
			
			filtraDevolucionesVentas(ventas, devoluciones, ventasDevueltas);
			
			logger.debug("Despues " + ventas.size());
			logger.debug("ventasDevueltas " + ventasDevueltas.size());
			
			Ticket ticketVentasMostrador = ticketService.crearTicketVentasMostrador(ventas, establecimiento);
			Cliente cliente = emisorService.readClienteVentasMostrador(establecimiento);
			cliente.setRfc(rfcVentasMostrador);
			int domicilioFiscal = cliente.getDomicilios().get(0).getId();
			Comprobante comprobante = comprobanteService.obtenerComprobantePor(cliente, ticketVentasMostrador, domicilioFiscal, establecimiento, TipoDocumento.FACTURA);
			Documento documentoVentasMostardor = new Documento();
			documentoVentasMostardor.setEstablecimiento(establecimiento);
			documentoVentasMostardor.setCliente(cliente);
			documentoVentasMostardor.setComprobante(comprobante);
			documentoVentasMostardor.setTipoDocumento(TipoDocumento.FACTURA);
			documentoVentasMostardor.setVentas(ventas);
			documentoVentasMostardor.setVentasMostrador(true);
			
			documentosAProcesar.add(documentoVentasMostardor);
			documentosAProcesar.addAll(listaNotasDeCredito);
			
			for(Documento documento : documentosAProcesar) {
				generarDocumento(documento);
			}
			//FIXME desarrollar-cambiar fecha cierre
		} else {
			logger.error("El cierre del dia actual es posible realizarlo hasta despues del cierre de la tienda");
			throw new PortalException("El cierre del dia actual es posible realizarlo hasta despues del cierre de la tienda");
		}
	}
	
	private List<Documento> prepararDocumentosNcr(List<Ticket> devoluciones, Establecimiento establecimiento) {
		List<Documento> listaNotasDeCredito = new ArrayList<Documento>();
		Set<String> archivosOrigenDevolucion = new HashSet<String>();
		
		for(Ticket devolucion : devoluciones) {
			archivosOrigenDevolucion.add(devolucion.getTransaccion().
					getPartidasDevolucion().get(0).getTicketFileOrigen());
		}
		logger.debug(archivosOrigenDevolucion.toString());
		
		for (Iterator<String> it = archivosOrigenDevolucion.iterator(); it.hasNext(); ){
			String archivoOrigen = it.next();
			Documento documentoNcr = documentoService.findByEstadoTicket(archivoOrigen, establecimiento, devoluciones);
			
			if (documentoNcr != null) {
				listaNotasDeCredito.add(documentoNcr);
			}
		}
		
		return listaNotasDeCredito;
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
