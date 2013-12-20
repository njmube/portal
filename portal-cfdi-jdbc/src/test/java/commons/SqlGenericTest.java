package commons;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.cliente.sql.DomicilioSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml" })
public class SqlGenericTest {

	public static final Logger logger = Logger.getLogger(SqlGenericTest.class);

	@Test
	public void qryTest() {
		String qryString = EstablecimientoSql.FIND_BY_CLAVE;
		logger.info(qryString);
		Assert.assertNotNull(qryString);
	}

}
