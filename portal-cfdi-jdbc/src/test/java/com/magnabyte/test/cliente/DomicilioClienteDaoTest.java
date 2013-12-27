package com.magnabyte.test.cliente;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.cliente.DomicilioClienteDao;
import com.magnabyte.cfdi.portal.dao.cliente.dummys.ClienteDummy;
import com.magnabyte.cfdi.portal.dao.cliente.dummys.DomicilioClienteDummy;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml" })
public class DomicilioClienteDaoTest {
	
	private static final Logger logger = Logger.getLogger(DomicilioClienteDaoTest.class);
	
	@Autowired
	DomicilioClienteDao domicilioClienteDao;
	
	public static DomicilioCliente DOMICILIOS = null;
	public static Cliente CLIENTE = null;
	
	static {
		DOMICILIOS = DomicilioClienteDummy.generateDomicilioCliente();
		CLIENTE = ClienteDummy.generateCliente();		
	}
	
//	@Test
	public void save() {
		logger.info("Instertando un domicilio de cliente en la base de datos");
		DomicilioCliente domicilio = DOMICILIOS;
		domicilioClienteDao.save(domicilio);
		Assert.assertNotNull(domicilio.getId());
	}
	
	@Test
	public void update() {
		logger.info("Actualizando un domicilio de cliente en la base de datos");
		DomicilioCliente domicilio = DOMICILIOS;
		domicilio.setId(3);
		domicilioClienteDao.update(domicilio);
		Assert.assertNotNull(domicilio.getId());
	}
	
	@Test
	public void readCte() {
		logger.info("Recuperando las direcciones de cliente de la base de datos.");
		List<DomicilioCliente> domicilios = domicilioClienteDao.readByCliente(CLIENTE);
		Assert.assertNotNull(domicilios);
		Assert.assertEquals(1, domicilios.size());
	}

}
