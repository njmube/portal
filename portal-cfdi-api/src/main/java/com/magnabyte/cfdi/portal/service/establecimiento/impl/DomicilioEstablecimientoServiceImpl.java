package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.establecimiento.DomicilioEstablecimientoDao;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.establecimiento.DomicilioEstablecimientoService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el servicio de domicilio de establecimiento
 */
@Service("domicilioEstablecimientoService")
public class DomicilioEstablecimientoServiceImpl implements
		DomicilioEstablecimientoService {

	private static final Logger logger = LoggerFactory
			.getLogger(DomicilioEstablecimientoServiceImpl.class);

	@Autowired
	private DomicilioEstablecimientoDao domicilioEstablecimientoDao;

	@Autowired
	private MessageSource messageSource;
	
	@Transactional
	@Override
	public void save(DomicilioEstablecimiento domicilioEstablecimiento) {
		if (domicilioEstablecimiento != null) {
			domicilioEstablecimientoDao.save(domicilioEstablecimiento);
		} else {
			logger.error(messageSource.getMessage("domicilio.nulo", null, null));
			throw new PortalException(messageSource.getMessage("domicilio.nulo", null, null));
		}

	}

	@Transactional
	@Override
	public void update(DomicilioEstablecimiento domicilioEstablecimiento) {

		if (domicilioEstablecimiento != null) {
			domicilioEstablecimientoDao.update(domicilioEstablecimiento);

		} else {
			logger.error(messageSource.getMessage("domicilio.nulo", null, null));
			throw new PortalException(messageSource.getMessage("domicilio.nulo", null, null));
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public Estado readEstado(Estado estado) {
		return domicilioEstablecimientoDao.readEstado(estado);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Estado findEstado (Estado estado) {
		return domicilioEstablecimientoDao.findEstado(estado);
	}
	
	@Transactional(readOnly = true)
	@Override
	public DomicilioEstablecimiento readById(DomicilioEstablecimiento domicilioEstablecimiento) {
		return domicilioEstablecimientoDao.readById(domicilioEstablecimiento);
		
	}

}
