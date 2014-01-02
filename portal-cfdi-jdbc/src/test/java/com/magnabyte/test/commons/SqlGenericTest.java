package com.magnabyte.test.commons;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.documento.DocumentoDao;
import com.magnabyte.cfdi.portal.dao.documento.sql.DocumentoSql;
import com.magnabyte.cfdi.portal.dao.emisor.EmisorDao;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jdbcApplicationContext.xml" })
public class SqlGenericTest {

	@Autowired
	EmisorDao emisorDao;
	
	@Autowired
	DocumentoDao documentoDao;
	
	public static final Logger logger = Logger.getLogger(SqlGenericTest.class);

	@Test
	public void qryTest() {
		String qryString = DocumentoSql.READ_DOCUMENTO_RUTA;
		logger.info(qryString);
		Assert.assertNotNull(qryString);
	}
	
	@Test
	public void daoTest() {
		Cliente cliente = new Cliente();
		cliente.setRfc("XEXX010101000");
		
		List<Documento> docsDB = documentoDao.getDocumentoByCliente(cliente);
		
		Assert.assertNotNull(docsDB);
	}

}
