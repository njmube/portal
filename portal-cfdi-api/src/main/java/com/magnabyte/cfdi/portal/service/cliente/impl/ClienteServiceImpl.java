package com.magnabyte.cfdi.portal.service.cliente.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.dao.cliente.ClienteDao;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private DomicilioClienteService domicilioClienteService;
	
	@Override
	public List<Cliente> findClientesByNameRfc(Cliente cliente) {
		if (cliente.getRfc() != null && !cliente.getRfc().equals("")) {
			cliente.setRfc("%" + cliente.getRfc() + "%");
		}
		
		if (cliente.getNombre() != null && !cliente.getNombre().equals("")) {
			cliente.setNombre("%" + cliente.getNombre() + "%");
		}
		return clienteDao.findClientesByNameRfc(cliente);
	}

	@Override
	public Cliente read(Cliente cliente) {
		Cliente cteBD = null;
		if(cliente != null) {
			if(cliente.getId() != null) {
				cteBD = clienteDao.read(cliente);
				cteBD.setDomicilios(domicilioClienteService.getByCliente(cliente));
				logger.debug("Nombre ClienteBD: " + cteBD.getNombre());
			} else {
				logger.debug("El id de cliente no puede ser nulo.");
			}
		} else {
			logger.debug("El cliente no puede ser nulo.");
		}
		return cteBD;
	}

	@Override
	public void save(Cliente cliente) {
		if(cliente != null) {
			clienteDao.save(cliente);
			if(!cliente.getDomicilios().isEmpty()) {
				domicilioClienteService.save(cliente);
			} else {
				logger.debug("La lista de direcciones no puede estar vacia.");
			}
		} else {
			logger.debug("El cliente no puede ser nulo.");
		}
	}

	@Override
	public void update(Cliente cliente) {
		if(cliente != null) {
			clienteDao.update(cliente);
			if(!cliente.getDomicilios().isEmpty()) {
				domicilioClienteService.update(cliente);
			} else {
				logger.debug("La lista de direcciones de cliente está vacía.");
			}
		} else {
			logger.debug("El cliente no puede ser nulo.");
		}
	}

}
