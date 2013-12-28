package com.magnabyte.test.cliente;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.cliente.ClienteDao;
import com.magnabyte.cfdi.portal.dao.cliente.dummys.ClienteDummy;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml",
		"classpath:/serviceApplicationContext.xml" })
public class ClienteServiceTest {

	private static final Logger logger = Logger.getLogger(ClienteServiceTest.class);
	
	@Mock
	ClienteDao clienteDaoMock;
	
	@Mock
	DomicilioClienteService domicilioServiceMock;
	
	@InjectMocks
	@Autowired
	ClienteService clienteService;
	
	public static Cliente CLIENTE = null;
	public static Cliente CLIENTE_DIF = null;
	public static Cliente CLIENTE_NO_EXISTE = null;
	
	static {
		CLIENTE = ClienteDummy.generateCliente();
		CLIENTE_DIF = ClienteDummy.generateClienteDif();
		CLIENTE_NO_EXISTE = ClienteDummy.generateClienteNoExiste();
	}
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
//	@Test
	public void save() {
		logger.info("Actualizando un cliente completo.");
		Cliente cliente = CLIENTE;
		
		clienteService.save(cliente);
		Assert.assertNotNull(cliente.getId());
		
		Mockito.verify(clienteDaoMock, Mockito.atLeast(1)).save(cliente);
		Mockito.verify(domicilioServiceMock, Mockito.atLeast(1)).save(cliente);
	}
	
//	@Test
	public void update() {
		logger.info("Actualizando un cliente completo.");
		Cliente cliente = CLIENTE;
		clienteService.update(cliente);
		Assert.assertNotNull(cliente.getId());
		
		Mockito.verify(clienteDaoMock, Mockito.atLeastOnce()).update(cliente);
		Mockito.verify(domicilioServiceMock, Mockito.atLeastOnce()).update(cliente);
	}
	
	@Test
	public void readCliente() {
		logger.info("Recuperando el cliente de la base de datos.");
		Cliente cliente = CLIENTE;
		
		Mockito.when(clienteDaoMock.read(cliente)).thenReturn(cliente);		
		clienteService.read(cliente);
		Assert.assertNotNull(cliente.getId());
		
		Mockito.verify(clienteDaoMock, Mockito.atLeastOnce()).read(cliente);
		Mockito.verify(domicilioServiceMock, Mockito.atLeastOnce()).getByCliente(cliente);
	}
	
//	@Test
	public void readListClientes() {
		logger.info("Recuperando la lista de clientes de la base de datos.");
		List<Cliente> clientes = clienteService.findClientesByNameRfc(CLIENTE);
		Assert.assertNotNull(clientes);		
	}
}
