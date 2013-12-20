package com.magnabyte.cfdi.portal.web.webservice.impl;

import mx.gob.sat.cfd._3.Comprobante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certus.facturehoy.ws2.cfdi.WsEmisionTimbrado;
import com.certus.facturehoy.ws2.cfdi.WsResponseBO;
import com.certus.facturehoy.ws2.cfdi.WsServicioBO;
import com.certus.facturehoy.ws2.cfdi.WsServicios;
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
	
	@Override
	public void timbrarDocumento(Comprobante comprobante) {
		logger.debug("en timbrar Documento");
		String user = "AAA010101AAA.Test.User";
		String password = "Prueba$1";
		WsResponseBO response = new WsResponseBO();
		//QUITAR
		comprobante.getEmisor().setRfc("AAA010101AAA");
		
		//
		response = wsEmisionTimbrado.emitirTimbrar(user, password, obtenerIdServicio(user, password), documentoXmlService.convierteComprobanteAByteArray(comprobante));
		
		if (!response.isIsError()) {
			if (response.getXML() != null) {
				comprobante = documentoXmlService.convierteByteArrayAComprobante(response.getXML());
				documentoXmlService.convierteComprobanteAStream(comprobante);
			}
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
			throw new RuntimeException(serviciosContratados.getMensaje());
		}
		idServicio = 2764104;
		return idServicio;
	}
}
