package com.magnabyte.cfdi.portal.web.cfdi.impl;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.certificado.CertificadoService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
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
	
	@Value("${hora.inicio}")
	private int horaInicio;
	
	@Value("${hora.cierre}")
	private int horaCierre;
	
	@Override
	public void generarDocumento(Documento documento, HttpServletRequest request) {
		logger.debug("cfdiService...");
		CertificadoDigital certificado = certificadoService.readVigente(documento.getComprobante());
		documentoService.guardarDocumento(documento);
		if (documentoService.sellarComprobante(documento.getComprobante(), certificado)) {
			if (documentoWebService.timbrarDocumento(documento, request)) {
				documentoService.insertDocumentoCfdi(documento);
				documentoService.insertAcusePendiente(documento);
				if(documento instanceof DocumentoSucursal) {
					ticketService.updateEstadoFacturado((DocumentoSucursal) documento);
				}
			}
		}	
	}
	
	@Override
	public void closeOfDay(Establecimiento establecimiento) {
		
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (hora > horaCierre) {
			String fechaCierre = "20131207";
			ticketService.closeOfDay(establecimiento, fechaCierre);
		} else {
			logger.error("El cierre del dia actual es posible realizarlo hasta despues del cierre de la tienda");
			throw new PortalException("El cierre del dia actual es posible realizarlo hasta despues del cierre de la tienda");
		}
	}
}