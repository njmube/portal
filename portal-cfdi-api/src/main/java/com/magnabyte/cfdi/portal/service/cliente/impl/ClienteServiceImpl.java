package com.magnabyte.cfdi.portal.service.cliente.impl;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante.Receptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.dao.cliente.ClienteDao;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteDao clienteDao;
	
	@Override
	public List<Receptor> findClientesByNameRfc(Receptor receptor) {
		if (receptor.getRfc() != null && !receptor.getRfc().equals("")) {
			receptor.setRfc("%" + receptor.getRfc() + "%");
		}
		
		if (receptor.getNombre() != null && !receptor.getNombre().equals("")) {
			receptor.setNombre("%" + receptor.getNombre() + "%");
		}
		return clienteDao.findClientesByNameRfc(receptor);
	}

}
