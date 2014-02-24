package com.magnabyte.cfdi.portal.service.documento.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.stream.StreamSource;

import jcifs.Config;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.documento.TicketDao;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.factory.EstablecimientoFactory;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.ListaTickets;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.InformacionPago;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.InformacionPago.Pago;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.Partida;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.Partida.Articulo;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.PartidaDescuento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.PartidaDevolucion;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.TransaccionHeader;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;
import com.magnabyte.cfdi.portal.model.utils.StringUtils;
import com.magnabyte.cfdi.portal.service.documento.ComprobanteService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Clase que representa el servicio de ticket
 */
@Service("ticketService")
public class TicketServiceImpl implements TicketService {

	private static final Logger logger = 
			LoggerFactory.getLogger(TicketServiceImpl.class);
	
	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private ComprobanteService comprobanteService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
	@Autowired
	private Unmarshaller unmarshaller;
	
	@Autowired
	private SambaService sambaService;
	
	@Value("${ticket.clave.venta}")
	private String claveVentaTicket;
	
	@Value("${ticket.clave.devolucion}")
	private String claveDevolucionTicket;
	
	@Value("${vm.concepto.descripcion}")
	private String vmConceptoDescripcion;
	
	@Value("${vm.concepto.unidad}")
	private String vmConceptoUnidad;
	
	@Value("${vm.concepto.categoria}")
	private String vmConceptoCategoria;
	
	@Value("${vm.metodo.pago}")
	private String vmMetodoPago;
	
	@Value("${vm.moneda}")
	private String vmMoneda;
	
	@Value("${ticket.categoria.sinprecio}")
	private String categoriaSinPrecio; 
	
	private static final String ticketGenerico = "0";
	
	private static final String cajaGenerica = "0";
	
	@Transactional
	@Override
	public void save(DocumentoSucursal documento) {
		if(documento.getTicket() != null) {
			if (!documento.isRequiereNotaCredito()) {
				documento.getTicket().setTipoEstadoTicket(TipoEstadoTicket.FACTURADO);
			}
			ticketDao.save(documento);
		} else {
			logger.debug("El Ticket no puede ser nulo.");
			throw new PortalException("El Ticket no puede ser nulo.");
		}
	}
	
