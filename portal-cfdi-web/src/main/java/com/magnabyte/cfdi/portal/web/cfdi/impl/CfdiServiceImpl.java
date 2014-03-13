package com.magnabyte.cfdi.portal.web.cfdi.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.ListaTickets;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.certificado.CertificadoService;
import com.magnabyte.cfdi.portal.service.cfdi.v32.CfdiV32Service;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.documento.ComprobanteService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.service.emisor.EmisorService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.web.cfdi.CfdiService;
import com.magnabyte.cfdi.portal.web.webservice.DocumentoWebService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el servicio de cfdi
 */
@Service("cfdiService")
public class CfdiServiceImpl implements CfdiService {

	private static final Logger logger = LoggerFactory
			.getLogger(CfdiServiceImpl.class);
	
	private RestTemplate restTemplate;

	@Autowired
	private DocumentoWebService documentoWebService;

	@Autowired
	private DocumentoService documentoService;

	@Autowired
	private ComprobanteService comprobanteService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CfdiV32Service cfdiV32Service;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private CertificadoService certificadoService;

	@Autowired
	private SambaService sambaService;

	@Autowired
	private EmisorService emisorService;

	@Autowired
	private DocumentoXmlService documentoXmlService;

	@Autowired
	private EstablecimientoService establecimientoService;
	
	@Autowired
	private MessageSource messageSource;

	@Value("${hora.inicio}")
	private int horaInicio;

	@Value("${hora.cierre}")
	private int horaCierre;

	@Value("${generic.rfc.ventas.mostrador}")
	private String rfcVentasMostrador;

	@Async
	@Override
	public void generarDocumento(Documento documento) {
		logger.debug("cfdiService...");

		int idServicio = documentoWebService.obtenerIdServicio();
		CertificadoDigital certificado = certificadoService
				.readVigente(documento.getComprobante());
		sellarYTimbrarComprobante(documento, idServicio, certificado);
		if (documento instanceof DocumentoCorporativo) {
			sambaService.moveProcessedSapFile((DocumentoCorporativo) documento);
			sambaService.writeProcessedCfdiXmlFile(documentoXmlService
					.convierteComprobanteAByteArray(documento.getComprobante(),
							PortalUtils.encodingUTF8), documento);
		}
		envioMailCliente(documento);
	}

	private void envioMailCliente(Documento documento) {
		if (documento.getTipoDocumento().equals(TipoDocumento.FACTURA) &&
				(documento.getCliente().getEmail() != null && !documento.getCliente().getEmail().isEmpty())) {
			logger.debug("Se enviara el email con los archivos del documento");
			String fileName = documento.getTipoDocumento().getNombre() 
					+ "_" + documento.getComprobante().getSerie() 
					+ "_" + documento.getComprobante().getFolio();
			envioDocumentosFacturacion(documento.getCliente().getEmail(), fileName, documento.getId());
		}
	}
	
	@Async
	@Override
	public void procesarDocumentoRefacturado(Documento documento) {
		Documento facturaDocumentoNuevo = documento;
		Documento notaCreditoDocumentoOrigen = documento.getDocumentoOrigen();
		
		//FIXME Quitar para produccion
		facturaDocumentoNuevo.getComprobante().getEmisor().setRfc("AAA010101AAA");
		notaCreditoDocumentoOrigen.getComprobante().getEmisor().setRfc("AAA010101AAA");
		
		try {
			generarDocumento(facturaDocumentoNuevo);
		} catch (PortalException ex) {
			logger.info(messageSource.getMessage("cfdi.error.factura.generar", new Object[] {documento.getId()}, null));
		}
		try {
			generarDocumento(notaCreditoDocumentoOrigen);
		} catch (PortalException ex) {
			logger.info(messageSource.getMessage("cfdi.error.ncr.generar", new Object[] {documento.getId()}, null));
		}
	}
	
	@Override
	public void generarDocumentoCorp(Documento documento) {
		generarDocumento(documento);
	}

	@Override
	public void sellarYTimbrarComprobante(Documento documento, int idServicio,
			CertificadoDigital certificado) {
		if (cfdiV32Service.sellarComprobante(documento.getComprobante(),
				certificado)) {
			if (documentoWebService.timbrarDocumento(documento, idServicio)) {
				documentoService.updateDocumentoStatusAndXml(documento);
				documentoService.insertDocumentoCfdi(documento);
				documentoService.insertDocumentoPendiente(documento,
						TipoEstadoDocumentoPendiente.ACUSE_PENDIENTE);
				if (documento instanceof DocumentoSucursal) {
					// ticketService.updateEstadoFacturado((DocumentoSucursal)
					// documento);
					if (((DocumentoSucursal) documento).isRequiereNotaCredito()) {
						try {
							generarDocumentoNcr(documento, idServicio);
						} catch (PortalException ex) {
							logger.info(messageSource.getMessage("cfdi.error.ncr.generar", new Object[] {documento.getId()}, null));
						}
					}
				}
			}
		}
	}

