package com.magnabyte.cfdi.portal.service.documento.impl;

import java.io.File;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.ssl.PKCS8Key;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Conceptos;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Conceptos.Concepto;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Impuestos;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Impuestos.Traslados;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Impuestos.Traslados.Traslado;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Receptor;
import com.magnabyte.cfdi.portal.model.cfdi.v32.TUbicacion;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusDomicilioCliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.InformacionPago;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.Partida;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.PartidaDescuento;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;
import com.magnabyte.cfdi.portal.service.documento.ComprobanteService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.emisor.EmisorService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.service.xml.util.CfdiConfiguration;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 * 
 * Clase que representa el servicio de comprobante
 */
@Service("comprobanteService")
public class ComprobanteServiceImpl implements ComprobanteService, ResourceLoaderAware {

	private static final Logger logger = LoggerFactory.getLogger(ComprobanteServiceImpl.class);
	
	private ResourceLoader resourceLoader;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@Autowired
	private CfdiConfiguration cfdiConfiguration;

	@Autowired
	private EmisorService emisorService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private DomicilioClienteService domicilioClienteService;
	
	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
	
	@Value("${cfdi.comprobante.tipo.cambio}")
	private String tipoCambio;
	
	@Value("${cfdi.comprobante.condiciones.pago}")
	private String condicionesPago;
	
	@Value("${cfdi.comprobante.forma.pago}")
	private String formaPago;
	
	@Value("${ticket.categoria.sinprecio}")
	private String categoriaSinPrecio;
	
	@Value("${ticket.unidad.default}")
	private String unidadDefault;
	
	@Value("${cfdi.comprobante.tasa.iva}")
	private String ivaTasa;
	
	@Value("${cfdi.comprobante.descripcion.iva}")
	private String ivaDescripcion;
	
	private BigDecimal IVA;
	
	private BigDecimal IVA_DIVISION;
	
	private BigDecimal IVA_MULTIPLICACION;
	
