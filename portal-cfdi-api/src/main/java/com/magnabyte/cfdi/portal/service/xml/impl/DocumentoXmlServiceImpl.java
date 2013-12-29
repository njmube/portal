package com.magnabyte.cfdi.portal.service.xml.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import mx.gob.sat.cfd._3.Comprobante;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.service.xml.util.CustomNamespacePrefixMapper;

@Service("documentoXmlService")
public class DocumentoXmlServiceImpl implements DocumentoXmlService, ResourceLoaderAware {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoXmlServiceImpl.class);

	@Autowired
	private SambaService sambaService;

	@Autowired
	private Unmarshaller unmarshaller;
	
	@Autowired
	private Marshaller marshaller;

	@Autowired
	private CustomNamespacePrefixMapper customNamespacePrefixMapper;
	
	private ResourceLoader resourceLoader;
	
	@Override
	public Comprobante convertXmlSapToCfdi(InputStream xmlSap) {
		Comprobante comprobante = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			
			Document documentoCFD = (Document) builder.build(xmlSap);
			Element documentoPrevio = documentoCFD.getRootElement().getChild("Comprobante");
			Element trasladosPrevio = documentoCFD.getRootElement().getChild("Comprobante").getChild("Traslados");
			Element documento = (Element) documentoPrevio.clone();
			documentoCFD.setRootElement(documento);
			
			if (trasladosPrevio != null) {
				Element traslados = (Element) trasladosPrevio.clone();
				documentoCFD.getRootElement().getChild("Impuestos").addContent(traslados);
			}
			
			revisaNodos(documento);
			if (documento != null) {
				cambiaNameSpace(documento, Namespace.getNamespace(CustomNamespacePrefixMapper.CFDI_PREFIX, CustomNamespacePrefixMapper.CFDI_URI));
				documento.setAttribute("version", "3.2");
				documento.setAttribute("tipoDeComprobante", documento.getAttributeValue("tipoDeComprobante").toLowerCase());
				documento.setAttribute("sello", "");
				documento.setAttribute("noCertificado", "xxxxxxxxxxxxxxxxxxxx");
				documento.setAttribute("certificado", "");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				OutputStreamWriter oos = new OutputStreamWriter(baos, "UTF-8");
				XMLOutputter outputter = new XMLOutputter();
	            outputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
	            outputter.output(documentoCFD, oos);
	            oos.flush();
	            oos.close();
				comprobante = convierteByteArrayAComprobante(baos.toByteArray());
				logger.debug("XML valido: " + validaComprobanteXml(comprobante));
			}
		} catch (IOException e) {
			logger.error("Error al convertir el archivo SAP a CFDI: ", e);
			throw new PortalException("Error al convertir el archivo SAP a CFDI: " + e.getMessage());
		} catch (JDOMException e) {
			logger.error("Error al leer el documento SAP: ", e);
			throw new PortalException("Error al leer el documento SAP: " + e.getMessage());
		}  
		return comprobante;
	}

	@SuppressWarnings("unchecked")
	private void cambiaNameSpace(Element parentElement, Namespace namespace) {
		parentElement.setNamespace(namespace);
		List<Element> list = parentElement.getChildren();
		for (Element element : list) {
			element.setNamespace(namespace);
			cambiaNameSpace(element, namespace);
		}
	}

	@SuppressWarnings("unchecked")
	private void revisaNodos(Element element) {
		element = eliminaAtributosVacios(element);
		List<Element> list = element.getChildren();
		for (int contador = 0; contador < list.size(); contador ++) {
			revisaNodos(list.get(contador));
		}
	}

	@SuppressWarnings("unchecked")
	private Element eliminaAtributosVacios(Element element) {
		List<Attribute> list = element.getAttributes();
		for (int contador = 0; contador < list.size(); contador ++) {
			Attribute attribute = list.get(contador);
			if (attribute.getValue() == null || attribute.getValue().isEmpty()) {
				element.removeAttribute(attribute);
				contador --;
			}
		}
		return element;
	}

	public boolean validaComprobanteXml(Comprobante comprobante) {
		try {
			InputStream comprobanteStream = convierteComprobanteAStream(comprobante);
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new StreamSource(resourceLoader.getResource("classpath:/cfdv32.xsd").getInputStream()));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(comprobanteStream));
			return true;
		} catch (Exception e) {
			logger.error("Error al validar el documento: {}", e.getMessage());
			return false;
		}
	}
	
	@Override
	public InputStream convierteComprobanteAStream(Comprobante comprobante) {
		return new ByteArrayInputStream(convierteComprobanteAByteArray(comprobante));
	}
	
	@Override
	public byte[] convierteComprobanteAByteArray(Comprobante comprobante) {
		Map<String, Object> marshallerProperties = new HashMap<String, Object>();
		marshallerProperties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshallerProperties.put(javax.xml.bind.Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd");
		marshallerProperties.put("com.sun.xml.bind.namespacePrefixMapper", customNamespacePrefixMapper);
		((Jaxb2Marshaller) marshaller).setMarshallerProperties(marshallerProperties);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter oos;
		try {
			oos = new OutputStreamWriter(baos, "UTF-8");
			marshaller.marshal(comprobante, new StreamResult(oos));
			marshaller.marshal(comprobante, new StreamResult(System.out));
	        oos.flush();
	        oos.close();
		} catch (UnsupportedEncodingException e) {
			logger.error("La codificacion no es soportada: ", e);
			throw new PortalException("La codificacion no es soportada: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Error al convertir el Comprobante a Arreglo de Bytes: ", e);
			throw new PortalException("Error al convertir el Comprobante a Arreglo de Bytes: " + e.getMessage());
		}
		return baos.toByteArray();
	}

	@Override
	public Comprobante convierteByteArrayAComprobante(byte[] xmlCfdi) {
		try {
			return (Comprobante) unmarshaller.unmarshal(new StreamSource(new ByteArrayInputStream(xmlCfdi)));
		} catch (IOException e) {
			logger.error("Error al convertir el Arreglo de Bytes a Comprobante: ", e);
			throw new PortalException("Error al convertir el Arreglo de Bytes a Comprobante: " + e.getMessage());
		}
	}
	
	@Override
	public String obtenerNumCertificado(byte[] xmlCfdi) {
		SAXBuilder builder = new SAXBuilder();
		String noCertificadoSat = null;
		try {
			Document documentoCFDI = (Document) builder.build(new ByteArrayInputStream(xmlCfdi));
			Namespace nsCfdi = Namespace.getNamespace(CustomNamespacePrefixMapper.CFDI_PREFIX, CustomNamespacePrefixMapper.CFDI_URI);
			Namespace nsTfd = Namespace.getNamespace(CustomNamespacePrefixMapper.TFD_PREFIX, CustomNamespacePrefixMapper.TFD_URI);
			noCertificadoSat = documentoCFDI.getRootElement().getChild("Complemento", nsCfdi).getChild("TimbreFiscalDigital", nsTfd).getAttributeValue("noCertificadoSAT");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return noCertificadoSat;
	}
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}

	