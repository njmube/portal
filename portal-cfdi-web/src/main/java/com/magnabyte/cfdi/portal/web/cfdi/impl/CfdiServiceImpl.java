package com.magnabyte.cfdi.portal.web.cfdi.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
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
	
	@Override
	public void generarDocumento(Documento documento, HttpServletRequest request) {
		logger.debug("cfdiService...");
		documentoService.guardarDocumento(documento);
		if (documentoService.sellarComprobante(documento.getComprobante())) {
			if (documentoWebService.timbrarDocumento(documento, request)) {
				documentoService.insertDocumentoCfdi(documento);
				documentoService.insertAcusePendiente(documento);
				if(documento instanceof DocumentoSucursal) {
					ticketService.updateEstadoFacturado((DocumentoSucursal) documento);
				}
		
			}
		}	
	}
}