	@Override
	public void recuperarTimbreDocumentosPendientes() {
		List<Documento> documentosTimbrePendientes = new ArrayList<Documento>();
		documentosTimbrePendientes = documentoService
				.obtenerDocumentosTimbrePendientes();

		if (!documentosTimbrePendientes.isEmpty()) {
			logger.debug("iniciando proceso recuperacion de timbre");
			int idServicio = documentoWebService.obtenerIdServicio();

			for (Documento documentoPendiente : documentosTimbrePendientes) {
				try {
					documentoPendiente = documentoService
							.findById(documentoPendiente);
					CertificadoDigital certificado = certificadoService
							.readVigente(documentoPendiente.getComprobante());
					sellarYTimbrarComprobante(documentoPendiente, idServicio,
							certificado);
					logger.debug("Sello y timbre obtenidos correctamente");
					if (documentoPendiente.getTipoDocumento().equals(TipoDocumento.FACTURA)) {
						documentoPendiente.setCliente(clienteService.read(documentoPendiente.getCliente()));
					}
					envioMailCliente(documentoPendiente);
//					documentoService.deleteDocumentoPendiente(
//							documentoPendiente,
//							TipoEstadoDocumentoPendiente.TIMBRE_PENDIENTE);
				} catch (PortalException ex) {
					logger.info(messageSource.getMessage("cfdi.error.timbre.pendiente", new Object[] {documentoPendiente.getId()}, null));
				}
			}
		}
	}

