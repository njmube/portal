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
import com.magnabyte.cfdi.portal.model.cliente.comparator.ComparadorNombre;
import com.magnabyte.cfdi.portal.model.cliente.comparator.ComparadorRfc;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private DomicilioClienteService domicilioClienteService;
	
	@Autowired
	private ComparadorRfc comparadorRfc;
	
	@Autowired
	private ComparadorNombre comparadorNombre;
	
	@Transactional(readOnly = true)
	@Override
	public Cliente findClienteByRfc(Cliente cliente) {
		Cliente clienteBD = clienteDao.findClienteByRfc(cliente);
		if (clienteBD != null) {
			clienteBD.setDomicilios(domicilioClienteService.getByCliente(clienteBD));
		}
		return clienteBD;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Cliente> findClientesByNameRfc(Cliente cliente) {
		List<Cliente> clientes = null;
		
		if (cliente.getRfc() != null && !cliente.getRfc().equals("")) {
			cliente.setRfc("%" + cliente.getRfc() + "%");
		}
		
		if (cliente.getNombre() != null && !cliente.getNombre().equals("")) {
			cliente.setNombre("%" + cliente.getNombre() + "%");
		}
		
		if(cliente.getNombre().equals("") && cliente.getRfc().equals("")) {
			clientes = clienteDao.getAll();
		} else if(!cliente.getNombre().equals("") || !cliente.getRfc().equals("")) {			
			clientes = clienteDao.findClientesByNameRfc(cliente);
		}
		
		return clientes;
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
				logger.error("El id de cliente no puede ser nulo.");
				throw new PortalException("El id de cliente no puede ser nulo.");
			}
		} else {
			logger.error("El cliente no puede ser nulo.");
			throw new PortalException("El cliente no puede ser nulo.");
		}
		return cteBD;
	}

	@Transactional
	@Override
	public void save(Cliente cliente) {
		if(cliente != null) {
			if(isExtranjero(cliente)) {
				guardarExtranjeroVentasMostrador(cliente);				
			} else {
				if(!existRfc(cliente)) {
					clienteDao.save(cliente);
					guardaDomicilioCliente(cliente);
				} else {
					logger.error("El rfc del cliente ya existe, no es "
							+ "posible guardarlo nuevamente.");
					throw new PortalException("El rfc del cliente ya existe, no es "
							+ "posible guardarlo nuevamente.");
				}
			}
		} else {
			logger.error("El cliente no puede ser nulo.");
			throw new PortalException("El cliente no puede ser nulo.");
		}
	}

	private void guardaDomicilioCliente(Cliente cliente) {
		if(cliente.getDomicilios() != null && !cliente.getDomicilios().isEmpty()) {
			domicilioClienteService.save(cliente);
		} else {
			logger.error("La lista de direcciones no puede estar vacia.");
			throw new PortalException("La lista de direcciones no puede estar vacia.");
		}
	}

	private void guardarExtranjeroVentasMostrador(Cliente cliente) {
		if(!existNombre(cliente)) {
			clienteDao.save(cliente);
			guardaDomicilioCliente(cliente);
		} else {
			logger.error("El nombre del cliente ya existe, no es "
					+ "posible guardarlo nuevamente.");
			throw new PortalException("El nombre del cliente ya existe, no es "
					+ "posible guardarlo nuevamente.");
		}
	}
	
	private boolean isExtranjero(Cliente cliente) {
		return (cliente.getDomicilios().get(0).getEstado()
				.getPais().getId() != 1);
	}

	@Transactional
	@Override
	public void saveClienteCorporativo(Cliente cliente) {
		if(cliente != null) {
			clienteDao.save(cliente);
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
				logger.error("La lista de direcciones de cliente está vacía.");
				throw new PortalException("La lista de direcciones de cliente está vacía.");
			}
		} else {
			logger.error("El cliente no puede ser nulo.");
			throw new PortalException("El cliente no puede ser nulo.");
		}
	}
	@Override
	public Cliente readClientesByNameRfc(Cliente cliente) {
		Cliente cteBD = null;
		if(cliente != null) {
			if(cliente.getRfc() != null && cliente.getNombre() != null) {
				cteBD = clienteDao.readClientesByNameRfc(cliente);
//				if(cteBD != null) {					
//					cteBD.setDomicilios(domicilioClienteService.getByCliente(cteBD));
//				}
			} else {
				logger.error("El rfc y nombre de cliente no puede ser nulo.");
				throw new PortalException("El rfc y nombre de cliente no puede ser nulo.");
			}
		} else {
			logger.error("El cliente no puede ser nulo.");
			throw new PortalException("El cliente no puede ser nulo.");
		}
		return cteBD;
	}

	@Override
	public boolean exist(Cliente cliente) {
		Cliente clienteBD = readClientesByNameRfc(cliente);
		if(clienteBD != null) {
			if(clienteBD.equals(cliente)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean existRfc(Cliente cliente) {
		Cliente clienteBD = findClienteByRfc(cliente);
		return comparadorRfc.compare(cliente, clienteBD) == 0;
	}
	
	private boolean existNombre(Cliente cliente) {
		Cliente clienteBD = findClienteByName(cliente);
		return comparadorNombre.compare(cliente, clienteBD) == 0;
	}

	@Override
	public boolean comparaDirecciones(DomicilioCliente domicilio,
		DomicilioCliente domicilioBD) {
		logger.debug(domicilioBD.toString());
		logger.debug(domicilio.toString());
		return domicilioBD.compara(domicilio);
	}

	@Override
	public List<Cliente> getAll() {		
		return clienteDao.getAll();
	}

	@Override
	public Cliente findClienteByName(Cliente cliente) {		
		return clienteDao.getClienteByNombre(cliente);
	}
}
