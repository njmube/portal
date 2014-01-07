package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.establecimiento.RutaEstablecimientoDao;
import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.establecimiento.RutaEstablecimientoService;

@Service("RutaEstablecimiento")
public class RutaEstablecimientoServiceImpl implements RutaEstablecimientoService {
	
	private static final Logger logger = LoggerFactory.getLogger(RutaEstablecimientoServiceImpl.class);
	
	@Autowired
	RutaEstablecimientoDao rutaEstablecimientoDao;
	
	
	@Transactional
	@Override
	public void save(RutaRepositorio rutaRepositorio) {
		if (rutaRepositorio != null) {
			rutaEstablecimientoDao.save(rutaRepositorio);
		} else {
			logger.error("La ruta del establecimiento no puede estar vacia.");
			throw new PortalException("La ruta del establecimiento no puede estar vacia.");
		}
	}
	
	@Transactional
	@Override
	public void update(RutaRepositorio rutaRepositorio) {

		if (rutaRepositorio != null) {
			rutaEstablecimientoDao.update(rutaRepositorio);

		} else {
			logger.error("La ruta del establecimiento no puede estar vacia.");
			throw new PortalException("La ruta del establecimiento no puede estar vacia.");
		}
	}
	
	@Override
	public RutaRepositorio readById (RutaRepositorio rutaRepositorio){
		return rutaEstablecimientoDao.readById(rutaRepositorio);
	}
	

}
