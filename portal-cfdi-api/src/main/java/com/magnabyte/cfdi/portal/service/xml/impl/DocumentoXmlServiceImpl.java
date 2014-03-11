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

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.leyendasfisc.v32.LeyendasFiscales;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.cfdi.v32.CfdiV32Service;
import com.magnabyte.cfdi.portal.service.cfdi.v32.impl.CfdiV32ServiceImpl;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.service.xml.util.CustomNamespacePrefixMapper;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Clase que representa el servicio de documento xml
 */
@Service("documentoXmlService")
public class DocumentoXmlServiceImpl implements DocumentoXmlService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoXmlServiceImpl.class);

	@Autowired
	private SambaService sambaService;

	@Autowired
	@Qualifier("jaxb2Marshaller")
	private Unmarshaller unmarshaller;
	
	@Autowired
	@Qualifier("jaxb2Marshaller")
	private Marshaller marshaller;

	@Autowired
	@Qualifier("jaxb2MarshallerLeyFisc")
	private Unmarshaller unmarshallerLeyFisc;
	
	@Autowired
	@Qualifier("jaxb2MarshallerLeyFisc")
	private Marshaller marshallerLeyFisc;

	@Autowired
	private CfdiV32Service cfdiV32Service;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public DocumentoCorporativo convertXmlSapToCfdi(InputStream xmlSap) {
		DocumentoCorporativo documentoCorporativo = new DocumentoCorporativo();
		try {
			SAXBuilder builder = new SAXBuilder();
			
			Document documentoCFD = (Document) builder.build(xmlSap);
			Element documentoPrevio = documentoCFD.getRootElement().getChild("Comprobante");
			Element obsGralPrevio = documentoCFD.getRootElement().getChild("Observaciongral");
			Element trasladosPrevio = documentoCFD.getRootElement().getChild("Comprobante").getChild("Traslados");
			Element documento = (Element) documentoPrevio.clone();
			documentoCFD.setRootElement(documento);
			
			if (trasladosPrevio != null) {
				Element traslados = (Element) trasladosPrevio.clone();
				if (traslados.getChild("Traslado") != null)
					documentoCFD.getRootElement().getChild("Impuestos").addContent(traslados);
			}
			
			if (obsGralPrevio != null) {
				String nit = obsGralPrevio.getAttributeValue("NIT");
				if (nit != null) {
					documentoCorporativo.setNit(nit);
				}
			}
			
			validarTraslados(documento);
			agregarLeyendasFiscales(documento);
			revisaNodos(documento);
			if (documento != null) {
				Namespace cfdiNS = Namespace.getNamespace(CfdiV32ServiceImpl.CFDI_PREFIX
						, CfdiV32ServiceImpl.CFDI_URI);
				cambiaNameSpace(documento, cfdiNS);
				if (documento.getChild("Complemento", cfdiNS) != null 
						&& documento.getChild("Complemento", cfdiNS).getChild("LeyendasFiscales", cfdiNS) != null) {
					Namespace leyFiscNS = Namespace.getNamespace(CfdiV32ServiceImpl.LEYFISC_PREFIX, CfdiV32ServiceImpl.LEYFISC_URI);
					cambiaNameSpace(documento.getChild("Complemento", cfdiNS).getChild("LeyendasFiscales", cfdiNS), leyFiscNS);
				}
				documento.setAttribute("version", CfdiV32ServiceImpl.VERSION_CFDI);
				documento.setAttribute("tipoDeComprobante", documento.getAttributeValue("tipoDeComprobante").toLowerCase());
				documento.setAttribute("sello", CfdiV32ServiceImpl.SELLO_PREVIO);
				documento.setAttribute("noCertificado", CfdiV32ServiceImpl.NUMERO_CERTIFICADO_PREVIO);
				documento.setAttribute("certificado", CfdiV32ServiceImpl.CERTIFICADO_PREVIO);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				OutputStreamWriter oos = new OutputStreamWriter(baos, PortalUtils.encodingUTF16);
				XMLOutputter outputter = new XMLOutputter();
	            outputter.setFormat(Format.getPrettyFormat().setEncoding(PortalUtils.encodingUTF16));
	            outputter.output(documentoCFD, oos);
	            oos.flush();
	            oos.close();
				documentoCorporativo.setComprobante(convierteByteArrayAComprobante(baos.toByteArray()));
				TipoDocumento tipoDocumento = documentoCorporativo.getComprobante().getTipoDeComprobante().equalsIgnoreCase("ingreso") 
						? TipoDocumento.FACTURA : TipoDocumento.NOTA_CREDITO;
				documentoCorporativo.setTipoDocumento(tipoDocumento);
			}
		} catch (IOException e) {
			logger.error(messageSource.getMessage("documento.xml.error.sap", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.xml.error.sap", new Object[] {e.getMessage()}, null));
		} catch (JDOMException e) {
			logger.error(messageSource.getMessage("documento.xml.error.lectura.sap", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.xml.error.lectura.sap", new Object[] {e.getMessage()}, null));
		}  
		return documentoCorporativo;
	}

	private void validarTraslados(Element document) {
		if(document.getChild("Impuestos").getChild("Traslados") != null) {
			if(document.getChild("Impuestos").getChild("Traslados").getChild("Traslado") == null) {
				document.getChild("Impuestos").removeChild("Traslados");
			}
		}
	}
	
	private void agregarLeyendasFiscales(Element document) {
		if (document.getChild("Complemento") != null) {
			String leyendaFiscValue = document.getChild("Complemento")
					.getChild("AdditionalInformation").getChild("referenceIdentification")
					.getAttributeValue("referencia"); 
			Attribute textoLeyenda = new Attribute("textoLeyenda", leyendaFiscValue); 
			if (document.getChild("Complemento").removeChild("AdditionalInformation")) {
				Element leyendasFiscales = new Element("LeyendasFiscales");
				leyendasFiscales.setAttribute("version", "1.0");
				Element leyenda = new Element("Leyenda");
				leyenda.setAttribute(textoLeyenda);
				leyendasFiscales.addContent(leyenda);
				document.getChild("Complemento").addContent(leyendasFiscales);
			}
		}
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

	@Override
	public InputStream convierteComprobanteAStream(Comprobante comprobante) {
		return new ByteArrayInputStream(convierteComprobanteAByteArray(comprobante, PortalUtils.encodingUTF16));
	}
	
	@Override
	public byte[] convierteComprobanteAByteArray(Comprobante comprobante, String encoding) {
		Map<String, Object> marshallerProperties = new HashMap<String, Object>();
		marshallerProperties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
		//FIXME validar para que sirve
		marshallerProperties.put(javax.xml.bind.Marshaller.JAXB_FRAGMENT, true);
		marshallerProperties.put(javax.xml.bind.Marshaller.JAXB_SCHEMA_LOCATION, CfdiV32ServiceImpl.SCHEMA_LOCATION);
		if (hasLeyendasFiscales(comprobante)) {
			marshallerProperties.put("com.sun.xml.bind.namespacePrefixMapper", new CustomNamespacePrefixMapper(CfdiV32ServiceImpl.PREFIXES_LEYFISC));
		} else {
			marshallerProperties.put("com.sun.xml.bind.namespacePrefixMapper", new CustomNamespacePrefixMapper(CfdiV32ServiceImpl.PREFIXES));
		}
		marshallerProperties.put("jaxb.encoding", encoding);
		((Jaxb2Marshaller) marshaller).setMarshallerProperties(marshallerProperties);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter oos;
		try {
			oos = new OutputStreamWriter(baos, encoding);
			marshaller.marshal(comprobante, new StreamResult(oos));
			marshaller.marshal(comprobante, new StreamResult(System.out));
	        oos.flush();
	        oos.close();
		} catch (UnsupportedEncodingException e) {
			logger.error(messageSource.getMessage("documento.error.codificacion", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.error.codificacion", 
					new Object[] {e.getMessage()}, null));
		} catch (IOException e) {
			logger.error(messageSource.getMessage("documento.xml.comprobante.error.conversion.byte", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.xml.comprobante.error.conversion.byte", 
					new Object[] {e.getMessage()}, null));
		}
		return baos.toByteArray();
	}

	private boolean hasLeyendasFiscales(Comprobante comprobante) {
		if (comprobante.getComplemento() != null) {
			for (Object complemento : comprobante.getComplemento().getAny()) {
				try {
					Object complementoLeyFisc = unmarshallerLeyFisc.unmarshal(new DOMSource((Node) complemento));
					if (complementoLeyFisc instanceof LeyendasFiscales) {
						return true;
					}
				} catch (XmlMappingException e) {
					continue;
				} catch (IOException e) {
					continue;
				}
			}
		}
		return false;
	}

	@Override
	public Comprobante convierteByteArrayAComprobante(byte[] xmlCfdi) {
		try {
			return (Comprobante) unmarshaller.unmarshal(new StreamSource(new ByteArrayInputStream(xmlCfdi)));
		} catch (IOException e) {
			logger.error(messageSource.getMessage("documento.xml.comprobante.error.conversion", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.xml.comprobante.error.conversion", 
					new Object[] {e.getMessage()}, null));
		}
	}
	
	@Override
	public String obtenerNumCertificado(byte[] xmlCfdi) {
		SAXBuilder builder = new SAXBuilder();
		String noCertificadoSat = null;
		try {
			Document documentoCFDI = (Document) builder.build(new ByteArrayInputStream(xmlCfdi));
			Namespace nsCfdi = Namespace.getNamespace(CfdiV32ServiceImpl.CFDI_PREFIX, 
					CfdiV32ServiceImpl.CFDI_URI);
			Namespace nsTfd = Namespace.getNamespace(CfdiV32ServiceImpl.TFD_PREFIX, 
					CfdiV32ServiceImpl.TFD_URI);
			noCertificadoSat = documentoCFDI.getRootElement().getChild("Complemento", nsCfdi)
					.getChild("TimbreFiscalDigital", nsTfd).getAttributeValue("noCertificadoSAT");
		} catch (JDOMException e) {
			logger.error(messageSource.getMessage("documento.xml.error.numcertificado.sat", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.xml.error.numcertificado.sat", 
					new Object[] {e.getMessage()}, null));
		} catch (IOException e) {
			logger.error(messageSource.getMessage("documento.xml.error.numcertificado.sat", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.xml.error.numcertificado.sat", 
					new Object[] {e.getMessage()}, null));
		}
		
		return noCertificadoSat;
	}
	
}

	