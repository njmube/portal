package com.magnabyte.cfdi.portal.service.documento.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.factory.EstablecimientoFactory;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
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
	
	@Autowired
	private ServletContext context;
	
	@Transactional
	@Override
	public void insertDocumentoCfdi(Documento documento) {
		documentoDao.insertDocumentoCfdi(documento);
	}
	
	@Transactional
	@Override
	public void guardarDocumento(Documento documento) {
		if(documento != null) {
			Calendar fechaFacturacion = Calendar.getInstance();
			documento.setFechaFacturacion(fechaFacturacion.getTime());
			if (documento.isVentasMostrador()) {
				ticketService.guardarTicketsCierreDia(documento);
			}
			documento.setXmlCfdi(documentoXmlService
					.convierteComprobanteAByteArray(documento.getComprobante(), PortalUtils.encodingUTF16));
			if(documento instanceof DocumentoSucursal) {
				Ticket ticketDB = null;
				if(((DocumentoSucursal) documento).getTicket().getTipoEstadoTicket() != null 
						&& ((DocumentoSucursal) documento).getTicket().getTipoEstadoTicket().equals(TipoEstadoTicket.NCR_GENERADA)) {
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
						StringBuilder builder = new StringBuilder();
						builder.append("El ticket ya fue facturado con anterioridad, puede consultar la ");
						builder.append(documento.getTipoDocumento().getNombre());
						builder.append(" ").append(documento.getComprobante().getSerie());
						builder.append("-").append(documento.getComprobante().getFolio());
						builder.append(" por medio de su RFC.");
						logger.debug(builder.toString());
						throw new PortalException(builder.toString());
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
					StringBuilder builder = new StringBuilder();
					builder.append("La factura ").append(documento.getTipoDocumento().getNombre());
					builder.append(" ").append(documento.getComprobante().getSerie());
					builder.append("-").append(documento.getComprobante().getFolio());
					builder.append(" ya fue procesada con anterioridad.");
					logger.debug(builder.toString());
					throw new PortalException(builder.toString());
				} else {
					saveDocumentAndDetail(documento);
					documentoDao.insertDocumentoFolio(documento);
				}
			} else {
				saveDocumentAndDetail(documento);
				asignarSerieYFolio(documento);
			}
		} else {
			logger.debug("El Documento no puede ser nulo.");
			throw new PortalException("El Documento no puede ser nulo.");
		}		
		
	}
	
	@Transactional
	@Override
	public void updateDocumentoXmlCfdi(Documento documento) {
		documentoDao.updateDocumentoXmlCfdi(documento);
	}

	private void asignarSerieYFolio(Documento documento) {
		synchronized (documentoSerieDao) {
			Map<String, Object> serieFolioMap = documentoSerieDao.readSerieAndFolio(documento);
			documento.getComprobante().setSerie((String) serieFolioMap.get(DocumentoSql.SERIE));
			documento.getComprobante().setFolio((String) serieFolioMap.get(DocumentoSql.FOLIO_CONSECUTIVO));
			documentoSerieDao.updateFolioSerie(documento);
			documentoDao.insertDocumentoFolio(documento);
		}
	}

	private void saveDocumentAndDetail(Documento documento) {
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
				logger.debug("El documento ya encuentra en la lista de pendientes por timbrar.");
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
			logger.error("Error al convertir el documento a bytes");
			throw new PortalException("Error al convertir el documento a bytes");
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
	public byte[] recuperarDocumentoPdf(Documento documento) {
		logger.debug("Creando reporte");
		JasperPrint reporteCompleto = null;
		byte[] bytesReport = null;
		String reporteCompilado = context.getRealPath("WEB-INF/reports/ReporteFactura.jasper");

		Locale locale = new Locale("es", "MX");
		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
		comprobantes.add(documento.getComprobante());
		String pathImages = context.getRealPath("resources/img");
		Map<String, Object> map = new HashMap<String, Object>();
		if (documento instanceof DocumentoCorporativo) {
			map.put("FOLIO_SAP", ((DocumentoCorporativo) documento).getFolioSap());
		} else if (documento instanceof DocumentoSucursal) {
			map.put("SUCURSAL", documento.getEstablecimiento().getNombre());
		}
		
		map.put("TIPO_DOC", documento.getTipoDocumento().getNombre());
		map.put(JRParameter.REPORT_LOCALE, locale);
		map.put("NUM_SERIE_CERT", documentoXmlService.obtenerNumCertificado(documento.getXmlCfdi()));
		map.put("SELLO_CFD", documento.getTimbreFiscalDigital().getSelloCFD());
		map.put("SELLO_SAT", documento.getTimbreFiscalDigital().getSelloSAT());
		map.put("FECHA_TIMBRADO", documento.getTimbreFiscalDigital().getFechaTimbrado());
		map.put("FOLIO_FISCAL", documento.getTimbreFiscalDigital().getUUID());
		map.put("CADENA_ORIGINAL", documento.getCadenaOriginal());
		map.put("PATH_IMAGES", pathImages);
		map.put("QRCODE", codigoQRService.generaCodigoQR(documento));
		map.put("LETRAS", NumerosALetras.convertNumberToLetter(documento.getComprobante().getTotal().toString()));
		map.put("REGIMEN", documento.getComprobante().getEmisor().getRegimenFiscal().get(0).getRegimen());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(comprobantes);

		try {
			reporteCompleto = JasperFillManager.fillReport(reporteCompilado, map, dataSource);
			bytesReport = JasperExportManager.exportReportToPdf(reporteCompleto);
			return bytesReport;
		} catch (JRException e) {
			logger.error("Ocurrió un error al crear el PDF: {}", e);
			throw new PortalException("Ocurrió un error al crear el PDF: " + e.getMessage());
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
		Documento documentoNcr = new Documento();
		documentoNcr.setEstablecimiento(establecimiento);
		documentoNcr.setCliente(cliente);
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
	
	@Transactional(readOnly = true)
	@Override
	public boolean isArticuloSinPrecio(String claveArticulo) {
		List<String> articulosSinPrecio = ticketService.readArticulosSinPrecio();
		if (articulosSinPrecio != null) {
			return articulosSinPrecio.contains(claveArticulo);
		}
		return false;
	}
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
