package com.magnabyte.cfdi.portal.service.cliente.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.cliente.ClienteDao;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private DomicilioClienteService domicilioClienteService;
	
	@Transactional(readOnly = true)
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

	@Transactional(readOnly = true)
	@Override
	public Cliente read(Cliente cliente) {
		Cliente cteBD = null;
		if(cliente != null) {
			if(cliente.getId() != null) {
				cteBD = clienteDao.read(cliente);
				cteBD.setDomicilios(domicilioClienteService.getByCliente(cliente));
			} else {
				logger.debug("El id de cliente no puede ser nulo.");
			}
		} else {
			logger.debug("El cliente no puede ser nulo.");
		}
		return cteBD;
	}

	@Transactional
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

	@Transactional
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
	
	@Override
	public Cliente readClientesByNameRfc(Cliente cliente) {
		Cliente cteBD = null;
		if(cliente != null) {
			if(cliente.getRfc() != null && cliente.getNombre() != null) {
				cteBD = clienteDao.readClientesByNameRfc(cliente);
				if(cteBD != null) {					
					cteBD.setDomicilios(domicilioClienteService.getByCliente(cteBD));
				}
			} else {
				logger.debug("El rfc y nombre de cliente no puede ser nulo.");
			}
		} else {
			logger.debug("El cliente no puede ser nulo.");
		}
		return cteBD;
	}

	@Override
	public boolean exist(Cliente cliente) {
		Cliente clienteBD = readClientesByNameRfc(cliente); 
		if(clienteBD != null) {
			if(clienteBD.equals(cliente)) {
				DomicilioCliente domicilio = cliente.getDomicilios().get(0);
				if(domicilio != null) {
					for(DomicilioCliente domicilioBD : clienteBD.getDomicilios()) {
						return comparaDirecciones(domicilio, domicilioBD);
					}
				}
				return false;
			}
		}
		return false;
	}

	private boolean comparaDirecciones(DomicilioCliente domicilio,
		DomicilioCliente domicilioBD) {
		logger.debug(domicilioBD.toString());
		logger.debug(domicilio.toString());
		return domicilioBD.compara(domicilio);
	}
}
