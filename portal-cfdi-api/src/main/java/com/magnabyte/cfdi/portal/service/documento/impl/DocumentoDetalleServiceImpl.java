package com.magnabyte.cfdi.portal.service.documento.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.dao.documento.DocumentoDetalleDao;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.documento.DocumentoDetalleService;

@Service("documentoDetalleService")
public class DocumentoDetalleServiceImpl  implements DocumentoDetalleService {

	private static final Logger logger = 
			LoggerFactory.getLogger(DocumentoDetalleServiceImpl.class);
	
	@Autowired
	DocumentoDetalleDao documentoDetalleDao;
	
	@Override
	public void save(Documento documento) {
		if(!documento.getComprobante().getConceptos()
				.getConcepto().isEmpty()) {
			documentoDetalleDao.save(documento);
		} else {
			logger.debug("La lista de Conceptos no puede ser nula.");
			throw new PortalException("La lista de Conceptos no puede ser nula.");
		}
	}

}
