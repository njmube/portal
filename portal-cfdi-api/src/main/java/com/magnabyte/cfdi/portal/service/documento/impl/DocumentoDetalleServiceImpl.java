package com.magnabyte.cfdi.portal.service.documento.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.documento.DocumentoDetalleDao;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Conceptos;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.documento.DocumentoDetalleService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Clase que representa el servicio de detealle documento
 */
@Service("documentoDetalleService")
public class DocumentoDetalleServiceImpl  implements DocumentoDetalleService {

	private static final Logger logger = 
			LoggerFactory.getLogger(DocumentoDetalleServiceImpl.class);
	
	@Autowired
	private DocumentoDetalleDao documentoDetalleDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Transactional
	@Override
	public void save(Documento documento) {
		if(!documento.getComprobante().getConceptos()
				.getConcepto().isEmpty()) {
			documentoDetalleDao.save(documento);
		} else {
			logger.error(messageSource.getMessage("documento.detalle.conceptos.nulos", null, null));
			throw new PortalException(messageSource.getMessage("documento.detalle.conceptos.nulos", null, null));
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public Conceptos read(Documento documento) {
		return documentoDetalleDao.read(documento);
	}

}
