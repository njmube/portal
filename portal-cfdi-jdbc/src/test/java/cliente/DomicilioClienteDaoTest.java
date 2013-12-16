package cliente;

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
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml" })
public class DomicilioClienteDaoTest {
	
	private static final Logger logger = Logger.getLogger(DomicilioClienteDaoTest.class);
	
	@Autowired
	DomicilioClienteDao domicilioClienteDao;
	
	public static Cliente CLIENTE = null;
	
	static {
		CLIENTE = ClienteDummy.generateCliente();
	}
	
	@Test
	public void readCte() {
		logger.info("Recuperando las direcciones de cliente de la base de datos.");
		List<DomicilioCliente> domicilios = domicilioClienteDao.readByCliente(CLIENTE);
		Assert.assertNotNull(domicilios);
		Assert.assertEquals(1, domicilios.size());
	}

}
