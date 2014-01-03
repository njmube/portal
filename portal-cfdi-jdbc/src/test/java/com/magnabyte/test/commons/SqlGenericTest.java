package com.magnabyte.test.commons;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.cliente.sql.ClienteSql;
import com.magnabyte.cfdi.portal.dao.documento.DocumentoDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml" })
public class SqlGenericTest {

	@Autowired
	DocumentoDao documentoDao;
	
	public static final Logger logger = Logger.getLogger(SqlGenericTest.class);

	@Test
	public void qryTest() {
		String qryString = ClienteSql.GET_ALL;
		logger.info(qryString);
		Assert.assertNotNull(qryString);
	}
}