	@PostConstruct
	public void init() {
		IVA = new BigDecimal(ivaTasa);
		
		IVA_DIVISION = IVA
			.divide(new BigDecimal(100)).add(new BigDecimal(1)).setScale(4, BigDecimal.ROUND_HALF_UP);
		IVA_MULTIPLICACION = IVA_DIVISION
			.subtract(new BigDecimal(1)).setScale(4, BigDecimal.ROUND_HALF_UP);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Comprobante obtenerComprobantePor(Cliente cliente, Ticket ticket,
		Integer idDomicilioFiscal, Establecimiento establecimiento, TipoDocumento tipoDocumento) {
		Comprobante comprobante = new Comprobante();
		
		if (ticket != null && ticket.getTransaccion().getTransaccionHeader().getIdTicket() != null &&
				ticket.getTransaccion().getTransaccionHeader().getIdCaja() != null &&
				ticket.getTransaccion().getTransaccionHeader().getIdSucursal() != null &&
				ticket.getTransaccion().getTransaccionHeader().getFechaHora() != null) {
		
			
			inicializaComprobante(comprobante);
			
			for(InformacionPago infoPago : ticket.getTransaccion().getInformacionPago()) {
				comprobante.setNumCtaPago(infoPago.getNumeroCuenta());
				comprobante.setMetodoDePago(infoPago.getPago().getMetodoPago().toUpperCase());
				comprobante.setMoneda(infoPago.getPago().getMoneda());
				break;
			}
			
			formatTicketDate(ticket);
			comprobante.setEmisor(emisorService.getEmisorPorEstablecimiento(establecimiento));
			comprobante.setReceptor(createReceptor(cliente, idDomicilioFiscal));
			getDetalleFromTicket(ticket, comprobante);
			//FIXME validar que fecha utilizara el comprobante
			createFechaDocumento(comprobante);
			comprobante.setLugarExpedicion(comprobante.getEmisor().getExpedidoEn().getLocalidad() 
					+ ", " + comprobante.getEmisor().getExpedidoEn().getEstado());
			
			comprobante.setTipoDeComprobante(tipoDocumento.getNombreComprobante());
			comprobante.setTipoCambio(tipoCambio);
			comprobante.setCondicionesDePago(condicionesPago);
			comprobante.setFormaDePago(formaPago);
		} else {
			throw new PortalException("El ticket no puedo ser nulo.");
		}
		
		return comprobante;
	}
	
	@Override
	public Comprobante obtenerComprobantePor(Documento documento, Cliente cliente,
			Integer idDomicilioFiscal, Establecimiento establecimiento) {
		Comprobante comprobante = new Comprobante();
		inicializaComprobante(comprobante);
		
		comprobante.setNumCtaPago(documento.getComprobante().getNumCtaPago());
		comprobante.setMetodoDePago(documento.getComprobante().getMetodoDePago());
		comprobante.setMoneda(documento.getComprobante().getMoneda());
		
		comprobante.setEmisor(emisorService.getEmisorPorEstablecimiento(establecimiento));
		comprobante.setReceptor(createReceptor(cliente, idDomicilioFiscal));
		
		
		comprobante.setConceptos(documento.getComprobante().getConceptos());
		comprobante.setDescuento(documento.getComprobante().getDescuento());

		comprobante.setSubTotal(documento.getComprobante().getSubTotal());
		comprobante.setImpuestos(documento.getComprobante().getImpuestos());
		comprobante.setTotal(documento.getComprobante().getTotal());
		
		//FIXME validar que fecha utilizara el comprobante
		createFechaDocumento(comprobante);
		comprobante.setLugarExpedicion(comprobante.getEmisor().getExpedidoEn().getLocalidad() 
				+ ", " + comprobante.getEmisor().getExpedidoEn().getEstado());
		
		comprobante.setTipoDeComprobante(documento.getComprobante().getTipoDeComprobante());
		comprobante.setTipoCambio(tipoCambio);
		comprobante.setCondicionesDePago(condicionesPago);
		comprobante.setFormaDePago(formaPago);
		return comprobante;
	}
	
	private void inicializaComprobante(Comprobante comprobante) {
		comprobante.setVersion(cfdiConfiguration.getVersionCfdi());
		comprobante.setSello(cfdiConfiguration.getSelloPrevio());
		comprobante.setNoCertificado(cfdiConfiguration.getNumeroCertificadoPrevio());
		comprobante.setCertificado(cfdiConfiguration.getCertificadoPrevio());
	}
	
	private void formatTicketDate(Ticket ticket) {
		try {
			ticket.getTransaccion().getTransaccionHeader()
				.setFechaHora(FechasUtils.specificStringFormatDate(ticket.getTransaccion().getTransaccionHeader().getFechaHora(), 
						FechasUtils.formatyyyyMMddHHmmss, FechasUtils.formatddMMyyyyHHmmssSlash));
		} catch (PortalException e) {
			FechasUtils.parseStringToDate(ticket.getTransaccion().getTransaccionHeader().getFechaHora(), FechasUtils.formatddMMyyyyHHmmssSlash);
		} 
	}
	
	private Receptor createReceptor(Cliente cliente, Integer idDomicilioFiscal) {
		Receptor receptor = new Receptor();
		TUbicacion tUbicacion = new TUbicacion();
		DomicilioCliente domicilioCte = new DomicilioCliente();
		domicilioCte.setId(idDomicilioFiscal);
		
		for(DomicilioCliente domicilio : cliente.getDomicilios()) {
			if(domicilioCte.equals(domicilio)){
				domicilioCte = domicilio;
				break;
			}
		}
		
		if (domicilioCte.getCalle() != null) 
			tUbicacion.setCalle(domicilioCte.getCalle());
		if (domicilioCte.getNoExterior() != null) 
			tUbicacion.setNoExterior(domicilioCte.getNoExterior());
		if (domicilioCte.getNoInterior() != null && !domicilioCte.getNoInterior().trim().isEmpty()) {
			tUbicacion.setNoInterior(domicilioCte.getNoInterior());
		}
		if (domicilioCte.getEstado() != null) {
			tUbicacion.setPais(domicilioCte.getEstado().getPais().getNombre());
			tUbicacion.setEstado(domicilioCte.getEstado().getNombre());
		}
		if (domicilioCte.getMunicipio() != null) 
			tUbicacion.setMunicipio(domicilioCte.getMunicipio());
		if (domicilioCte.getColonia() != null) 
			tUbicacion.setColonia(domicilioCte.getColonia());
		if (domicilioCte.getCodigoPostal() != null) 
			tUbicacion.setCodigoPostal(domicilioCte.getCodigoPostal());
//		tUbicacion.setReferencia(domicilioCte.getReferencia());
//		tUbicacion.setLocalidad(domicilioCte.getLocalidad());
		
		receptor.setRfc(cliente.getRfc());
		receptor.setNombre(cliente.getNombre());
		receptor.setDomicilio(tUbicacion);
		return receptor;
	}
	
	private void getDetalleFromTicket(Ticket ticket, Comprobante comprobante) {
		Conceptos conceptos = new Conceptos();
		BigDecimal subTotal = new BigDecimal(0);
		for(Partida partida : ticket.getTransaccion().getPartidas()) {
			if (!documentoService.isArticuloSinPrecio(partida.getArticulo().getId())) {
				Concepto concepto = new Concepto();
				concepto.setCantidad(partida.getCantidad());
				concepto.setNoIdentificacion(partida.getArticulo().getId());
				concepto.setDescripcion(partida.getArticulo().getDescripcion());
				concepto.setImporte(partida.getPrecioTotal().divide(IVA_DIVISION, 4, BigDecimal.ROUND_HALF_UP));
				concepto.setValorUnitario(concepto.getImporte().divide(concepto.getCantidad(), 4, BigDecimal.ROUND_HALF_UP));
				if (partida.getArticulo().getUnidad() != null) {
					concepto.setUnidad(partida.getArticulo().getUnidad());
				} else {
					concepto.setUnidad(unidadDefault);
					subTotal = subTotal.add(concepto.getImporte());
				}
				if (partida.getArticulo().getTipoCategoria() != null && !partida.getArticulo().getTipoCategoria().equals(categoriaSinPrecio)) {
					subTotal = subTotal.add(concepto.getImporte());
				}
				conceptos.getConcepto().add(concepto);
			}
		}
		comprobante.setConceptos(conceptos);
		BigDecimal descuentoTotal = new BigDecimal(0);
		for(PartidaDescuento descuento : ticket.getTransaccion().getPartidasDescuentos()) {
			descuentoTotal = descuentoTotal.add(descuento.getDescuentoTotal());
		}
		
		descuentoTotal = descuentoTotal.negate();
		comprobante.setDescuento(descuentoTotal.divide(IVA_DIVISION, 2, BigDecimal.ROUND_HALF_UP));

		comprobante.setSubTotal(subTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		Impuestos impuesto = new Impuestos();
		BigDecimal importeImpuesto = (comprobante.getSubTotal().subtract(comprobante.getDescuento()))
				.multiply(IVA_MULTIPLICACION).setScale(2, BigDecimal.ROUND_HALF_UP);
		impuesto.setTotalImpuestosTrasladados(importeImpuesto);
		Traslados traslados = new Traslados();
		Traslado traslado = new Traslado();
		traslado.setImporte(importeImpuesto);
		traslado.setImpuesto(ivaDescripcion);
		traslado.setTasa(IVA.setScale(2, BigDecimal.ROUND_HALF_UP));
		traslados.getTraslado().add(traslado);
		impuesto.setTraslados(traslados);
		comprobante.setImpuestos(impuesto);
		comprobante.setTotal(comprobante.getSubTotal().subtract(comprobante.getDescuento()).add(comprobante.getImpuestos().getTotalImpuestosTrasladados()).setScale(2, BigDecimal.ROUND_UP));
	}
	
	private void createFechaDocumento(Comprobante comprobante) {
		Calendar dateNow = Calendar.getInstance();
		//FIXME verificar hora servidor
		dateNow.add(Calendar.HOUR, -1);
		try {
			XMLGregorianCalendar fechaComprobante = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateNow.get(Calendar.YEAR), 
					dateNow.get(Calendar.MONTH) + 1, dateNow.get(Calendar.DAY_OF_MONTH), 
					dateNow.get(Calendar.HOUR_OF_DAY), dateNow.get(Calendar.MINUTE), dateNow.get(Calendar.SECOND), 
					DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED); 
			comprobante.setFecha(fechaComprobante);
		} catch (DatatypeConfigurationException e) {
			logger.error("Ocurrió un error al asignar la fecha del Documento.", e);
			throw new PortalException("Ocurrió un error al asignar la fecha del Documento.", e);
		}
	}
	
	@Override
	public boolean sellarComprobante(Comprobante comprobante, CertificadoDigital certificado) {
		logger.debug("en sellar Documento");
		String cadena = obtenerCadena(comprobante);
		String sello = obtenerSelloDigital(cadena, certificado);
		logger.debug("SELLO: {}", sello);
		logger.debug("CADENA: {}", cadena);
		if(validSelloDigital(sello, cadena, comprobante, certificado)) {
			comprobante.setSello(sello);
			return true;
		} else {
			logger.error("El Sello Digital no es valido");
			throw new PortalException("El Sello Digital no es valido");
		}
	}
	
	private boolean validSelloDigital(String sello, String cadena, Comprobante comprobante, CertificadoDigital certificado) {
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
			logger.error("Ocurrió un error al obtener la fecha del ticket: ", e);
			throw new PortalException("Ocurrió un error al obtener la fecha del ticket: ", e);
		} catch (IOException e) {
			logger.error("Ocurrió un error al validar el Sello Digital, no se pudo cargar el certificado.", e);
			throw new PortalException("Ocurrió un error al validar el Sello Digital, no se pudo cargar el certificado.", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Ocurrió un error al validar el Sello Digital.", e);
			throw new PortalException("Ocurrió un error al validar el Sello Digital.", e);
		} catch (InvalidKeyException e) {
			logger.error("Ocurrió un error al validar el Sello Digital, el certificado es invalido", e);
			throw new PortalException("Ocurrió un error al validar el Sello Digital, el certificado es invalido", e);
		} catch (SignatureException e) {
			logger.error("Ocurrió un error al validar el Sello Digital, el certificado es invalido", e);
			throw new PortalException("Ocurrió un error al validar el Sello Digital, el certificado es invalido", e);
		}
	}

	private String obtenerSelloDigital(String cadena, CertificadoDigital certificado) {
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
			logger.error("Ocurrió un error al obtener el Sello Digital, no se pudo cargar la llave del certificado.", e);
			throw new PortalException("Ocurrió un error al obtener el Sello Digital, no se pudo cargar la llave del certificado.", e);
		} catch (GeneralSecurityException e) {
			logger.error("Ocurrió un error al obtener el Sello Digital.", e);
			throw new PortalException("Ocurrió un error al obtener el Sello Digital.", e);
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
						logger.error("Ocurrió un error al obtener la Cadena Original, "
								+ "no se pudo leer el xslt para generar la cadena original", e);
						throw new PortalException("Ocurrió un error al obtener la Cadena Original, "
								+ "no se pudo leer el xslt para generar la cadena original", e);
					}
				}
			});
			Transformer transformer = tFactory.newTransformer(xsltSource);
			transformer.transform(xmlSource, outputTarget);
			logger.debug("regresando Cadena");
			return writer.toString();
		} catch (TransformerConfigurationException e) {
			logger.error("Ocurrió un error al obtener la Cadena Original.", e);
			throw new PortalException("Ocurrió un error al obtener la Cadena Original.", e);
		} catch (IOException e) {
			logger.error("Ocurrió un error al obtener la Cadena Original, "
					+ "no se pudo leer el xslt para generar la cadena original", e);
			throw new PortalException("Ocurrió un error al obtener la Cadena Original, "
					+ "no se pudo leer el xslt para generar la cadena original", e);
		} catch (TransformerException e) {
			logger.error("Ocurrió un error al obtener la Cadena Original.", e);
			throw new PortalException("Ocurrió un error al obtener la Cadena Original.", e);
		}
	}
	
	@Transactional
	@Override
	public Cliente obtenerClienteDeComprobante(Comprobante comprobante) {
		Cliente cliente = new Cliente();
		DomicilioCliente domicilio = new DomicilioCliente();
		List<DomicilioCliente> domicilios = new ArrayList<DomicilioCliente>();
		
		Pais pais = new Pais();
		pais.setNombre(comprobante.getReceptor().getDomicilio().getPais());
		
		Estado estado = new Estado();
		estado.setNombre(comprobante.getReceptor().getDomicilio().getEstado());
		
		Pais paisBD;
		boolean paisSinEstado = false;
		
		cliente.setNombre(comprobante.getReceptor().getNombre());
		cliente.setRfc(comprobante.getReceptor().getRfc());
		
		if(!clienteService.exist(cliente)) {
			clienteService.saveClienteCorporativo(cliente);
		} else {
			cliente = clienteService.readClientesByNameRfc(cliente);
		}
		
		if(!pais.getNombre().isEmpty()) {
			paisBD = domicilioClienteService.readPais(pais);
			if(paisBD == null) {
				opcionDeCatalogoService.save(pais, "c_pais", "id_pais");
				estado.setPais(pais);
				if(!comprobarEstado(domicilio, estado)) {					
					paisSinEstado = true;
					domicilio.setEstado(new Estado());					
				}
			} else {
				estado.setPais(paisBD);				
				comprobarEstado(domicilio, estado);
			}
		}
			
		domicilio.setCalle(comprobante.getReceptor().getDomicilio().getCalle());
		domicilio.setNoExterior(comprobante.getReceptor().getDomicilio().getNoExterior());
		domicilio.setNoInterior(comprobante.getReceptor().getDomicilio().getNoInterior());
		domicilio.setColonia(comprobante.getReceptor().getDomicilio().getColonia());
		domicilio.setMunicipio(comprobante.getReceptor().getDomicilio().getMunicipio());
//		domicilio.setLocalidad(comprobante.getReceptor().getDomicilio().getLocalidad());
//		domicilio.setReferencia(comprobante.getReceptor().getDomicilio().getReferencia());
		domicilio.setCodigoPostal(comprobante.getReceptor().getDomicilio().getCodigoPostal());
		domicilio.setEstatus(EstatusDomicilioCliente.ACTIVO);
		domicilios.add(domicilio);
		cliente.setDomicilios(domicilios);
		
		if(paisSinEstado) {
			domicilioClienteService.save(cliente);
			domicilioClienteService.savePaisSinEstado(cliente.getDomicilios().get(0), pais);
		} else {
			DomicilioCliente dom = cliente.getDomicilios().get(0);
			if(dom != null) {
				List<DomicilioCliente> domiciliosBD = 
						domicilioClienteService.getByCliente(cliente);
				if(domiciliosBD != null && !domiciliosBD.isEmpty()) {
					boolean existeDom = false;
					for(DomicilioCliente domicilioBD : domiciliosBD) {
						if(clienteService.comparaDirecciones(dom, domicilioBD)) {
							dom.setId(domicilioBD.getId());
							existeDom = true;
							break;
						}					
					}
					if(!existeDom) {
						domicilioClienteService.save(cliente);
					}
				} else {
					domicilioClienteService.save(cliente);
				}
			}
		}
		
		return cliente;
	}

	private boolean comprobarEstado(DomicilioCliente domicilio, Estado estado) {
		Estado estadoBD;
		if(estado != null && !estado.getNombre().isEmpty()) {
			estadoBD = domicilioClienteService.readEstado(estado);
			if(estadoBD == null) {
				domicilioClienteService.saveEstado(estado);
				domicilio.setEstado(estado);
			} else {
				domicilio.setEstado(estadoBD);
			}
			return true;
		}
		return false;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
