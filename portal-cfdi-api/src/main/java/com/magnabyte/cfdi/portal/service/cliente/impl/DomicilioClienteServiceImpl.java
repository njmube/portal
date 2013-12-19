package com.magnabyte.cfdi.portal.service.cliente.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
