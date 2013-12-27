package commons;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.cliente.sql.ClienteSql;
import com.magnabyte.cfdi.portal.dao.cliente.sql.DomicilioSql;
import com.magnabyte.cfdi.portal.dao.documento.sql.DocumentoDetalleSql;
import com.magnabyte.cfdi.portal.dao.emisor.EmisorDao;
import com.magnabyte.cfdi.portal.dao.emisor.sql.EmisorSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.EstablecimientoDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml" })
public class SqlGenericTest {

	@Autowired
	EmisorDao emisorDao;
	
	public static final Logger logger = Logger.getLogger(SqlGenericTest.class);

	@Test
	public void qryTest() {
		String qryString = DocumentoDetalleSql.INSERT_DETALLE_DOC;
		logger.info(qryString);
		Assert.assertNotNull(qryString);
	}

//	@Test
	public void test() {
		EmpresaEmisor empresa = new EmpresaEmisor();
		empresa.setId(4);
		
		EmpresaEmisor empresaBD = emisorDao.read(empresa);		
		logger.info("---------------- Empresa: " + empresa.toString());
		
		Assert.assertEquals("MOD041014KI3", empresaBD.getEmisor().getRfc());
	}

}
