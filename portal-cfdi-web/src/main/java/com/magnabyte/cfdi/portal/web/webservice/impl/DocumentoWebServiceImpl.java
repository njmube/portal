package com.magnabyte.cfdi.portal.web.webservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.certus.facturehoy.ws2.cfdi.WsEmisionTimbrado;
import com.certus.facturehoy.ws2.cfdi.WsResponseBO;
import com.certus.facturehoy.ws2.cfdi.WsServicioBO;
import com.certus.facturehoy.ws2.cfdi.WsServicios;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.EstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.web.webservice.DocumentoWebService;

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
	private SambaService sambaService;
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
	@Value("${ws.user}")
	private String userWs;
	
	@Value("${ws.password}")
	private String passwordWs;
	
	@Override
	public boolean timbrarDocumento(Documento documento, HttpServletRequest request, int idServicio) {
		TimbreFiscalDigital timbre = null;
		logger.debug("en timbrar Documento");
		WsResponseBO response = new WsResponseBO();
		
		try {
			response = wsEmisionTimbrado.emitirTimbrar(userWs, passwordWs, idServicio, 
				documentoXmlService.convierteComprobanteAByteArrayForWebService(documento.getComprobante()));
//			if(documento != null) {
//				throw new Exception("Sin servicio web service.");
//			}
		} catch(Exception ex) {
			documentoService.insertDocumentoPendiente(documento, EstadoDocumentoPendiente.TIMBRE_PENDIENTE);
			logger.debug("Ocurrío un error al realizar la conexión", ex);
			throw new PortalException("Ocurrío un error al realizar la conexión", ex);
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
			documento.setXmlCfdi(response.getXML());
			if (documento instanceof DocumentoCorporativo) {
//				sambaService.moveProcessedSapFile((DocumentoCorporativo) documento);
			}
//			sambaService.writeProcessedCfdiXmlFile(response.getXML(), documento);
			if(request != null) {
//				sambaService.writePdfFile(documento, request);
			}
			return true;
		} else {
			logger.debug("El Web Service devolvió un error: {}", response.getMessage());
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
			logger.debug("Ocurrío un error al realizar la conexión", ex);
			throw new PortalException("Ocurrío un error al realizar la conexión", ex);
		}
	
		if (response.getAcuse() != null) {
			logger.debug("llamada a samba");
//			sambaService.writeAcuseCfdiXmlFile(response.getAcuse(), documento);
			documentoService.deleteFromAcusePendiente(documento);
		} else {
			logger.debug("El webservice no devolvio el acuse");
			logger.debug(response.getMessage());
		}
	}
	
	@Override
	public int obtenerIdServicio() {
		int idServicio = 0;
		WsServicioBO serviciosContratados = null;
		try {
			serviciosContratados = wsServicios.obtenerServicios(userWs, passwordWs);
		} catch(Exception ex) {
			logger.debug("Ocurrío un error al realizar la conexión, el sistema de facturación no se encuentra disponible por el momento", ex);
			throw new PortalException("Ocurrío un error al realizar la conexión, el sistema de facturación no se encuentra disponible por el momento", ex);
		}
		if (serviciosContratados.getArray().size() > 0) {
			for (WsServicioBO servicio : serviciosContratados.getArray()) {
				logger.debug("Id proceso {}", servicio.getIdProcess());
				logger.debug("Nombre servicio {}", servicio.getNombreServicio());
				idServicio = servicio.getIdProcess();
			}
		} else {
			logger.debug("No hay servicios contratados: {}", serviciosContratados.getMensaje());
			throw new PortalException("No hay servicios contratados: " + serviciosContratados.getMensaje());
		}
		//FIXME Comentar para produccion
		idServicio = 5652528;
		return idServicio;
	}
}
