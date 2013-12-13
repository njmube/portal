package com.magnabyte.cfdi.portal.service.xml.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

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
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.xml.XmlConverterService;

@Service("xmlConverterService")
public class XmlConverterServiceImpl implements XmlConverterService {

	private static final Logger logger = LoggerFactory.getLogger(XmlConverterServiceImpl.class);

	@Autowired
	private SambaService sambaService;

	@Autowired
	private Unmarshaller unmarshaller;

	@Override
	public Comprobante convertXmlSapToCfdi(String rutaRepositorio, String fileName) {
		Comprobante comprobante = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			
			Document documentoCFD;
			try {
				documentoCFD = (Document) builder.build(sambaService.getFile(rutaRepositorio, fileName));
				Element documentoPrevio = documentoCFD.getRootElement().getChild("Comprobante");
				Element documento = (Element) documentoPrevio.clone();
				documentoCFD.setRootElement(documento);
				revisaNodos(documento);
				if (documento != null) {
					documento.setNamespace(Namespace.NO_NAMESPACE);
					documento.setNamespace(Namespace.getNamespace("cfdi", "http://www.sat.gob.mx/cfd/3"));
					cambiaNameSpace(documento, Namespace.getNamespace("cfdi", "http://www.sat.gob.mx/cfd/3"));
					documento.setAttribute("schemaLocation", "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd",
							Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance"));
					documento.setAttribute("version", "3.2");
					documento.setAttribute("tipoDeComprobante", documento.getAttributeValue("tipoDeComprobante").toString().toLowerCase());
					documento.setAttribute("sello", "");
					documento.setAttribute("noCertificado", "xxxxxxxxxxxxxxxxxxxx");
					documento.setAttribute("certificado", "");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					OutputStreamWriter oos= new OutputStreamWriter(baos, "UTF-8");
					XMLOutputter outputter = new XMLOutputter();
		            outputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
		            outputter.output(documentoCFD,oos);
		            oos.flush();
		            oos.close();
					System.out.println(baos.toString());
					comprobante = (Comprobante) unmarshaller.unmarshal(new StreamSource(new ByteArrayInputStream(baos.toByteArray())));
				}
			} catch (JDOMException e) {
				logger.error("Error el leer el documento Sap");
				e.printStackTrace();
			}
		
		} catch (XmlMappingException e) {
			logger.error("Error al convertir el objeto a xml");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Error al recuperar el documento");
		}
		return comprobante;
	}

	private void cambiaNameSpace(Element element, Namespace namespace) {
		Iterator iterador = element.getChildren().iterator();
		while (iterador.hasNext()) {
			Element elementoHijo = (Element) iterador.next();
			elementoHijo.setNamespace(Namespace.NO_NAMESPACE);
			elementoHijo.setNamespace(namespace);
			cambiaNameSpace(elementoHijo, namespace);
		}
	}

	private void revisaNodos(Element element) {
		element = eliminaAtributosVacios(element);
		List list = element.getChildren();
		for (int contador = 0; contador < list.size(); contador ++) {
			Element childElement = (Element) list.get(contador);
			revisaNodos(childElement);
		}
	}

	private Element eliminaAtributosVacios(Element element) {
		List list = element.getAttributes();
		for (int contador = 0; contador < list.size(); contador ++) {
			Attribute attribute = (Attribute) list.get(contador);
			if (attribute.getValue() == null || attribute.getValue() == "") {
				element.removeAttribute(attribute);
			}
		}
		return element;
	}
}
