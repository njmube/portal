package com.magnabyte.cfdi.portal.service.documento.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import jcifs.smb.NtlmPasswordAuthentication;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.documento.DocumentoDao;
import com.magnabyte.cfdi.portal.dao.documento.DocumentoSerieDao;
import com.magnabyte.cfdi.portal.dao.documento.sql.DocumentoSql;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.factory.EstablecimientoFactory;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;
import com.magnabyte.cfdi.portal.service.codigoqr.CodigoQRService;
import com.magnabyte.cfdi.portal.service.commons.EmailService;
import com.magnabyte.cfdi.portal.service.documento.ComprobanteService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoDetalleService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.service.emisor.EmisorService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.util.NumerosALetras;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 * 
 * Clase que representa el servicio de documento
 */
@Service("documentoService")
public class DocumentoServiceImpl implements DocumentoService, ResourceLoaderAware {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoServiceImpl.class);
	
	@Autowired
	private CodigoQRService codigoQRService;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@Autowired
	private DocumentoDao documentoDao;
	
	@Autowired
	private DocumentoSerieDao documentoSerieDao;
	
	@Autowired
	private ComprobanteService comprobanteService;
	
	@Autowired
	private DocumentoDetalleService documentoDetalleService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
	@Autowired
	private SambaService sambaService;
	
	@Autowired
	private DomicilioClienteService domicilioClienteService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EmisorService emisorService;
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ServletContext context;
	
	@Autowired
	private MessageSource messageSource;
	
	private ResourceLoader resourceLoader;
	
	@Value("${email.subject}")
	private String subject;
	
	@Value("${email.plantilla.plaintext}")
	private String namePlainText;
	
	@Value("${email.plantilla.htmltext}")
	private String nameHtmlText;
	
	@Value("${email.plantilla.htmltexterror}")
	private String nameHtmlTextError;
	
	@Value("${email.plantilla.plaintexterror}")
	private String namePlainTextError;
	
	@Transactional
	@Override
	public void insertDocumentoCfdi(Documento documento) {
		documentoDao.insertDocumentoCfdi(documento);
	}
	
	@Transactional
	@Override
	public void guardarDocumento(Documento documento) {
		if(documento != null) {
			//FIXME Validar para corporativo
			comprobanteService.createFechaDocumento(documento.getComprobante());
			documento.setFechaFacturacion(documento.getComprobante().getFecha().toGregorianCalendar().getTime());

			documento.setXmlCfdi(documentoXmlService
					.convierteComprobanteAByteArray(documento.getComprobante(), PortalUtils.encodingUTF16));
			if(documento instanceof DocumentoSucursal) {
				Ticket ticketDB = null;
				if(((DocumentoSucursal) documento).getTicket().getTipoEstadoTicket() != null 
						&& ((DocumentoSucursal) documento).getTicket().getTipoEstadoTicket().equals(TipoEstadoTicket.NCR_GENERADA)) {
					ticketDB = null;
				} else if (((DocumentoSucursal) documento).getTicket().getTipoEstadoTicket() != null 
						&& ((DocumentoSucursal) documento).getTicket().getTipoEstadoTicket().equals(TipoEstadoTicket.REFACTURADO)) {
					ticketDB = ((DocumentoSucursal) documento).getTicket();
				} else if (((DocumentoSucursal) documento).getTicket().getTipoEstadoTicket() != null 
						&& ((DocumentoSucursal) documento).getTicket().getTipoEstadoTicket().equals(TipoEstadoTicket.POR_REFACTURAR)) {
					((DocumentoSucursal) documento).getTicket().setTipoEstadoTicket(TipoEstadoTicket.FACTURADO);
					ticketDB = null;
				} else {
					ticketDB = ticketService.read(((DocumentoSucursal) documento).getTicket(), documento.getEstablecimiento());
				}
				if (ticketDB != null) {
					switch (ticketDB.getTipoEstadoTicket()) {
//					case GUARDADO:
//						logger.debug("El ticket ya fue guardado previamente.");
//						((DocumentoSucursal) documento).getTicket().setId(ticketDB.getId());
//						documento.setId(ticketService.readIdDocFromTicketGuardado((DocumentoSucursal) documento));
//						documentoDao.updateDocumentoCliente((DocumentoSucursal) documento);
//						Map<String, Object> serieFolioMap = documentoSerieDao.readSerieAndFolioDocumento(documento);
//						documento.getComprobante().setSerie((String) serieFolioMap.get(DocumentoSql.SERIE));
//						documento.getComprobante().setFolio((String) serieFolioMap.get(DocumentoSql.FOLIO));
//						break;
					case REFACTURADO:
						ticketService.updateEstadoRefacturado((DocumentoSucursal) documento);
						saveDocumentAndDetail(documento);
						((DocumentoSucursal) documento).getTicket().setTipoEstadoTicket(TipoEstadoTicket.NCR_GENERADA);
						ticketService.save((DocumentoSucursal) documento);
						asignarSerieYFolio(documento);
						break;
					case FACTURADO_MOSTRADOR:
						logger.debug("El ticket ya fue facturado por ventas mostrador.");
						saveDocumentAndDetail(documento);
						ticketService.save((DocumentoSucursal) documento);
						asignarSerieYFolio(documento);
						((DocumentoSucursal) documento).setRequiereNotaCredito(true);
						break;
					case FACTURADO:
						documento.setId(ticketService.readIdDocFromTicketFacturado((DocumentoSucursal) documento));
						Map<String, Object> serieFolioMap = documentoSerieDao.readSerieAndFolioDocumento(documento);
						documento.getComprobante().setSerie((String) serieFolioMap.get(DocumentoSql.SERIE));
						documento.getComprobante().setFolio((String) serieFolioMap.get(DocumentoSql.FOLIO));
						Object[] exArgs = { 
								documento.getTipoDocumento().getNombre(), 
								documento.getComprobante().getSerie(),
								documento.getComprobante().getFolio()
								};
						logger.debug(messageSource.getMessage("documento.ticket.facturado", exArgs, null));
						throw new PortalException(messageSource.getMessage("documento.ticket.facturado", exArgs, null));
					default:
						break;
					}
				} else {
					saveDocumentAndDetail(documento);
					ticketService.save((DocumentoSucursal) documento);
					asignarSerieYFolio(documento);
				}
			} else if (documento instanceof DocumentoCorporativo) {
				Documento documentoDB = documentoDao.readDocumentoFolio(documento);
				if (documentoDB != null) {
					Object[] exArgs = { 
							documento.getTipoDocumento().getNombre(), 
							documento.getComprobante().getSerie(),
							documento.getComprobante().getFolio()
							};
					logger.debug(messageSource.getMessage("documento.corporativo.facturado", exArgs, null));
					throw new PortalException(messageSource.getMessage("documento.corporativo.facturado", exArgs, null));
				} else {
					saveDocumentAndDetail(documento);
					documentoDao.insertDocumentoFolio(documento);
				}
			} else {
				saveDocumentAndDetail(documento);
				asignarSerieYFolio(documento);
				if (documento.isVentasMostrador()) {
					ticketService.guardarTicketsCierreDia(documento);
				}
			}
		} else {
			logger.debug(messageSource.getMessage("documento.nulo", null, null));
			throw new PortalException(messageSource.getMessage("documento.nulo", null, null));
		}		
		
	}
	
	@Override
	public void guardarDocumentoRefacturado(Documento documento) {
		guardarDocumento(documento);
		documento.getDocumentoOrigen().setTipoEstadoDocumento(TipoEstadoDocumento.REPROCESADO);
		updateDocumentoStatus(documento.getDocumentoOrigen());
		guardarDocumento(documento.getDocumentoOrigen());		
	}
	
	@Transactional
	@Override
	public void updateDocumentoStatus(Documento documento) {
		documentoDao.updateDocumentoStatus(documento);
	}
	
	@Transactional
	@Override
	public void updateDocumentoStatusAndXml(Documento documento) {
		documento.setTipoEstadoDocumento(TipoEstadoDocumento.PROCESADO);
		documentoDao.updateDocumentoStatus(documento);
		documentoDao.updateDocumentoXmlCfdi(documento);
	}

	private void asignarSerieYFolio(Documento documento) {
		synchronized (documentoSerieDao) {
			Map<String, Object> serieFolioMap = documentoSerieDao.readNextSerieAndFolio(documento);
			documento.getComprobante().setSerie((String) serieFolioMap.get(DocumentoSql.SERIE));
			documento.getComprobante().setFolio((String) serieFolioMap.get(DocumentoSql.FOLIO_CONSECUTIVO));
			documentoSerieDao.updateFolioSerie(documento);
			documentoDao.insertDocumentoFolio(documento);
		}
	}

	private void saveDocumentAndDetail(Documento documento) {
		documento.setTipoEstadoDocumento(TipoEstadoDocumento.PENDIENTE);
		documentoDao.save(documento);
		documentoDetalleService.save(documento);		
	}

	@Transactional
	@Override
	public void insertDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumento) {
		switch (estadoDocumento) {
		case ACUSE_PENDIENTE:
			documentoDao.insertDocumentoPendiente(documento, estadoDocumento);
			break;
		case TIMBRE_PENDIENTE:
			if (!isTimbrePendiente(documento)) {
				documentoDao.insertDocumentoPendiente(documento, estadoDocumento);
			} else {
				logger.info("El documento ya encuentra en la lista de pendientes por timbrar.");
			}
			break;
		default:
			break;
		}
	}
	
	private boolean isTimbrePendiente(Documento documento) {
		Documento documentoDB = documentoDao.readDocumentoPendiente(documento, TipoEstadoDocumentoPendiente.TIMBRE_PENDIENTE); 
		return documentoDB != null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Documento> obtenerAcusesPendientes() {
		return documentoDao.obtenerAcusesPendientes();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Documento> getDocumentos(Cliente cliente, String fechaInicial, String fechaFinal) {
		List<Documento> listaDocumentos = documentoDao
				.getDocumentoByCliente(cliente, fechaInicial, fechaFinal);
		List<Integer> idDocumentos = new ArrayList<Integer>();
		List<Documento> documentosPorId = null;
		
		if(listaDocumentos != null && !listaDocumentos.isEmpty()) {			
			for(Documento ruta : listaDocumentos) {
				idDocumentos.add(ruta.getId());
			}
		
			documentosPorId = documentoDao.getNombreDocumentoFacturado(idDocumentos);
			
			for (Documento documento2 : documentosPorId) {
				for (Documento documento : listaDocumentos) {
					if (documento2.getId().equals(documento.getId())) {
						documento2.setEstablecimiento(documento.getEstablecimiento());
						documento2.setNombre(documento2.getTipoDocumento()
								+ "_" + documento2.getComprobante().getSerie() 
								+ "_" + documento2.getComprobante().getFolio());
						break;
					}
				}
			}
		}
		
		return documentosPorId;
	}
	
	@Transactional(readOnly = true)
	@Override
	public byte[] recuperarDocumentoArchivo(String fileName, 
			Integer idEstablecimiento, String extension) {
		try {
			Establecimiento establecimiento = establecimientoService.readRutaById(
					EstablecimientoFactory.newInstance(idEstablecimiento));
			NtlmPasswordAuthentication authentication = sambaService.getAuthentication(establecimiento);
			InputStream file = sambaService.getFileStream(establecimiento.getRutaRepositorio().getRutaRepositorio() 
					+ establecimiento.getRutaRepositorio().getRutaRepoOut(), 
					fileName + "." + extension, 
					authentication);
			
			return IOUtils.toByteArray(file);
		
		} catch (Exception e) {
			logger.error(messageSource.getMessage("documento.conversion.error", null, null));
			throw new PortalException(messageSource.getMessage("documento.conversion.error", null, null));
		}
	}
	
	@Override
	public byte[] recuperarDocumentoXml(Documento documento) {
//		try {
//			logger.debug(new String(documento.getXmlCfdi(), PortalUtils.encodingUTF16));
			Comprobante comprobante = documentoXmlService.convierteByteArrayAComprobante(documento.getXmlCfdi());
			return documentoXmlService.convierteComprobanteAByteArray(comprobante, PortalUtils.encodingUTF8);
//		} catch (UnsupportedEncodingException ex) {
//			logger.error("Error al convertir el documento a bytes");
//			throw new PortalException("Error al convertir el documento a bytes");
//		}
	}
	
	@Override
	public Map<String, Object> populateReportParams(Documento documento) {
		Map<String, Object> model = new HashMap<String, Object>();
		Locale locale = new Locale("es", "MX");
		
		String pathImages = context.getRealPath("resources/img");
		if (documento instanceof DocumentoCorporativo) {
			model.put("FOLIO_SAP", ((DocumentoCorporativo) documento).getFolioSap());
			model.put("NIT", ((DocumentoCorporativo) documento).getNit());
			if (documentoXmlService.hasLeyendasFiscales(documento.getComprobante())) {
				model.put("LEYFISC", documentoXmlService.obtenerLeyendasFiscales(documento.getComprobante()));
			}
		} else if (documento instanceof DocumentoSucursal) {
			//FIXME revisar el id de establecimiento
			model.put("SUCURSAL", documento.getEstablecimiento().getNombre());
			model.put("CAJA", ((DocumentoSucursal) documento).getTicket().getTransaccion().getTransaccionHeader().getIdCaja());
			model.put("TICKET", ((DocumentoSucursal) documento).getTicket().getTransaccion().getTransaccionHeader().getIdTicket());
			model.put("FECHATICKET", ((DocumentoSucursal) documento).getTicket().getTransaccion().getTransaccionHeader().getFecha());
		}
		if (documento.getDocumentoOrigen() != null) { 
			Map<String, Object> serieFolioMap = documentoSerieDao.readSerieAndFolioDocumento(documento.getDocumentoOrigen());
			StringBuilder sb = new StringBuilder();
			sb.append(serieFolioMap.get(DocumentoSql.SERIE)).append("-").append(serieFolioMap.get(DocumentoSql.FOLIO));
			model.put("DOC_ORIGEN", sb.toString());
		}
		model.put("TIPO_DOC", documento.getTipoDocumento().getNombre());
		model.put("NUM_SERIE_CERT", documentoXmlService.obtenerNumCertificado(documento.getXmlCfdi()));
		model.put("SELLO_CFD", documento.getTimbreFiscalDigital().getSelloCFD());
		model.put("SELLO_SAT", documento.getTimbreFiscalDigital().getSelloSAT());
		model.put("FECHA_TIMBRADO", documento.getTimbreFiscalDigital().getFechaTimbrado());
		model.put("FOLIO_FISCAL", documento.getTimbreFiscalDigital().getUUID());
		model.put("CADENA_ORIGINAL", documento.getCadenaOriginal());
		model.put("PATH_IMAGES", pathImages);
		model.put(JRParameter.REPORT_LOCALE, locale);
		model.put("QRCODE", codigoQRService.generaCodigoQR(documento));
		model.put("LETRAS", NumerosALetras.convertNumberToLetter(documento.getComprobante().getTotal().toString()));
		model.put("REGIMEN", documento.getComprobante().getEmisor().getRegimenFiscal().get(0).getRegimen());
		if (documento.getComprobante().getImpuestos().getTraslados() != null) {
			model.put("IVA", documento.getComprobante().getImpuestos().getTraslados().getTraslado().get(0).getTasa());
		} else {
			model.put("IVA", BigDecimal.ZERO);
		}
		
		return model;
	}
	
	@Override
	public byte[] recuperarDocumentoPdf(Documento documento) {
		logger.debug("Creando reporte");
		JasperPrint reporteCompleto = null;
		byte[] bytesReport = null;
		String reporteCompilado = context.getRealPath("WEB-INF/reports/ReporteFactura.jasper");

		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
		comprobantes.add(documento.getComprobante());
		Map<String, Object> map = populateReportParams(documento);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(comprobantes);

		try {
			reporteCompleto = JasperFillManager.fillReport(reporteCompilado, map, dataSource);
			bytesReport = JasperExportManager.exportReportToPdf(reporteCompleto);
			return bytesReport;
		} catch (JRException e) {
			logger.error(messageSource.getMessage("documento.error.pdf", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.error.pdf", new Object[] {e.getMessage()}, null));
		}
	}

	@Override
	public void envioDocumentosFacturacion(final String para, final String fileName,
		final Integer idEstablecimiento) {
	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String asunto = subject + fileName;
				String htmlPlantilla = null;
				String textoPlanoPlantilla = null;
				String htmlPlantillaError = null;
				String textoPlanoPlantillaError = null;
				
				Resource htmlResource = resourceLoader.getResource("classpath:/" + nameHtmlText);
				Resource plainTextResource = resourceLoader.getResource("classpath:/" + namePlainText);
				Resource htmlResourceError = resourceLoader.getResource("classpath:/" + nameHtmlTextError);
				Resource plainTextResourceError = resourceLoader.getResource("classpath:/" + namePlainTextError);
				
				try {
					htmlPlantillaError = IOUtils.toString(htmlResourceError.getInputStream(), PortalUtils.encodingUTF8);
					textoPlanoPlantillaError = IOUtils.toString(plainTextResourceError.getInputStream(), PortalUtils.encodingUTF8);
					
					Establecimiento establecimiento = establecimientoService.readRutaById(
							EstablecimientoFactory.newInstance(idEstablecimiento));
					NtlmPasswordAuthentication authentication = sambaService.getAuthentication(establecimiento);

					InputStream pdf = sambaService.getFileStream(establecimiento.getRutaRepositorio().getRutaRepositorio() 
							+ establecimiento.getRutaRepositorio().getRutaRepoOut(), fileName + ".pdf", authentication);
					
					InputStream xml = sambaService.getFileStream(establecimiento.getRutaRepositorio().getRutaRepositorio() 
							+ establecimiento.getRutaRepositorio().getRutaRepoOut(), fileName + ".xml", authentication);
					
					final Map<String, ByteArrayResource> attach = new HashMap<String, ByteArrayResource>();
					attach.put(fileName + ".pdf", new ByteArrayResource(IOUtils.toByteArray(pdf)));
					attach.put(fileName + ".xml", new ByteArrayResource(IOUtils.toByteArray(xml)));
					
					htmlPlantilla = IOUtils.toString(htmlResource.getInputStream(), PortalUtils.encodingUTF8);
					textoPlanoPlantilla = IOUtils.toString(plainTextResource.getInputStream(), PortalUtils.encodingUTF8);

					emailService.sendMailWithAttach(textoPlanoPlantilla,htmlPlantilla, asunto, attach, para);
					
				} catch (PortalException ex) {
					logger.error("Error al leer los archivos adjuntos.", ex);
					emailService.sendMimeMail(textoPlanoPlantillaError, htmlPlantillaError ,asunto, para);
				} catch (IOException ex) {
					logger.error("Error al leer los archivos adjuntos.", ex);
					emailService.sendMimeMail(textoPlanoPlantillaError, htmlPlantillaError ,asunto, para);
				}
			}
		}).start();
	}
	
	@Override
	public void envioDocumentosFacturacionPorXml(String para, String fileName,
		Integer idDocumento) {
		Documento documento = new Documento();
		documento.setId(idDocumento);
		documento = findById(documento);
		if (documento instanceof DocumentoSucursal ) {
			((DocumentoSucursal) documento).setTicket(ticketService.findByDocumento(documento));
		}
		String asunto = subject + fileName;
		String htmlPlantilla = null;
		String textoPlanoPlantilla = null;
		String htmlPlantillaError = null;
		String textoPlanoPlantillaError = null;
		
		Resource htmlResource = resourceLoader.getResource("classpath:/" + nameHtmlText);
		Resource plainTextResource = resourceLoader.getResource("classpath:/" + namePlainText);
		Resource htmlResourceError = resourceLoader.getResource("classpath:/" + nameHtmlTextError);
		Resource plainTextResourceError = resourceLoader.getResource("classpath:/" + namePlainTextError);
		
		try {
			htmlPlantillaError = IOUtils.toString(htmlResourceError.getInputStream(), PortalUtils.encodingUTF8);
			textoPlanoPlantillaError = IOUtils.toString(plainTextResourceError.getInputStream(), PortalUtils.encodingUTF8);
			
			final Map<String, ByteArrayResource> attach = new HashMap<String, ByteArrayResource>();
			attach.put(fileName + ".pdf", new ByteArrayResource(recuperarDocumentoPdf(documento)));
			attach.put(fileName + ".xml", new ByteArrayResource(recuperarDocumentoXml(documento)));
			
			htmlPlantilla = IOUtils.toString(htmlResource.getInputStream(), PortalUtils.encodingUTF8);
			textoPlanoPlantilla = IOUtils.toString(plainTextResource.getInputStream(), PortalUtils.encodingUTF8);
			emailService.sendMailWithAttach(textoPlanoPlantilla,htmlPlantilla, asunto, attach, para);
			
		} catch (PortalException ex) {
			logger.error("Error al leer los archivos adjuntos.", ex);
			emailService.sendMimeMail(textoPlanoPlantillaError, htmlPlantillaError ,asunto, para);
		} catch (IOException ex) {
			logger.error("Error al leer los archivos adjuntos.", ex);
			emailService.sendMimeMail(textoPlanoPlantillaError, htmlPlantillaError ,asunto, para);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Documento> obtenerDocumentosTimbrePendientes() {
		return documentoDao.obtenerDocumentosTimbrePendientes();
	}

	@Transactional(readOnly = true)
	@Override
	public Documento read(Documento documento) {
		return documentoDao.read(documento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Documento findById(Documento documento) {
		Documento documentoBD = null;
		Documento documentoBDParaTipo = null;
		Documento documentoBDParaTimbre = null;
		Establecimiento establecimientoBD = null;
		Comprobante comprobante = null;
//		Ticket ticketBD = null;
		if(documento.getId() != null) {
			documentoBD = documentoDao.read(documento);
//			if (documentoBD instanceof DocumentoSucursal) {
//				ticketBD = ticketService.readByDocumento(documento);
//				if (ticketBD != null) {
//					((DocumentoSucursal) documentoBD).setTicket(ticketBD);
//				}
//			}
			establecimientoBD = establecimientoService.read(documentoBD.getEstablecimiento());
			comprobante = documentoXmlService.convierteByteArrayAComprobante(documentoBD.getXmlCfdi());
			documentoBD.setComprobante(comprobante);
			documentoBDParaTipo = documentoDao.readDocumentoFolioById(documentoBD);
			if (documentoBDParaTipo != null) {
				documentoBD.getComprobante().setSerie(documentoBDParaTipo.getComprobante().getSerie());
				documentoBD.getComprobante().setFolio(documentoBDParaTipo.getComprobante().getFolio());
				documentoBD.setTipoDocumento(documentoBDParaTipo.getTipoDocumento());
			}
			documentoBD.setEstablecimiento(establecimientoBD);
			documentoBDParaTimbre = documentoDao.readDocumentoCfdiById(documento);
			if (documentoBDParaTimbre != null) {
				documentoBD.setTimbreFiscalDigital(documentoBDParaTimbre.getTimbreFiscalDigital());
				documentoBD.setCadenaOriginal(documentoBDParaTimbre.getCadenaOriginal());
			}
		}
		return documentoBD;
	}
	
	@Override
	public Documento findByEstadoTicket(String archivoOrigen, 
			Establecimiento establecimiento, List<Ticket> devoluciones) {
		//FIXME Revisar recuperacion del ticket
		DocumentoSucursal documentoTicketOrigen = ticketService.readDocFromTicket(archivoOrigen);
		if (documentoTicketOrigen != null) {
			switch (documentoTicketOrigen.getTicket().getTipoEstadoTicket()) {
			case FACTURADO:
				return obtenerDocumentoNcr(devoluciones, documentoTicketOrigen, establecimiento);
			case FACTURADO_MOSTRADOR:
				return obtenerDocumentoNcr(devoluciones, documentoTicketOrigen, establecimiento);
			default:
				return null;
			}
		} else {
			return null;
		}
	}
	
	private Documento obtenerDocumentoNcr(List<Ticket> devoluciones, 
			DocumentoSucursal documentoOrigen, Establecimiento establecimiento) {
		Ticket ticketDevuelto = ticketService.crearTicketDevolucion(documentoOrigen, devoluciones, establecimiento);
		Cliente cliente = readClienteFromDocumento(documentoOrigen);
		if (cliente == null) {
			cliente = emisorService.readClienteVentasMostrador(establecimiento);
		}
		int domicilioFiscal = cliente.getDomicilios().get(0).getId();
		Comprobante comprobante = comprobanteService.obtenerComprobantePor(cliente, ticketDevuelto, domicilioFiscal, establecimiento, TipoDocumento.NOTA_CREDITO);
		Documento documentoNcr = new DocumentoSucursal();
		documentoNcr.setEstablecimiento(establecimiento);
		documentoNcr.setCliente(cliente);
		((DocumentoSucursal) documentoNcr).setTicket(ticketDevuelto);
		documentoNcr.setComprobante(comprobante);
		documentoNcr.setTipoDocumento(TipoDocumento.NOTA_CREDITO);
		return documentoNcr;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Cliente readClienteFromDocumento(Documento documento) {
		Cliente cliente = documentoDao.readClienteFromDocumento(documento);
		
		if (cliente != null) {
			List<DomicilioCliente> domicilios = new ArrayList<DomicilioCliente>();
			DomicilioCliente domicilioCliente = domicilioClienteService
					.readById(cliente.getDomicilios().get(0));
			domicilios.add(domicilioCliente);
			cliente.setDomicilios(domicilios);
		}
		
		return cliente;
	}

	@Transactional
	@Override
	public void deleteDocumentoPendiente(Documento documento, 
			TipoEstadoDocumentoPendiente estadoDocumentoPendiente) {
		documentoDao.deletedDocumentoPendiente(documento, estadoDocumentoPendiente);
	}
	
	@Transactional
	@Override
	public void saveAcuseCfdiXmlFile(Documento documento) {
		documentoDao.saveAcuseCfdiXmlFile(documento);
	}
	
	public boolean isArticuloSinPrecio(String claveArticulo) {
		List<String> articulosSinPrecio = ticketService.readArticulosSinPrecio();
		if (articulosSinPrecio != null) {
			return articulosSinPrecio.contains(claveArticulo);
		}
		return false;
	}
	
	//FIXME Terminar
	@Transactional(readOnly = true)
	@Override
	public void findBySerieFolioImporte(Documento documento, Establecimiento establecimiento) {
		
		documento = documentoDao.findBySerie(documento);
		if (!documento.getTipoDocumento().equals(TipoDocumento.FACTURA)) {
			throw new PortalException(messageSource.getMessage("documento.error.tipo.refacturacion", null, null));
		}
		
		if (documento.getEstablecimiento().getTipoEstablecimiento().getRol().equalsIgnoreCase("ROLE_CORP")) {
			throw new PortalException(messageSource.getMessage("documento.error.sucursal.refacturacion", null, null));
		}
		documento = documentoDao.findBySerieFolioImporte(documento);
		switch (documento.getTipoEstadoDocumento()) {
		case REPROCESADO:
			logger.error(messageSource.getMessage("documento.error.refacturado", null, null));
			throw new PortalException(messageSource.getMessage("documento.error.refacturado", null, null));				
		default:
			break;
		}		
		documento.setCliente(clienteService.read(documento.getCliente()));
		documento.setComprobante(documentoXmlService.convierteByteArrayAComprobante(documento.getXmlCfdi()));
		if (documento instanceof DocumentoSucursal) {
			((DocumentoSucursal) documento).setTicket(ticketService.findByDocumento(documento));
		}
		try {
			documento.setDocumentoOrigen((Documento) documento.clone());
		} catch (CloneNotSupportedException e) {
			logger.error(messageSource.getMessage("documento.error.origen", null, null));
			throw new PortalException(messageSource.getMessage("documento.error.origen", null, null));
		}
		documento.setEstablecimiento(establecimiento);
		procesarDocumentoNuevo(documento);
		procesarDocumentoOrigen(documento.getDocumentoOrigen());
	}
	
	private void procesarDocumentoNuevo(Documento documento) {
		documento.setId(null);
		documento.setComprobante(documentoXmlService.convierteByteArrayAComprobante(documento.getXmlCfdi()));
		if (documento instanceof DocumentoSucursal) {
			((DocumentoSucursal) documento).getTicket().setTipoEstadoTicket(TipoEstadoTicket.POR_REFACTURAR);
		}
		limpiarComprobante(documento);
	}

	private void procesarDocumentoOrigen(Documento documentoOrigen) {
		documentoOrigen.setTipoDocumento(TipoDocumento.NOTA_CREDITO);
		if (documentoOrigen instanceof DocumentoSucursal) {
			((DocumentoSucursal) documentoOrigen).getTicket().setTipoEstadoTicket(TipoEstadoTicket.REFACTURADO);
		}
		limpiarComprobante(documentoOrigen);
	}
	
	private void limpiarComprobante(Documento documento) {
		documento.getComprobante().setNoCertificado(null);
		documento.getComprobante().setCertificado(null);
		documento.getComprobante().setFecha(null);
		documento.getComprobante().setSello(null);
		documento.getComprobante().setComplemento(null);
		documento.getComprobante().setTipoDeComprobante(documento.getTipoDocumento().getNombreComprobante());
	}
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
