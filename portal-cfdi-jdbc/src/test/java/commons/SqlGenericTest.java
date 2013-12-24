package commons;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.cliente.sql.ClienteSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.EstablecimientoDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml" })
public class SqlGenericTest {

	@Autowired
	EstablecimientoDao establecimientoDao;
	
	public static final Logger logger = Logger.getLogger(SqlGenericTest.class);

//	@Test
	public void qryTest() {
		String qryString = EstablecimientoSql.READ_BY_CLAVE;
		logger.info(qryString);
		Assert.assertNotNull(qryString);
	}

	@Test
	public void test() {
		Establecimiento es = new Establecimiento();
		es.setClave("001");
		
		Establecimiento esBD = establecimientoDao.readByClave(es);		
		logger.info("---------------- Estab: " + esBD.toString());
		
		Assert.assertEquals("ROLE_CORP", esBD.getTipoEstablecimiento().getRol());
	}

}
