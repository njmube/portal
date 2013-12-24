package com.magnabyte.cfdi.portal.service.documento.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
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

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import mx.gob.sat.cfd._3.Comprobante;
import mx.gob.sat.cfd._3.Comprobante.Conceptos;
import mx.gob.sat.cfd._3.Comprobante.Conceptos.Concepto;
import mx.gob.sat.cfd._3.Comprobante.Impuestos;
import mx.gob.sat.cfd._3.Comprobante.Receptor;
import mx.gob.sat.cfd._3.TUbicacion;

import org.apache.commons.ssl.PKCS8Key;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.dao.emisor.EmisorDao;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.InformacionPago;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.Partida;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.PartidaDescuento;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;

@Service("documentoService")
public class DocumentoServiceImpl implements DocumentoService, ResourceLoaderAware {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoServiceImpl.class);
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	@Autowired
	private EmisorDao emisorDao;
	
	private ResourceLoader resourceLoader;
	
	@Override
	public boolean sellarComprobante(Comprobante comprobante) {
		logger.debug("en sellar Documento");
		//QUITAR
		comprobante.getEmisor().setRfc("AAA010101AAA");
		//
		String cadena = obtenerCadena(comprobante);
		String sello = obtenerSelloDigital(cadena);
		logger.debug("sello {} y cadena {}", sello, cadena);
		if(validSelloDigital(sello, cadena, comprobante)) {
			comprobante.setSello(sello);
			return true;
		}
		return false;
	}

	private boolean validSelloDigital(String sello, String cadena, Comprobante comprobante) {
		CertificateFactory certFactory;
		try {
			logger.debug("validando sello");
			certFactory = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(resourceLoader.getResource("classpath:/aaa010101aaa__csd_01.cer").getInputStream());
			certificate.checkValidity();
			PublicKey publicKey = certificate.getPublicKey();
			comprobante.setNoCertificado(new String(certificate.getSerialNumber().toByteArray()));
			comprobante.setCertificado(new String(Base64.encode(certificate.getEncoded())));
			
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(publicKey);
			signature.update(cadena.getBytes("UTF-8"));
			
			return signature.verify(Base64.decode(sello));
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String obtenerSelloDigital(String cadena) {
		try {
			logger.debug("en obtener sello digital");
			InputStream keyStream = resourceLoader.getResource("classpath:/aaa010101aaa__csd_01.key").getInputStream();
			PKCS8Key key = new PKCS8Key(keyStream, "12345678a".toCharArray());
			PrivateKey privateKey = key.getPrivateKey();
			
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(privateKey);
			signature.update(cadena.getBytes("UTF-8"));
			byte[] firma = signature.sign();
			logger.debug("regresando sello");
			return new String(Base64.encode(firma));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String obtenerCadena(Comprobante comprobante) {
		try {
			logger.debug("en obtener Cadena");
			Source xmlSource = new StreamSource(documentoXmlService.convierteComprobanteAStream(comprobante));
			Source xsltSource = new StreamSource(resourceLoader.getResource("classpath:/cadenaoriginal_3_2.xslt").getInputStream());
			StringWriter writer = new StringWriter();
			Result outputTarget = new StreamResult(writer);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(xsltSource);
			transformer.transform(xmlSource, outputTarget);
			logger.debug("regresando Cadena");
			return writer.toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public Comprobante obtenerComprobantePor(Cliente cliente, Ticket ticket,
			Integer domicilioFiscal, Establecimiento establecimiento) {
		
		Comprobante comprobante = new Comprobante();
		Receptor receptor = new Receptor();
		Conceptos conceptos = new Conceptos();
		TUbicacion tUbicacion = new TUbicacion();
		DomicilioCliente domicilioCte = new DomicilioCliente();
		domicilioCte.setId(domicilioFiscal);
		
		BigDecimal IVA = new BigDecimal(1.16);
		
		for(DomicilioCliente domicilio : cliente.getDomicilios()) {
			if(domicilioCte.equals(domicilio)){
				domicilioCte = domicilio;
				break;
			}
		}
		
		tUbicacion.setCalle(domicilioCte.getCalle());
		tUbicacion.setNoExterior(domicilioCte.getNoExterior());
		tUbicacion.setNoInterior(domicilioCte.getNoInterior());
		tUbicacion.setPais(domicilioCte.getEstado().getPais().getNombre());
		tUbicacion.setEstado(domicilioCte.getEstado().getNombre());
		tUbicacion.setMunicipio(domicilioCte.getMunicipio());
		tUbicacion.setColonia(domicilioCte.getColonia());
		tUbicacion.setCodigoPostal(domicilioCte.getCodigoPostal());
		tUbicacion.setReferencia(domicilioCte.getReferencia());
		tUbicacion.setLocalidad(domicilioCte.getLocalidad());
		
		BigDecimal subTotal = new BigDecimal(0);
		for(Partida partida : ticket.getTransaccion().getPartidas()) {
			Concepto concepto = new Concepto();
			concepto.setCantidad(partida.getCantidad());
			concepto.setDescripcion(partida.getArticulo().getDescripcion());
			concepto.setUnidad(partida.getArticulo().getUnidad());
			concepto.setImporte(partida.getPrecioTotal().divide(IVA, 2, BigDecimal.ROUND_HALF_UP));
			concepto.setValorUnitario(partida.getPrecioUnitario().divide(IVA, 2, BigDecimal.ROUND_HALF_UP));
			if (!partida.getArticulo().getTipoCategoria().equals("PROMOCIONES")) {
				subTotal = subTotal.add(concepto.getImporte());
			}
			conceptos.getConcepto().add(concepto);
		}
		
		comprobante.setSubTotal(subTotal);
		Impuestos impuesto = new Impuestos();
		impuesto.setTotalImpuestosTrasladados(ticket.getTransaccion().getTransaccionTotal().getTotalVenta().subtract(subTotal));
		comprobante.setImpuestos(impuesto);
		comprobante.setTotal(ticket.getTransaccion().getTransaccionTotal().getTotalVenta());

		BigDecimal descuentoTotal = new BigDecimal(0);
		for(PartidaDescuento descuento : ticket.getTransaccion().getPartidasDescuentos()) {
			descuentoTotal = descuentoTotal.add(descuento.getDescuentoTotal());
		}
		comprobante.setDescuento(descuentoTotal.setScale(2));
		
		for(InformacionPago infoPago : ticket.getTransaccion().getInformacionPago()) {
			comprobante.setNumCtaPago(infoPago.getNumeroCuenta());
			comprobante.setMetodoDePago(infoPago.getPago().getMetodoPago().toUpperCase());
			comprobante.setMoneda(infoPago.getPago().getMoneda());
		}
		
		receptor.setRfc(cliente.getRfc());
		receptor.setNombre(cliente.getNombre());
		receptor.setDomicilio(tUbicacion);
		
		EmpresaEmisor empresaEisor = emisorDao.read(establecimiento.getEmpresaEmisor());
		
		comprobante.setEmisor(empresaEisor.getEmisor());
		comprobante.setReceptor(receptor);
		comprobante.setConceptos(conceptos);
		comprobante.setTipoDeComprobante("ingreso");
		comprobante.setTipoCambio("1");
		comprobante.setCondicionesDePago("PAGO DE CONTADO");
		comprobante.setFormaDePago("PAGO EN UNA SOLA EXHIBICION");
		
		return comprobante;
	}

}
