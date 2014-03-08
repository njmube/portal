package com.magnabyte.cfdi.portal.service.cliente.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.cliente.DomicilioClienteDao;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el servicio de domicilio de cliente
 */
@Service("domicilioClienteService")
public class DomicilioClienteServiceImpl implements DomicilioClienteService {

	public static final Logger logger = Logger
			.getLogger(DomicilioClienteServiceImpl.class);
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private DomicilioClienteDao domicilioClienteDao;
	
	@Transactional(readOnly = true)
	@Override
	public DomicilioCliente readById(DomicilioCliente domicilioCliente) {
		return domicilioClienteDao.readById(domicilioCliente);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<DomicilioCliente> getByCliente(Cliente cliente) {
		List<DomicilioCliente> domiciliosBD = null;
		if(cliente != null) {
			if(cliente.getId() != null) {
				domiciliosBD = domicilioClienteDao.readByCliente(cliente);
			} else {
				logger.error(messageSource.getMessage("cliente.id.nulo", null, null));
				throw new PortalException(messageSource.getMessage("cliente.id.nulo", null, null));
			}
		} else {
			logger.error(messageSource.getMessage("cliente.nulo", null, null));
			throw new PortalException(messageSource.getMessage("cliente.nulo", null, null));
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
			logger.error(messageSource.getMessage("domicilio.lista.vacia", null, null));
			throw new PortalException(messageSource.getMessage("domicilio.lista.vacia", null, null));
		}
		
	}

	@Transactional
	@Override
	public void update(Cliente cliente) {
		List<DomicilioCliente> domNuevos = cliente.getDomicilios();		
		List<DomicilioCliente> domAnteriores = getByCliente(cliente);
		
		if(domNuevos != null && !domNuevos.isEmpty()) {
			for (DomicilioCliente domicilio : domNuevos) {
				if(domicilio.getCliente() == null) {
					domicilio.setCliente(cliente);
				}
				if (domAnteriores.contains(domicilio)) {
					domicilioClienteDao.update(domicilio);
				} else if (!domAnteriores.contains(domicilio)) {
					domicilioClienteDao.save(domicilio);
				}
			}
			
		} else {
			logger.error(messageSource.getMessage("domicilio.lista.vacia", null, null));
			throw new PortalException(messageSource.getMessage("domicilio.lista.vacia", null, null));
		}
	}
	
	@Transactional
	@Override
	public void saveEstado(Estado estado) {
		if(estado != null) {
			domicilioClienteDao.saveEstado(estado);
		} else {
			logger.error(messageSource.getMessage("domicilio.estado.nulo", null, null));
			throw new PortalException(messageSource.getMessage("domicilio.estado.nulo", null, null));
		}
	}
	
	@Transactional
	@Override
	public void savePaisSinEstado(DomicilioCliente domicilio, Pais pais) {
		if(pais != null) {
			domicilioClienteDao.savePaisSinEstado(domicilio, pais);
		} else {
			logger.error(messageSource.getMessage("domicilio.pais.nulo", null, null));
			throw new PortalException(messageSource.getMessage("domicilio.pais.nulo", null, null));
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Estado readEstado(Estado estado) {
		return domicilioClienteDao.readEstado(estado);
	}

	@Transactional(readOnly = true)
	@Override
	public Pais readPais(Pais pais) {
		return domicilioClienteDao.readPais(pais);
	}
}
