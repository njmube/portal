package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.establecimiento.SerieFolioEstablecimientoDao;
import com.magnabyte.cfdi.portal.model.establecimiento.SerieFolioEstablecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.establecimiento.SerieFolioEstablecimientoService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:18/03/2014
 * Clase que representa el servicio de la serie y folio del establecimiento
 */
@Service("SerieFolioEstableciminentoService")
public class SerieFolioEstablecimientoServiceImpl implements 
				SerieFolioEstablecimientoService {
	
	public static final Logger logger = Logger
			.getLogger(SerieFolioEstablecimientoServiceImpl.class);
	
	@Autowired
	private SerieFolioEstablecimientoDao serieFolioEstablecimientoDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Transactional
	@Override
	public void save (SerieFolioEstablecimiento serieFolioEstablecimiento) {
		if  (serieFolioEstablecimiento != null) {
			serieFolioEstablecimientoDao.save(serieFolioEstablecimiento);
		} else {
			logger.error(messageSource.getMessage("seriefolioestablecimiento.nulo", null, null));
			throw new PortalException(messageSource.getMessage("seriefolioestablecimiento.nulo", null, null));
		}
		
	}

}
