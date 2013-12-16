package cliente;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.cliente.ClienteDao;
import com.magnabyte.cfdi.portal.dao.cliente.dummys.ClienteDummy;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml" })
public class CienteDaoTest {

	private static final Logger logger = Logger.getLogger(CienteDaoTest.class);
	
	@Autowired
	ClienteDao clienteDao;
	
	public static Cliente CLIENTE = null;
	
	static {
		CLIENTE = ClienteDummy.generateCliente();
	}
	
	@Test
	public void readCte() {
		logger.info("Recuperando el cliente de la base de datos.");
		Cliente cliente = clienteDao.read(CLIENTE);
		Assert.assertNotNull(cliente);
	}
}
