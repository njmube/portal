package com.magnabyte.test.xml;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.service.xml.impl.DocumentoXmlServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/serviceApplicationContextTest.xml")
public class DocumentoXmlServiceTest implements ResourceLoaderAware {

	public static final Logger logger = 
			LoggerFactory.getLogger(DocumentoXmlServiceTest.class);
	
	DocumentoXmlService documentoXmlService = new DocumentoXmlServiceImpl();
	
	ResourceLoader resourceLoader;
	
	@Test
	public void recuperarNoCertificado() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		byte [] buffer = new byte [1024 * 8];
		int val = 0;
		InputStream is = resourceLoader.getResource("file:/home/omkbron/FACTURA_L_2.xml").getInputStream();
		while ((val = is.read(buffer)) != -1) {
			bos.write(buffer, 0, val);
		}
		bos.flush();
		bos.close();
		
		Assert.assertEquals("00001000000300171291", documentoXmlService.obtenerNumCertificado(baos.toByteArray()));
	}
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}