	@Transactional
	@Override
	public void guardarTicketsCierreDia(Documento documento) {
		if (documento.getVentas() != null && !documento.getVentas().isEmpty()) {
			ticketDao.saveTicketsCierreDia(documento, TipoEstadoTicket.FACTURADO_MOSTRADOR);
		}
		
		if (documento.getDevoluciones() != null && !documento.getDevoluciones().isEmpty()) {
			ticketDao.saveTicketsCierreDia(documento, TipoEstadoTicket.DEVUELTO);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Ticket read(Ticket ticket, Establecimiento establecimiento) {
		Ticket ticketDB = null;
		ticketDB = ticketDao.readByStatus(ticket, establecimiento, TipoEstadoTicket.FACTURADO);
		if (ticketDB != null) {
			return ticketDB;
		}
//		ticketDB = ticketDao.readByStatus(ticket, establecimiento, TipoEstadoTicket.GUARDADO);
//		if (ticketDB != null) {
//			return ticketDB;
//		}
		ticketDB = ticketDao.readByStatus(ticket, establecimiento, TipoEstadoTicket.FACTURADO_MOSTRADOR);
		if (ticketDB != null) {
			return ticketDB;
		}
		return ticketDB;
	}

	@Transactional(readOnly = true)
	@Override
	public Ticket readByDocumento(Documento documento) {
		return ticketDao.readByDocumento(documento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Integer readIdDocFromTicketFacturado(DocumentoSucursal documento) {
		return ticketDao.readIdDocFromTicketFacturado(documento);
	}
	
	@Transactional
	@Override
	public void updateEstadoFacturado(DocumentoSucursal documento) {
		if(documento.getTicket() != null) {
			documento.getTicket().setTipoEstadoTicket(TipoEstadoTicket.FACTURADO);
			ticketDao.updateEstado(documento);
		} else {
			logger.debug("El Ticket no puede ser nulo.");
			throw new PortalException("El Ticket no puede ser nulo.");
		}
	}
	
	@Transactional
	@Override
	public void updateEstadoNcr(DocumentoSucursal documento) {
		if(documento.getTicket() != null) {
			documento.getTicket().setTipoEstadoTicket(TipoEstadoTicket.NCR_GENERADA);
			ticketDao.updateEstado(documento);
		} else {
			logger.debug("El Ticket no puede ser nulo.");
			throw new PortalException("El Ticket no puede ser nulo.");
		}
	}
	
	@Override
	public boolean ticketExists(Ticket ticket, Establecimiento establecimiento) {
		logger.debug("buscando ticket ticketExists");
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		String noSucursal = establecimiento.getClave();
		String noCaja = ticket.getTransaccion().getTransaccionHeader().getIdCaja();
		String noTicket = ticket.getTransaccion().getTransaccionHeader().getIdTicket();
		String fechaXml = ticket.getTransaccion().getTransaccionHeader().getFecha();
		BigDecimal importe = ticket.getTransaccion().getTransaccionTotal().getTotalVenta();
		String fecha = FechasUtils.specificStringFormatDate(fechaXml, FechasUtils.formatddMMyyyyHyphen, FechasUtils.formatyyyyMMdd);
		String regex = noSucursal + "_" + noCaja + "_" + noTicket + "_" + fecha + "\\d{6}\\.xml$";
		String urlTicketFiles = establecimiento.getRutaRepositorio().getRutaRepositorio() 
				+ establecimiento.getRutaRepositorio().getRutaRepoIn() + fecha + "/"; 
		logger.debug("Ruta ticket {}", urlTicketFiles);
		Pattern pattern = Pattern.compile(regex);
		logger.debug(regex);
		SmbFile dir = null;
		NtlmPasswordAuthentication authentication = sambaService.getAuthentication(establecimiento);
		try {
			dir = new SmbFile(urlTicketFiles, authentication);
			if(dir.exists()) {
				logger.debug("el dir existe");
				SmbFile[] files = dir.listFiles();
				logger.debug("archivos {}", files.length);
				for (SmbFile file : files) {
					Matcher matcher = pattern.matcher(file.getName());
					if (matcher.matches()) {
						
						Ticket ticketXml = (Ticket) unmarshaller.unmarshal(new StreamSource(sambaService
								.getFileStream(urlTicketFiles, file.getName(), authentication)));
						
						if (!ticketXml.getTransaccion().getTransaccionHeader().getTipoTransaccion().equalsIgnoreCase(claveVentaTicket)
								|| ticketXml.getTransaccion().getTransaccionTotal().getTotalVenta().compareTo(importe) != 0) {
							return false;
						}
						ticket.setTransaccion(ticketXml.getTransaccion());
						ticket.getTransaccion().getTransaccionHeader().setFecha(fechaXml);
						if (ticket.getTransaccion().getInformacionPago() != null &&
								ticket.getTransaccion().getInformacionPago().size() > 0 &&
								ticket.getTransaccion().getInformacionPago().get(0).getNumeroCuenta() != null) {
							String numeroCuenta = ticket.getTransaccion().getInformacionPago().get(0).getNumeroCuenta();
							ticket.getTransaccion().getInformacionPago().get(0).setNumeroCuenta(numeroCuenta.replaceAll("\\*", ""));
						}
						ticket.setNombreArchivo(file.getName());
						return true;
					}
				}
			} else {
				logger.debug("el archivo no existe");
			}
		} catch (MalformedURLException e) {
			logger.error("La URL del archivo no es valida: ", e);
			throw new PortalException("La URL del archivo no es válida: "+ e.getMessage());
		} catch (SmbException e) {
			logger.error("Ocurrió un error al intentar recuperar el ticket: ", e);
			throw new PortalException("Ocurrió un error al intentar recuperar el ticket: " + e.getMessage());
		} catch (XmlMappingException e) {
			logger.error("Ocurrió un error al leer ticket: ", e);
			throw new PortalException("Ocurrió un error al leer el ticket: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Ocurrió un error al intentar recuperar el ticket: ", e);
			throw new PortalException("Ocurrió un error al intentar recuperar el ticket: " + e.getMessage());
		}
		return false;
	}
	
	@Override
	public void closeOfDay(Establecimiento establecimiento, String fechaCierre, List<Ticket> ventas, List<Ticket> devoluciones) {
		String urlTicketFiles = establecimiento.getRutaRepositorio().getRutaRepositorio() 
				+ establecimiento.getRutaRepositorio().getRutaRepoIn() + fechaCierre + "/"; 
		String regex = "^\\d+_\\d+_\\d+_\\d{14}\\.xml$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = null;
		SmbFile dir = null;
		List<String> archivosTicketsDelDia = new ArrayList<String>();
		try {
			NtlmPasswordAuthentication authentication = sambaService.getAuthentication(establecimiento);
			dir = new SmbFile(urlTicketFiles, authentication);
			if(dir.exists()) {
				SmbFile[] files = dir.listFiles();
				logger.debug("archivos {}", files.length);
				for (SmbFile file : files) {
					archivosTicketsDelDia.add(file.getName());
				}
				logger.debug("tickets size {}", archivosTicketsDelDia.size());
				
				List<String> archivosFacturados = ticketDao.readAllByDate(
						FechasUtils.specificStringFormatDate(fechaCierre, FechasUtils.formatyyyyMMdd, FechasUtils.formatyyyyMMddHyphen));
				
				logger.debug("facturados size {}", archivosFacturados.size());
				
				if (archivosFacturados != null) {
					archivosTicketsDelDia.removeAll(archivosFacturados);
				}
				
				for(String file : archivosTicketsDelDia) {
					matcher = pattern.matcher(file);
					if (matcher.matches()) {
						Ticket ticketXml = (Ticket) unmarshaller.unmarshal(new StreamSource(sambaService.getFileStream(urlTicketFiles, file, authentication)));
						ticketXml.setNombreArchivo(file);
						if (ticketXml.getTransaccion().getTransaccionHeader().getTipoTransaccion().equalsIgnoreCase(claveVentaTicket)) {
							ventas.add(ticketXml);
						} else if (ticketXml.getTransaccion().getTransaccionHeader().getTipoTransaccion().equalsIgnoreCase(claveDevolucionTicket)) {
							devoluciones.add(ticketXml);
						}
					}
				}
			}
		} catch(IOException ex) {
			logger.error("Ocurrió un error al generar la factura de ventas mostrador");
			throw new PortalException("Ocurrió un error al generar la factura de ventas mostrador");
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean isTicketFacturado(Ticket ticket, Establecimiento establecimiento) {
		Ticket ticketDB = ticketDao.readByStatus(ticket, establecimiento, TipoEstadoTicket.FACTURADO);
		return ticketDB != null;
	}
	
	@Override
	public DocumentoSucursal readDocFromTicket(String archivoOrigen) {
		DocumentoSucursal documentoOrigen = null;
		documentoOrigen = ticketDao.readDocByStatus(archivoOrigen, TipoEstadoTicket.FACTURADO);
		if (documentoOrigen != null) {
			return documentoOrigen;
		}
		documentoOrigen = ticketDao.readDocByStatus(archivoOrigen, TipoEstadoTicket.FACTURADO_MOSTRADOR);
		if (documentoOrigen != null) {
			return documentoOrigen;
		}
		return documentoOrigen;
	}
	
	@Transactional(readOnly = true)
	@Cacheable("articulosSinPrecio")
	@Override
	public List<String> readArticulosSinPrecio() {
		logger.debug("metodo con cache - articulos sin precio");
		return ticketDao.readArticulosSinPrecio();
	}

//	@Override
//	public String formatTicketClaveSucursal(Ticket ticket) {
//		NumberFormat nf = new DecimalFormat("000");
//		Integer numeroSucursal = 0;
//		try {
//			numeroSucursal = Integer.valueOf(ticket.getTransaccion().getTransaccionHeader().getIdSucursal());
//		} catch (NumberFormatException nfe) {
//			logger.error("El numero de sucursal es invalido:", nfe);
//		}
//		return nf.format(numeroSucursal);
//	}
	
	@Override
	public Ticket crearTicketVentasMostrador(List<Ticket> ventas,
			Establecimiento establecimiento) {
		logger.debug("ventas {}", ventas.size());
		Ticket ticketVentasMostrador = new Ticket();
		Transaccion transaccion = new Transaccion();
		TransaccionHeader header = new TransaccionHeader();
		InformacionPago infoPago = new InformacionPago();
		Partida concepto = new Partida();
		Articulo articulo = new Articulo();
		PartidaDescuento descuento = new PartidaDescuento();
		Pago pago = new Pago();
		BigDecimal precioTotal = new BigDecimal(0);
		BigDecimal descuentoTotal = new BigDecimal(0);
		pago.setMetodoPago(vmMetodoPago);
		pago.setMoneda(vmMoneda);
		
		infoPago.setPago(pago);
		transaccion.setTransaccionHeader(header);
		transaccion.getInformacionPago().add(infoPago);
		ticketVentasMostrador.setTransaccion(transaccion);
		header.setIdTicket(ticketGenerico);
		header.setIdCaja(cajaGenerica);
		header.setIdSucursal(establecimiento.getClave());
		header.setFechaHora(FechasUtils.parseDateToString(new Date(), FechasUtils.formatddMMyyyyHHmmssSlash));
		
		concepto.setCantidad(new BigDecimal(0));
		articulo.setId(null);
		articulo.setDescripcion(vmConceptoDescripcion);
		articulo.setUnidad(vmConceptoUnidad);
		articulo.setTipoCategoria(vmConceptoCategoria);
		concepto.setArticulo(articulo);
		
		for (Ticket ticket : ventas) {
			for (Partida partida : ticket.getTransaccion().getPartidas()) {
				if (!documentoService.isArticuloSinPrecio(partida.getArticulo().getId())) {
					if (partida.getArticulo().getTipoCategoria() != null && !partida.getArticulo().getTipoCategoria().equals(categoriaSinPrecio)) {
						precioTotal = precioTotal.add(partida.getPrecioTotal());
					}
				}
			}
			
			for (PartidaDescuento partidaDescuento : ticket.getTransaccion().getPartidasDescuentos()) {
				descuentoTotal = descuentoTotal.add(partidaDescuento.getDescuentoTotal());
			}
		}
		concepto.setPrecioUnitario(precioTotal);
		concepto.setPrecioTotal(precioTotal);
		descuento.setDescuentoTotal(descuentoTotal);
		ticketVentasMostrador.getTransaccion().getPartidas().add(concepto);
		ticketVentasMostrador.getTransaccion().getPartidasDescuentos().add(descuento);
		
		return ticketVentasMostrador;
	}
	
	@Override
	public Ticket crearTicketDevolucion(DocumentoSucursal documentoOrigen,
			List<Ticket> devoluciones, Establecimiento establecimiento) {
		
		Comprobante comprobante = documentoService.findById(documentoOrigen).getComprobante();
		Ticket ticketDevolucion = new Ticket();
		Transaccion transaccion = new Transaccion();
		TransaccionHeader header = new TransaccionHeader();
		InformacionPago infoPago = new InformacionPago();
		Pago pago = new Pago();
		BigDecimal precioTotal = new BigDecimal(0);
		BigDecimal descuentoTotal = new BigDecimal(0);
		
		pago.setMetodoPago(comprobante.getMetodoDePago());
		pago.setMoneda(comprobante.getMoneda());
		
		infoPago.setNumeroCuenta(comprobante.getNumCtaPago());
		infoPago.setPago(pago);
		
		transaccion.setTransaccionHeader(header);
		transaccion.getInformacionPago().add(infoPago);
		ticketDevolucion.setTransaccion(transaccion);
		header.setIdTicket(ticketGenerico);
		header.setIdCaja(cajaGenerica);
		header.setIdSucursal(establecimiento.getClave());
		header.setFechaHora(FechasUtils.parseDateToString(new Date(), FechasUtils.formatddMMyyyyHHmmssSlash));
		
		for (Ticket ticket : devoluciones) {
			if (ticket.getTransaccion().getPartidasDevolucion().get(0).getTicketFileOrigen()
					.equals(documentoOrigen.getTicket().getNombreArchivo())) {
				for (PartidaDevolucion partidaDevolucion : ticket.getTransaccion().getPartidasDevolucion()) {
					if (!documentoService.isArticuloSinPrecio(partidaDevolucion.getArticulo().getId())) {
						if (partidaDevolucion.getArticulo().getTipoCategoria() != null && !partidaDevolucion
								.getArticulo().getTipoCategoria().equals(categoriaSinPrecio)) {
							Partida partida = new Partida();
							Articulo articulo = new Articulo();
							articulo.setId(partidaDevolucion.getArticulo().getId());
							articulo.setDescripcion(partidaDevolucion.getArticulo().getDescripcion());
							articulo.setUnidad(partidaDevolucion.getArticulo().getUnidad());
							articulo.setTipoCategoria(partidaDevolucion.getArticulo().getTipoCategoria());
							partida.setCantidad(partidaDevolucion.getCantidad().negate());
							partida.setPrecioUnitario(partidaDevolucion.getPrecioUnitario());
							partida.setPrecioTotal(partidaDevolucion.getPrecioTotal().negate());
							partida.setArticulo(articulo);
							ticketDevolucion.getTransaccion().getPartidas().add(partida);
							precioTotal = precioTotal.add((partidaDevolucion.getPrecioTotal()));
						}
					}
				}
				
				for (PartidaDescuento partidaDescuento : ticket.getTransaccion().getPartidasDescuentos()) {
					partidaDescuento.setDescuentoTotal(partidaDescuento.getDescuentoTotal().negate());
					ticketDevolucion.getTransaccion().getPartidasDescuentos().add(partidaDescuento);
					descuentoTotal = descuentoTotal.add(partidaDescuento.getDescuentoTotal());
				}
			}
		}
		return ticketDevolucion;
	}

	@Override
	public String recibeTicketsWsdl(ListaTickets tickets) {
		
		return "Respuesta success: " + tickets.getVentas().size();
	}

	@Override
	public String fechaCierre(String noEstablecimiento) {
		Establecimiento establecimiento =  
				EstablecimientoFactory.newInstanceClave(StringUtils.
						formatTicketClaveSucursal(noEstablecimiento));
		
		establecimiento = establecimientoService.readByClave(establecimiento);
		String fechaCierre = establecimientoService.readFechaCierreById(establecimiento);
		
		return fechaCierre;
	}
}
