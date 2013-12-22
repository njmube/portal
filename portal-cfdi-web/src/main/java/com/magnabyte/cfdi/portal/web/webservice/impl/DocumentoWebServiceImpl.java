package com.magnabyte.cfdi.portal.web.webservice.impl;

import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certus.facturehoy.ws2.cfdi.WsEmisionTimbrado;
import com.certus.facturehoy.ws2.cfdi.WsResponseBO;
import com.certus.facturehoy.ws2.cfdi.WsServicioBO;
import com.certus.facturehoy.ws2.cfdi.WsServicios;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
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
	private SambaService sambaService;
	
	@Override
	public void timbrarDocumento(Documento documento) {
		TimbreFiscalDigital timbre = null;
		logger.debug("en timbrar Documento");
		String user = "AAA010101AAA.Test.User";
		String password = "Prueba$1";
		WsResponseBO response = new WsResponseBO();
		//QUITAR
		documento.getComprobante().getEmisor().setRfc("AAA010101AAA");
		//
		response = wsEmisionTimbrado.emitirTimbrar(user, password, obtenerIdServicio(user, password), 
				documentoXmlService.convierteComprobanteAByteArray(documento.getComprobante()));
		
		if (!response.isIsError()) {
			timbre = new TimbreFiscalDigital();
			timbre.setFechaTimbrado(response.getFechaHoraTimbrado());
			timbre.setSelloCFD(response.getSelloDigitalEmisor());
			timbre.setSelloSAT(response.getSelloDigitalTimbreSAT());
			timbre.setUUID(response.getFolioUDDI());
			documento.setCadenaOriginal(response.getCadenaOriginal());
			documento.setTimbreFiscalDigital(timbre);
			if (response.getXML() != null) {
				documento.setComprobante(documentoXmlService.convierteByteArrayAComprobante(response.getXML()));
			}
			sambaService.moveProcessedSapFile(documento);
			sambaService.writeProcessedCfdiFile(documentoXmlService.convierteComprobanteAStream(documentoXmlService.convierteByteArrayAComprobante(response.getXML())));
		} else {
			logger.debug("El Web Service devolvió un error.");
			throw new PortalException(response.getMessage());
		}
	}
	
	private int obtenerIdServicio(String user, String password) {
		int idServicio = 0;
		WsServicioBO serviciosContratados = wsServicios.obtenerServicios(user, password);
		if (serviciosContratados.getArray().size() > 0) {
			for (WsServicioBO servicio : serviciosContratados.getArray()) {
				logger.debug("Id proceso {}", servicio.getIdProcess());
				logger.debug("Nombre servicio {}", servicio.getNombreServicio());
				idServicio = servicio.getIdProcess();
			}
		} else {
			logger.debug("No hay servicios contratados");
			throw new PortalException(serviciosContratados.getMensaje());
		}
		//
		idServicio = 2764104;
		//
		return idServicio;
	}
}
