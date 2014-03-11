package com.magnabyte.cfdi.portal.service.cliente.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.cliente.ClienteDao;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.cliente.comparator.ComparadorNombre;
import com.magnabyte.cfdi.portal.model.cliente.comparator.ComparadorRfc;
import com.magnabyte.cfdi.portal.model.cliente.enumeration.TipoPersona;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el servicio de cliente
 */
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
	
	@Autowired
	private MessageSource messageSource;
	
	private static final String RFC_REGEX = "^[A-Z,Ñ,&amp;]{4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?$";
	private static final String LIKE = "%";
	
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
		
		if (cliente.getRfc() != null && !cliente.getRfc().isEmpty()) {
			cliente.setRfc(LIKE + cliente.getRfc() + LIKE);
		}
		
		if (cliente.getNombre() != null && !cliente.getNombre().isEmpty()) {
			cliente.setNombre(LIKE + cliente.getNombre() + LIKE);
		}
		
		if(cliente.getNombre().isEmpty() && cliente.getRfc().isEmpty()) {
			clientes = clienteDao.getAll();
		} else if(!cliente.getNombre().isEmpty() || !cliente.getRfc().isEmpty()) {			
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
				logger.error(messageSource.getMessage("cliente.id.nulo", null, null));
				throw new PortalException(messageSource.getMessage("cliente.id.nulo", null, null));
			}
		} else {
			logger.error(messageSource.getMessage("cliente.nulo", null, null));
			throw new PortalException(messageSource.getMessage("cliente.nulo", null, null));
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
					logger.error(messageSource.getMessage("cliente.rfc.existente", null, null));
					throw new PortalException(messageSource.getMessage("cliente.rfc.existente", null, null));
				}
			}
		} else {
			logger.error(messageSource.getMessage("cliente.nulo", null, null));
			throw new PortalException(messageSource.getMessage("cliente.nulo", null, null));
		}
	}

	private void guardaDomicilioCliente(Cliente cliente) {
		if(cliente.getDomicilios() != null && !cliente.getDomicilios().isEmpty()) {
			domicilioClienteService.save(cliente);
		} else {
			logger.error(messageSource.getMessage("domicilio.lista.vacia", null, null));
			throw new PortalException(messageSource.getMessage("domicilio.lista.vacia", null, null));
		}
	}

	private void guardarExtranjeroVentasMostrador(Cliente cliente) {
		if(!existNombre(cliente)) {
			clienteDao.save(cliente);
			guardaDomicilioCliente(cliente);
		} else {
			logger.error(messageSource.getMessage("cliente.nombre.existente", null, null));
			throw new PortalException(messageSource.getMessage("cliente.nombre.existente", null, null));
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
			Pattern pattern = Pattern.compile(RFC_REGEX);
			Matcher matcher = pattern.matcher(cliente.getRfc());
			if (matcher.matches()) {
				cliente.setTipoPersona(new TipoPersona(TipoPersona.PERSONA_FISICA));
			} else {
				cliente.setTipoPersona(new TipoPersona(TipoPersona.PERSONA_MORAL));
			}
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
				logger.error(messageSource.getMessage("domicilio.lista.vacia", null, null));
				throw new PortalException(messageSource.getMessage("domicilio.lista.vacia", null, null));
			}
		} else {
			logger.error(messageSource.getMessage("cliente.nulo", null, null));
			throw new PortalException(messageSource.getMessage("cliente.nulo", null, null));
		}
	}
	
	@Transactional(readOnly = true)
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
				logger.error(messageSource.getMessage("cliente.rfcnombre.nulo", null, null));
				throw new PortalException(messageSource.getMessage("cliente.rfcnombre.nulo", null, null));
			}
		} else {
			logger.error(messageSource.getMessage("cliente.nulo", null, null));
			throw new PortalException(messageSource.getMessage("cliente.nulo", null, null));
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
		if (clienteBD != null) {
			return comparadorRfc.compare(cliente, clienteBD) == 0;
		} else {
			return false;
		}
		
	}
	
	private boolean existNombre(Cliente cliente) {
		Cliente clienteBD = findClienteByName(cliente);
		if (clienteBD != null) {
			return comparadorNombre.compare(cliente, clienteBD) == 0;
		} else {
			return false;
		}
	}

	@Override
	public boolean comparaDirecciones(DomicilioCliente domicilio,
		DomicilioCliente domicilioBD) {
		logger.debug(domicilioBD.toString());
		logger.debug(domicilio.toString());
		return domicilioBD.compara(domicilio);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Cliente> getAll() {		
		return clienteDao.getAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente findClienteByName(Cliente cliente) {		
		return clienteDao.getClienteByNombre(cliente);
	}
	
}
