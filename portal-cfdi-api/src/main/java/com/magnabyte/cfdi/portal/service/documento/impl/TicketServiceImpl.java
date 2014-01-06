package com.magnabyte.cfdi.portal.service.documento.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.stream.StreamSource;

import jcifs.Config;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.documento.TicketDao;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Service("ticketService")
public class TicketServiceImpl implements TicketService {

	private static final Logger logger = 
			LoggerFactory.getLogger(TicketServiceImpl.class);
	
	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private Unmarshaller unmarshaller;
	
	@Autowired
	private SambaService sambaService;
	
	@Transactional
	@Override
	public void save(DocumentoSucursal documento) {
		if(documento.getTicket() != null) {
			documento.getTicket().setTipoEstadoTicket(TipoEstadoTicket.GUARDADO);
			ticketDao.save(documento);
		} else {
			logger.debug("El Ticket no puede ser nulo.");
			throw new PortalException("El Ticket no puede ser nulo.");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Ticket read(Ticket ticket, Establecimiento establecimiento) {
		return ticketDao.read(ticket, establecimiento);
	}
	
	@Transactional
	@Override
	public void updateEstadoFacturado(DocumentoSucursal documento) {
		if(documento.getTicket() != null) {
			documento.getTicket().setTipoEstadoTicket(TipoEstadoTicket.FACTURADO);
			ticketDao.updateEstadoFacturado(documento);
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
		String fecha = fechaXml.substring(6, 10) + fechaXml.substring(3, 5) + fechaXml.substring(0, 2);
		String regex = noSucursal + "_" + noCaja + "_" + noTicket + "_" + fecha + "\\d\\d\\d\\d\\d\\d\\.xml";
		String urlTicketFiles = establecimiento.getRutaRepositorio().getRutaRepositorio() 
				+ establecimiento.getRutaRepositorio().getRutaRepoIn() + fecha + File.separator ; 
		logger.debug("Ruta ticket {}", urlTicketFiles);
		Pattern pattern = Pattern.compile(regex);
		SmbFile dir = null;
		try {
			dir = new SmbFile(urlTicketFiles);
			if(dir.exists()) {
				SmbFile[] files = dir.listFiles();
				for (SmbFile file : files) {
					Matcher matcher = pattern.matcher(file.getName());
					if (matcher.matches()) {
						
						Ticket ticketXml = (Ticket) unmarshaller.unmarshal(new StreamSource(sambaService.getFileStream(urlTicketFiles, file.getName())));
						
						if (!ticketXml.getTransaccion().getTransaccionHeader().getTipoTransaccion().equalsIgnoreCase("SA") //FIXME
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
						return true;
					}
				}
			}
		} catch (MalformedURLException e) {
			logger.error("La URL del archivo no es valida: {}", e);
			throw new PortalException("La URL del archivo no es válida: "+ e.getMessage());
		} catch (SmbException e) {
			logger.error("Ocurrió un error al intentar recuperar el ticket: {}", e);
			throw new PortalException("Ocurrió un error al intentar recuperar el ticket: " + e.getMessage());
		} catch (XmlMappingException e) {
			logger.error("Ocurrió un error al leer ticket: {}", e);
			throw new PortalException("Ocurrió un error al leer el ticket: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Ocurrió un error al intentar recuperar el ticket: {}", e);
			throw new PortalException("Ocurrió un error al intentar recuperar el ticket: " + e.getMessage());
		}
		return false;
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean ticketProcesado(Ticket ticket, Establecimiento establecimiento) {
		Ticket ticketDB = ticketDao.read(ticket, establecimiento);
		if (ticketDB != null) {
			switch (ticketDB.getTipoEstadoTicket()) {
			case FACTURADO:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	@Override
	public String formatTicketClave(Ticket ticket) {
		NumberFormat nf = new DecimalFormat("000");
		Integer numeroSucursal = 0;
		try {
			numeroSucursal = Integer.valueOf(ticket.getTransaccion().getTransaccionHeader().getIdSucursal());
		} catch (NumberFormatException nfe) {
			logger.error("El numero de sucursal es invalido:", nfe);
		}
		return nf.format(numeroSucursal);
	}
}