	private void generarDocumentoNcr(Documento documento, int idServicio) {
		DocumentoSucursal documentoNcr = new DocumentoSucursal();
		documentoNcr.setTicket(((DocumentoSucursal) documento).getTicket());
		documentoNcr.getTicket().setTipoEstadoTicket(
				TipoEstadoTicket.NCR_GENERADA);

		Cliente cliente = emisorService.readClienteVentasMostrador(documento
				.getEstablecimiento());
		cliente.setRfc(rfcVentasMostrador);
		int domicilioFiscal = cliente.getDomicilios().get(0).getId();
		Comprobante comprobante = comprobanteService.obtenerComprobantePor(
				cliente, documentoNcr.getTicket(), domicilioFiscal,
				documento.getEstablecimiento(), TipoDocumento.NOTA_CREDITO);

		documentoNcr.setCliente(cliente);
		documentoNcr.setComprobante(comprobante);
		documentoNcr.setEstablecimiento(documento.getEstablecimiento());
		documentoNcr.setTipoDocumento(TipoDocumento.NOTA_CREDITO);
		documentoNcr.setFechaFacturacion(new Date());
		CertificadoDigital certificado = certificadoService
				.readVigente(documento.getComprobante());
		documentoService.guardarDocumento(documentoNcr);
		if (cfdiV32Service.sellarComprobante(documentoNcr.getComprobante(),
				certificado)) {
			if (documentoWebService.timbrarDocumento(documentoNcr, idServicio)) {
				documentoService.insertDocumentoCfdi(documentoNcr);
				documentoService.insertDocumentoPendiente(documentoNcr,
						TipoEstadoDocumentoPendiente.ACUSE_PENDIENTE);
				// if(documentoNcr instanceof DocumentoSucursal) {
				// ticketService.updateEstadoNcr((DocumentoSucursal)
				// documentoNcr);
				// }
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
			List<Documento> documentosAProcesar = new ArrayList<Documento>();

			establecimiento = establecimientoService
					.readByClave(establecimiento);

			//FIXME logica inicial
			ticketService.closeOfDay(establecimiento, FechasUtils
					.specificStringFormatDate(tickets.getFechaCierre(),
							FechasUtils.formatddMMyyyyHyphen,
							FechasUtils.formatyyyyMMdd), ventas, devoluciones);
			//
			// FIXME Logica para web service
//			 ventas = tickets.getVentas();
			//FIXME Filtrar ventas de facturados
//			 devoluciones = tickets.getDevoluciones();
//
			logger.debug("devoluciones {}", devoluciones.size());

			logger.debug("Antes " + ventas.size());

			List<Documento> listaNotasDeCredito = prepararDocumentosNcr(
					devoluciones, establecimiento);

			filtraDevolucionesVentas(ventas, devoluciones, ventasDevueltas);

			logger.debug("Despues " + ventas.size());
			logger.debug("ventasDevueltas " + ventasDevueltas.size());

			Ticket ticketVentasMostrador = ticketService
					.crearTicketVentasMostrador(ventas, establecimiento);
			Cliente cliente = emisorService
					.readClienteVentasMostrador(establecimiento);
			cliente.setRfc(rfcVentasMostrador);
			int domicilioFiscal = cliente.getDomicilios().get(0).getId();
			Comprobante comprobante = comprobanteService.obtenerComprobantePor(
					cliente, ticketVentasMostrador, domicilioFiscal,
					establecimiento, TipoDocumento.FACTURA);
			Documento documentoVentasMostardor = new Documento();
			documentoVentasMostardor.setEstablecimiento(establecimiento);
			documentoVentasMostardor.setCliente(cliente);
			documentoVentasMostardor.setComprobante(comprobante);
			documentoVentasMostardor.setTipoDocumento(TipoDocumento.FACTURA);
			documentoVentasMostardor.setVentas(ventas);
			documentoVentasMostardor.setVentasMostrador(true);

			documentosAProcesar.add(documentoVentasMostardor);
			//FIXME logica cambiar estados ticket y documento origen
			documentosAProcesar.addAll(listaNotasDeCredito);

			for (Documento documento : documentosAProcesar) {
				documentoService.guardarDocumento(documento);
				generarDocumento(documento);
			}
			 establecimientoService.updateFechaCierre(establecimiento,
			 tickets.getFechaCierre());
			 
		} else {
			logger.error(messageSource.getMessage("cierre.error.hora", null, null));
			throw new PortalException(messageSource.getMessage("cierre.error.hora", null, null));
		}
	}
	
	@Async
	@Override
	public void envioDocumentosFacturacion(String email, String fileName,
			Integer idDocumento) {
		documentoService.envioDocumentosFacturacionPorXml(email, fileName, idDocumento);
	}

	private List<Documento> prepararDocumentosNcr(List<Ticket> devoluciones,
			Establecimiento establecimiento) {
		List<Documento> listaNotasDeCredito = new ArrayList<Documento>();
		Set<String> archivosOrigenDevolucion = new HashSet<String>();

		for (Ticket devolucion : devoluciones) {
			archivosOrigenDevolucion.add(devolucion.getTransaccion()
					.getPartidasDevolucion().get(0).getTicketFileOrigen());
		}
		logger.debug(archivosOrigenDevolucion.toString());

		for (Iterator<String> it = archivosOrigenDevolucion.iterator(); it
				.hasNext();) {
			String archivoOrigen = it.next();
			Documento documentoNcr = documentoService.findByEstadoTicket(
					archivoOrigen, establecimiento, devoluciones);

			if (documentoNcr != null) {
				listaNotasDeCredito.add(documentoNcr);
			}
		}

		return listaNotasDeCredito;
	}

	private void filtraDevolucionesVentas(List<Ticket> ventas,
			List<Ticket> devoluciones, List<Ticket> ventasDevueltas) {

		Set<String> archivosOrigenDevolucion = new HashSet<String>();

		for (Ticket devolucion : devoluciones) {
			archivosOrigenDevolucion.add(devolucion.getTransaccion()
					.getPartidasDevolucion().get(0).getTicketFileOrigen());
		}
		logger.debug(archivosOrigenDevolucion.toString());

		for (String archivoOrigen : archivosOrigenDevolucion) {
			for (int i = 0; i < ventas.size(); i++) {
				if (ventas.get(i).getNombreArchivo().equals(archivoOrigen)) {
					ventasDevueltas.add(ventas.get(i));
					ventas.remove(i);
					break;
				}
			}
		}
	}
	
	@Async
	@Override
	public void recuperaTicketsRest(Establecimiento establecimiento, String fechaCierre) {
		
		restTemplate = new RestTemplate();
		
		String rutaIpLocal = establecimiento.getRutaRepositorio()
				.getRutaRepositorio().replace("smb://", "http://") + ":8080/" ;
		
		Map<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("fechaCierre", fechaCierre);
		parametros.put("claveEstablecimiento", establecimiento.getClave());
		parametros.put("rutaRepoIn", establecimiento.getRutaRepositorio().getRutaRepoIn());
		
		String resp = restTemplate.postForObject(rutaIpLocal + "obtenerTickets", parametros, String.class);
		
		logger.debug(resp);
	}
}
