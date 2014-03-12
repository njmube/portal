package com.magnabyte.cfdi.portal.service.documento.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.magnabyte.cfdi.portal.service.cfdi.v32.CfdiV32Service;
import com.magnabyte.cfdi.portal.service.cfdi.v32.impl.CfdiV32ServiceImpl;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.cliente.DomicilioClienteService;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;
import com.magnabyte.cfdi.portal.service.documento.ComprobanteService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.emisor.EmisorService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 * 
 * Clase que representa el servicio de comprobante
 */
@Service("comprobanteService")
public class ComprobanteServiceImpl implements ComprobanteService {

	private static final Logger logger = LoggerFactory.getLogger(ComprobanteServiceImpl.class);
	
	@Autowired
	private CfdiV32Service cfdiV32Service;

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
	
	@Autowired
	private MessageSource messageSource;
	
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
	
	@Value("${ticket.moneda.default}")
	private String tipoMoneda;
	
	private BigDecimal IVA;
	
	private BigDecimal IVA_DIVISION;
	
	private BigDecimal IVA_MULTIPLICACION;
	
	@PostConstruct
	public void init() {
		IVA = new BigDecimal(ivaTasa);
		
		IVA_DIVISION = IVA
			.divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE).setScale(4, BigDecimal.ROUND_HALF_UP);
		IVA_MULTIPLICACION = IVA_DIVISION
			.subtract(BigDecimal.ONE).setScale(4, BigDecimal.ROUND_HALF_UP);
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
//				comprobante.setMoneda(infoPago.getPago().getMoneda());
				comprobante.setMoneda(tipoMoneda);
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
			throw new PortalException(messageSource.getMessage("ticket.nulo", null, null));
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
		comprobante.setVersion(CfdiV32ServiceImpl.VERSION_CFDI);
		comprobante.setSello(CfdiV32ServiceImpl.SELLO_PREVIO);
		comprobante.setNoCertificado(CfdiV32ServiceImpl.NUMERO_CERTIFICADO_PREVIO);
		comprobante.setCertificado(CfdiV32ServiceImpl.CERTIFICADO_PREVIO);
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
	
	@Override
	public void depurarReceptor(Documento facturaDocumentoNuevo) {
		if (facturaDocumentoNuevo.getComprobante().getReceptor().getDomicilio().getNoInterior().isEmpty()) {
			facturaDocumentoNuevo.getComprobante().getReceptor().getDomicilio().setNoInterior(null);
		}
	}
	
	private void getDetalleFromTicket(Ticket ticket, Comprobante comprobante) {
		Conceptos conceptos = new Conceptos();
		BigDecimal subTotal = BigDecimal.ZERO;
		for(Partida partida : ticket.getTransaccion().getPartidas()) {
			if (!documentoService.isArticuloSinPrecio(partida.getArticulo().getId())) {
				Concepto concepto = new Concepto();
				concepto.setCantidad(partida.getCantidad());
				concepto.setNoIdentificacion(partida.getArticulo().getId());
				concepto.setDescripcion(partida.getArticulo().getDescripcion());
				concepto.setValorUnitario(partida.getPrecioUnitario().divide(IVA_DIVISION, 4, BigDecimal.ROUND_HALF_UP));
				concepto.setImporte(concepto.getValorUnitario().multiply(concepto.getCantidad()).setScale(4, BigDecimal.ROUND_HALF_UP));
				if (partida.getArticulo().getUnidad() != null) {
					concepto.setUnidad(partida.getArticulo().getUnidad());
				} else {
					concepto.setUnidad(unidadDefault);
				}
				if (partida.getArticulo().getTipoCategoria() != null && !partida.getArticulo().getTipoCategoria().equals(categoriaSinPrecio)) {
					subTotal = subTotal.add(concepto.getImporte());
				}
				conceptos.getConcepto().add(concepto);
			}
		}
		comprobante.setConceptos(conceptos);
		BigDecimal descuentoTotal = BigDecimal.ZERO;
		for(PartidaDescuento descuento : ticket.getTransaccion().getPartidasDescuentos()) {
			descuentoTotal = descuentoTotal.add(descuento.getDescuentoTotal());
		}
		
		descuentoTotal = descuentoTotal.negate();
		comprobante.setDescuento(descuentoTotal.divide(IVA_DIVISION, 2, BigDecimal.ROUND_HALF_UP));

		comprobante.setSubTotal(subTotal.setScale(2, BigDecimal.ROUND_HALF_UP));

		comprobante.setTotal(ticket.getTransaccion().getTransaccionTotal().getTotalVenta());
		Impuestos impuesto = new Impuestos();
		BigDecimal importeImpuesto = comprobante.getTotal()
				.subtract((comprobante.getSubTotal().subtract(comprobante.getDescuento())))
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		impuesto.setTotalImpuestosTrasladados(importeImpuesto);
		Traslados traslados = new Traslados();
		Traslado traslado = new Traslado();
		traslado.setImporte(importeImpuesto);
		traslado.setImpuesto(ivaDescripcion);
		traslado.setTasa(IVA.setScale(2, BigDecimal.ROUND_HALF_UP));
		traslados.getTraslado().add(traslado);
		impuesto.setTraslados(traslados);
		comprobante.setImpuestos(impuesto);
	}
	
	@Override
	public void createFechaDocumento(Comprobante comprobante) {
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
			logger.error(messageSource.getMessage("comprobante.error.fecha", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("comprobante.error.fecha", new Object[] {e.getMessage()}, null));
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

}
