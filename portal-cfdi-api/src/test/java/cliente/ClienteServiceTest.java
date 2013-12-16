package cliente;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.cliente.dummys.ClienteDummy;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml",
		"classpath:/serviceApplicationContext.xml" })
public class ClienteServiceTest {

	private static final Logger logger = Logger.getLogger(ClienteServiceTest.class);
	
	@Autowired
	ClienteService clienteService;
	
	public static Cliente CLIENTE = null;
	
	static {
		CLIENTE = ClienteDummy.generateCliente();
	}
	
	@Test
	public void readCliente() {
		logger.info("Recuperando el cliente de la base de datos.");
		Cliente cliente = clienteService.read(CLIENTE);
		Assert.assertNotNull(cliente.getId());
	}
	
	@Test
	public void readListClientes() {
		logger.info("Recuperando la lista de clientes de la base de datos.");
		List<Cliente> clientes = clienteService.findClientesByNameRfc(CLIENTE);
		Assert.assertNotNull(clientes);		
	}
}
