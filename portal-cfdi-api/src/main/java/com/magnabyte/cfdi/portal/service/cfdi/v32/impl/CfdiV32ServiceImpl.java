package com.magnabyte.cfdi.portal.service.cfdi.v32.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.ssl.PKCS8Key;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.cfdi.v32.CfdiV32Service;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;

@Service("cfdiV32Service")
public class CfdiV32ServiceImpl implements CfdiV32Service, ResourceLoaderAware {

	private static final Logger logger = LoggerFactory.getLogger(CfdiV32ServiceImpl.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	private ResourceLoader resourceLoader;
	
	public static String VERSION_CFDI;
	
	public static String NUMERO_CERTIFICADO_PREVIO;
	
	public static String SELLO_PREVIO;
	
	public static String CERTIFICADO_PREVIO;
	
	public static String SCHEMA_LOCATION;
	
	public static String CFDI_URI;

	public static String CFDI_PREFIX;

	public static String TFD_URI;

	public static String TFD_PREFIX;
	
	public static String LEYFISC_URI;
	
	public static String LEYFISC_PREFIX;
	
	public static Map<String, String> PREFIXES;
	
	public static Map<String, String> PREFIXES_LEYFISC;
	
	private static String[] XSD = {
		"classpath:/xsd/cfdv32.xsd", 
		"classpath:/xsd/tfdv32.xsd",
		"classpath:/xsd/leyendasFisc.xsd"
	};
	
	public static void initStaticValues(String versionCfdi, String numeroCertificadoPrevio, String selloPrevio,
			String certificadoPrevio, String schemaLocation, String cfdiUri, String cfdiPrefix, String tfdUri, String tfdPrefix,
			String leyFiscUri, String leyFiscPrefix) {
		VERSION_CFDI = versionCfdi;
		NUMERO_CERTIFICADO_PREVIO = numeroCertificadoPrevio;
		SELLO_PREVIO = selloPrevio;
		CERTIFICADO_PREVIO = certificadoPrevio;
		SCHEMA_LOCATION = schemaLocation;
		CFDI_URI = cfdiUri;
		CFDI_PREFIX = cfdiPrefix;
		TFD_URI = tfdUri;
		TFD_PREFIX = tfdPrefix;
		LEYFISC_URI = leyFiscUri;
		LEYFISC_PREFIX = leyFiscPrefix;
		PREFIXES = new HashMap<String, String>();
		PREFIXES.put(CFDI_URI, CFDI_PREFIX);
		PREFIXES.put(TFD_URI, TFD_PREFIX);
		PREFIXES_LEYFISC = new HashMap<String, String>();
		PREFIXES_LEYFISC.putAll(PREFIXES);
		PREFIXES_LEYFISC.put(LEYFISC_URI, LEYFISC_PREFIX);
	}
	
	@Override
	public boolean sellarComprobante(Comprobante comprobante,
			CertificadoDigital certificado) {
		logger.debug("en sellar Documento");
		String cadena = obtenerCadena(comprobante);
		String sello = obtenerSelloDigital(cadena, certificado);
		logger.debug("SELLO: {}", sello);
		logger.debug("CADENA: {}", cadena);
		if(validSelloDigital(sello, cadena, comprobante, certificado)) {
			comprobante.setSello(sello);
			return true;
		} else {
			logger.error(messageSource.getMessage("comprobante.sello.invalido", null, null));
			throw new PortalException(messageSource.getMessage("comprobante.sello.invalido", null, null));
		}
	}

	private boolean validSelloDigital(String sello, String cadena,
			Comprobante comprobante, CertificadoDigital certificado) {
		CertificateFactory certFactory;
		try {
			logger.debug("validando sello...");
			String certificateFileLocation = certificado.getRutaCertificado() + File.separator + certificado.getNombreCertificado();
			certFactory = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(resourceLoader.getResource(certificateFileLocation).getInputStream());
			certificate.checkValidity();
			PublicKey publicKey = certificate.getPublicKey();
			comprobante.setNoCertificado(new String(certificate.getSerialNumber().toByteArray()));
			comprobante.setCertificado(new String(Base64.encode(certificate.getEncoded())));
			
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(publicKey);
			signature.update(cadena.getBytes(PortalUtils.encodingUTF8));
			
			return signature.verify(Base64.decode(sello));
		} catch (CertificateException e) {
			logger.error(messageSource.getMessage("comprobante.error.certificado", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.certificado", new Object[] {e}, null));
		} catch (IOException e) {
			logger.error(messageSource.getMessage("comprobante.error.carga.certificado", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.carga.certificado", new Object[] {e}, null));
		} catch (NoSuchAlgorithmException e) {
			logger.error(messageSource.getMessage("comprobante.error.validar.sello", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.validar.sello", new Object[] {e}, null));
		} catch (InvalidKeyException e) {
			logger.error(messageSource.getMessage("comprobante.error.certificado.invalido", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.certificado.invalido", new Object[] {e}, null));
		} catch (SignatureException e) {
			logger.error(messageSource.getMessage("comprobante.error.certificado.invalido", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.certificado.invalido", new Object[] {e}, null));
		}
	}

	private String obtenerSelloDigital(String cadena,
			CertificadoDigital certificado) {
		try {
			logger.debug("en obtener sello digital");
			String keyFileLocation = certificado.getRutaKey() + File.separator + certificado.getNombreKey();
			InputStream keyStream = resourceLoader.getResource(keyFileLocation).getInputStream();
			String password = new String(Base64.decode(certificado.getPassword())).trim();
			logger.debug("password {}", password);
			PKCS8Key key = new PKCS8Key(keyStream, password.toCharArray());
			PrivateKey privateKey = key.getPrivateKey();
			
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(privateKey);
			signature.update(cadena.getBytes(PortalUtils.encodingUTF8));
			byte[] firma = signature.sign();
			logger.debug("regresando sello");
			return new String(Base64.encode(firma));
		} catch (IOException e) {
			logger.error(messageSource.getMessage("comprobante.error.carga.sello.llave", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.carga.sello.llave", new Object[] {e}, null));
		} catch (GeneralSecurityException e) {
			logger.error(messageSource.getMessage("comprobante.error.carga.sello", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.carga.sello", new Object[] {e}, null));
		}
	}

	private String obtenerCadena(Comprobante comprobante) {
		try {
			logger.debug("en obtener Cadena");
			Source xmlSource = new StreamSource(documentoXmlService.convierteComprobanteAStream(comprobante));
			Source xsltSource = new StreamSource(resourceLoader.getResource("WEB-INF/xslt/cadenaoriginal_3_2.xslt").getInputStream());
			StringWriter writer = new StringWriter();
			Result outputTarget = new StreamResult(writer);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			tFactory.setURIResolver(new URIResolver() {
				
				@Override
				public Source resolve(String href, String base) throws TransformerException {
					try {
						return new StreamSource(resourceLoader.getResource(href).getInputStream());
					} catch (IOException e) {
						logger.error(messageSource.getMessage("comprobante.error.cadena.xslt", new Object[] {e}, null));
						throw new PortalException(messageSource.getMessage("comprobante.error.cadena.xslt", new Object[] {e}, null));
					}
				}
			});
			Transformer transformer = tFactory.newTransformer(xsltSource);
			transformer.transform(xmlSource, outputTarget);
			logger.debug("regresando Cadena");
			return writer.toString();
		} catch (TransformerConfigurationException e) {
			logger.error(messageSource.getMessage("comprobante.error.cadena.original", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.cadena.original", new Object[] {e}, null));
		} catch (IOException e) {
			logger.error(messageSource.getMessage("comprobante.error.cadena.xslt", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.cadena.xslt", new Object[] {e}, null));
		} catch (TransformerException e) {
			logger.error(messageSource.getMessage("comprobante.error.cadena.original", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.cadena.original", new Object[] {e}, null));
		}
	}
	
	@Override
	public boolean isValidComprobanteXml(Comprobante comprobante) {
		try {
			InputStream comprobanteStream = documentoXmlService.convierteComprobanteAStream(comprobante);
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(getXsdSchemas());
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(comprobanteStream));
			return true;
		} catch (Exception e) {
			logger.error(messageSource.getMessage("documento.xml.comprobante.invalido", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.xml.comprobante.invalido", new Object[] {e.getMessage()}, null));
		}
	}
	
	private Source[] getXsdSchemas() throws IOException {
		Source[] schemas = new Source[XSD.length];
		for (int i = 0; i < XSD.length; i++) {
			schemas[i] = new StreamSource(resourceLoader.getResource(XSD[i]).getInputStream());
		}
		return schemas;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
