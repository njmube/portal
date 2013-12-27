package com.magnabyte.cfdi.portal.service.cliente.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.cliente.DomicilioClienteDao;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;

@Service("domicilioClienteService")
public class DomicilioClienteServiceImpl implements DomicilioClienteService {

	public static final Logger logger = Logger
			.getLogger(DomicilioClienteServiceImpl.class);

	@Autowired
	private DomicilioClienteDao domicilioClienteDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<DomicilioCliente> getByCliente(Cliente cliente) {
		List<DomicilioCliente> domiciliosBD = null;
		if(cliente != null) {
			if(cliente.getId() != null) {
				domiciliosBD = domicilioClienteDao.readByCliente(cliente);
			} else {
				logger.debug("El id de cliente no puede ser nulo.");
			}
		} else {
			logger.debug("El cliente no puede ser nulo.");
		}
		return domiciliosBD;
	}

	@Transactional
	@Override
	public void save(Cliente cliente) {
		if(!cliente.getDomicilios().isEmpty()) {
			for(DomicilioCliente domicilio : cliente.getDomicilios()) {
				domicilio.setCliente(cliente);
				domicilioClienteDao.save(domicilio);
			}
		} else {
			logger.debug("La lista de domicilios no puede ser vacia.");
		}
		
	}

	@Transactional
	@Override
	public void update(Cliente cliente) {
		List<DomicilioCliente> domNuevos = cliente.getDomicilios();		
		List<DomicilioCliente> domAnteriores = getByCliente(cliente);
		
		if(cliente.getDomicilios() != null) {
			for (DomicilioCliente domicilio : domNuevos) {
				if(domicilio.getCliente() == null) {
					domicilio.setCliente(cliente);
				}
				if (domAnteriores.contains(domicilio) && domicilio.getCalle() != null) {
					domicilioClienteDao.update(domicilio);
				} else if (!domAnteriores.contains(domicilio) && domicilio.getCalle() != null) {
					domicilioClienteDao.save(domicilio);
				}
			}
			for (DomicilioCliente domicilio : domAnteriores) {
				if (!domNuevos.contains(domicilio)) {
					domicilioClienteDao.delete(domicilio);
				}
			}
			
		}
	}
}
