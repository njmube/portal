package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.establecimiento.DomicilioEstablecimientoDao;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.establecimiento.DomicilioEstablecimientoService;

@Service("domicilioEstablecimientoService")
public class DomicilioEstablecimientoServiceImpl implements
		DomicilioEstablecimientoService {

	private static final Logger logger = LoggerFactory
			.getLogger(DomicilioEstablecimientoServiceImpl.class);

	@Autowired
	DomicilioEstablecimientoDao domicilioEstablecimientoDao;

	@Transactional
	@Override
	public void save(DomicilioEstablecimiento domicilioEstablecimiento) {
		if (domicilioEstablecimiento != null) {
			domicilioEstablecimientoDao.save(domicilioEstablecimiento);
		} else {
			logger.error("El domicilio no puede estar vacio.");
			throw new PortalException("El domicilio no puede estar vacio.");
		}

	}

	@Transactional
	@Override
	public void update(DomicilioEstablecimiento domicilioEstablecimiento) {

		if (domicilioEstablecimiento != null) {
			domicilioEstablecimientoDao.update(domicilioEstablecimiento);

		} else {
			logger.error("El domicilio no puede estar vacio.");
			throw new PortalException("El domicilio no puede estar vacio.");
		}
	}
	
	@Override
	public Estado readEstado(Estado estado) {
		return domicilioEstablecimientoDao.readEstado(estado);
	}
	
	@Override
	public DomicilioEstablecimiento readById(DomicilioEstablecimiento domicilioEstablecimiento) {
		return domicilioEstablecimientoDao.readById(domicilioEstablecimiento);
		
	}

}
