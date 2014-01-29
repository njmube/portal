package com.magnabyte.test.documento;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.dao.documento.DocumentoDao;
import com.magnabyte.cfdi.portal.dao.documento.DocumentoSerieDao;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.documento.impl.DocumentoServiceImpl;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/serviceApplicationContext.xml", "classpath:/jdbcApplicationContext.xml"})
public class DocumentoServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoServiceTest.class);

	@Mock
	DocumentoSerieDao documentoSerieDaoMock;
	
	@Mock
	DocumentoDao documentoDaoMock;
	
	@Autowired
	DocumentoDao documentoDao;
	
	@Autowired
	DocumentoXmlService documentoXmlService;
	
	@InjectMocks
	@Autowired
	DocumentoService documentoService = new DocumentoServiceImpl();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
//	@Test
	public void insertDocumentoFolioTest() {
//		Documento documento = new DocumentoSucursal();
//		documento.setId(8);
//		Establecimiento establecimiento = new Establecimiento();
//		establecimiento.setId(13);
//		documento.setComprobante(new Comprobante());
//		documento.setEstablecimiento(establecimiento);
//		documento.setTipoDocumento(TipoDocumento.FACTURA);
//		documentoService.insertDocumentoFolio(documento);
//		logger.debug("sync serie {}", documento.getComprobante().getSerie());
//		logger.debug("sync folio {}", documento.getComprobante().getFolio());
//		logger.debug("fin");
//		
//		Mockito.verify(documentoSerieDaoMock, Mockito.atLeastOnce()).updateFolioSerie(documento);
//		Mockito.verify(documentoDaoMock, Mockito.atLeastOnce()).insertDocumentoFolio(documento);
	}
	
	@Test
	public void read() throws UnsupportedEncodingException {
		Documento documento = new Documento();
		documento.setId(375);
		documento = documentoDao.read(documento);
		Comprobante comprobante = documentoXmlService.convierteByteArrayAComprobante(documento.getXmlCfdi());
		documentoXmlService.convierteComprobanteAByteArray(comprobante, PortalUtils.encodingUTF8);
		
		logger.debug("fin");
	}
}
