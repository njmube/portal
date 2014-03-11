package com.magnabyte.cfdi.portal.web.webservice.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.certus.facturehoy.ws2.cfdi.WsEmisionTimbrado;
import com.certus.facturehoy.ws2.cfdi.WsResponseBO;
import com.certus.facturehoy.ws2.cfdi.WsServicioBO;
import com.certus.facturehoy.ws2.cfdi.WsServicios;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.tfd.v32.TimbreFiscalDigital;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.web.webservice.DocumentoWebService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que represente el controlador de documento web service
 */
@Component("documentoWebService")
public class DocumentoWebServiceImpl implements DocumentoWebService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoWebServiceImpl.class);
	
	@Autowired
	private WsServicios wsServicios;
	
	@Autowired
	private WsEmisionTimbrado wsEmisionTimbrado;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${ws.user}")
	private String userWs;
	
	@Value("${ws.password}")
	private String passwordWs;
	
	@Override
	public boolean timbrarDocumento(Documento documento, int idServicio) {
		TimbreFiscalDigital timbre = null;
		logger.debug("en timbrar Documento");
		WsResponseBO response = new WsResponseBO();
		
		try {
			if(idServicio < 1) {
				throw new Exception(messageSource.getMessage("ws.error.conexion", null, null));
			}
			response = wsEmisionTimbrado.emitirTimbrar(userWs, passwordWs, idServicio, 
				documentoXmlService.convierteComprobanteAByteArray(documento.getComprobante(), PortalUtils.encodingUTF8));
		} catch(Exception ex) {
			documentoService.insertDocumentoPendiente(documento, TipoEstadoDocumentoPendiente.TIMBRE_PENDIENTE);
			logger.debug(messageSource.getMessage("ws.error.conexion", null, null));
			throw new PortalException(messageSource.getMessage("ws.error.conexion", null, null));
		}
		
		if (!response.isIsError()) {
			timbre = new TimbreFiscalDigital();
			timbre.setFechaTimbrado(response.getFechaHoraTimbrado());
			timbre.setSelloCFD(response.getSelloDigitalEmisor());
			timbre.setSelloSAT(response.getSelloDigitalTimbreSAT());
			timbre.setUUID(response.getFolioUDDI());
			timbre.setNoCertificadoSAT(documentoXmlService.obtenerNumCertificado(response.getXML()));
			documento.setCadenaOriginal(response.getCadenaOriginal());
			documento.setTimbreFiscalDigital(timbre);
			documento.setComprobante(documentoXmlService.convierteByteArrayAComprobante(response.getXML()));
			documento.setXmlCfdi(documentoXmlService
					.convierteComprobanteAByteArray(documento.getComprobante(), PortalUtils.encodingUTF16));

			return true;
		} else {
			documentoService.insertDocumentoPendiente(documento, TipoEstadoDocumentoPendiente.TIMBRE_PENDIENTE);
			logger.info("El Web Service devolvió un error: {}", response.getMessage());
			throw new PortalException(response.getMessage());
		}
	}
	
	@Override
	public void recuperarAcusesPendientes() {
		List<Documento> documentosPendientesAcuse = new ArrayList<Documento>();
		
		documentosPendientesAcuse = documentoService.obtenerAcusesPendientes();
		
		if (documentosPendientesAcuse != null) {
			for (Documento documento : documentosPendientesAcuse) {
				logger.debug("documento pendiente: {}", documento);
				documento.setEstablecimiento(establecimientoService.readRutaById(documento.getEstablecimiento()));
				recuperarAcuse(documento);
			}
		}
	}
	
	@Override
	public void recuperarAcuse(Documento documento) {
		logger.debug("recuperando acuse");
		
		WsResponseBO response = new WsResponseBO();
		try {
			response = wsEmisionTimbrado.recuperarAcuse(userWs, passwordWs, documento.getTimbreFiscalDigital().getUUID());
		} catch(Exception ex) {
			logger.debug(messageSource.getMessage("ws.error.conexion", null, null));
			throw new PortalException(messageSource.getMessage("ws.error.conexion", null, null));
		}
	
		if (response.getAcuse() != null) {
			logger.debug("llamada a samba");
			//FIXME Solo para pruebas, quitar para produccion.
			String acuse = "<acuse>Aqui va el acuse</acuse>";
			try {
				response.setAcuse(acuse.getBytes(PortalUtils.encodingUTF16));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//
			documento.setXmlCfdiAcuse(response.getAcuse());
			documentoService.saveAcuseCfdiXmlFile(documento);
			documentoService.deleteDocumentoPendiente(documento, TipoEstadoDocumentoPendiente.ACUSE_PENDIENTE);
		} else {
			logger.info("El webservice no devolvio el acuse");
			logger.info(response.getMessage());
		}
	}
	
	@Override
	public int obtenerIdServicio() {
		int idServicio = 0;
		WsServicioBO serviciosContratados = null;
		try {
			serviciosContratados = wsServicios.obtenerServicios(userWs, passwordWs);
		} catch(Exception ex) {
			logger.error(messageSource.getMessage("ws.error.conexion", null, null));
			return idServicio;
		}
		if (serviciosContratados.getArray().size() > 0) {
			for (WsServicioBO servicio : serviciosContratados.getArray()) {
				idServicio = servicio.getIdProcess();
			}
		} else {
			logger.info("No hay servicios contratados: {}", serviciosContratados.getMensaje());
			return idServicio;
		}
		//FIXME Comentar para produccion
		idServicio = 5652528;
		return idServicio;
	}
}
