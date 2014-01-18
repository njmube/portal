package com.magnabyte.test.documento;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.documento.DocumentoDao;
import com.magnabyte.cfdi.portal.dao.documento.DocumentoSerieDao;
import com.magnabyte.cfdi.portal.dao.documento.sql.DocumentoSql;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/jdbcApplicationContext.xml")
public class DocumentoDaoTest {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoDaoTest.class);
	
	@Autowired
	private DocumentoSerieDao documentoSerieDao;
	
	@Autowired
	private DocumentoDao documentoDao;

//	@Test
	public void pruebaSerieFolio() {
		Documento d = new DocumentoSucursal();
		Establecimiento e = new Establecimiento();
		e.setId(3);
		d.setEstablecimiento(e);
		d.setTipoDocumento(TipoDocumento.FACTURA);
		Map<String, Object> map = documentoSerieDao.readSerieAndFolio(d);
		logger.debug("map not null");
		Assert.assertNotNull(map);
		logger.debug("serie");
		Assert.assertEquals("ZZ1", map.get(DocumentoSql.SERIE));
		logger.debug("folio");
		Assert.assertEquals(1, Integer.parseInt(((String) map.get(DocumentoSql.FOLIO_CONSECUTIVO))));
	}
	
	@Test
	public void read() {
		Documento documento = new Documento();
		documento.setId(363);
		documento = documentoDao.read(documento);
		
		logger.debug("fin");
	}
}
