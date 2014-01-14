package com.magnabyte.cfdi.portal.web.cfdi.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.cfd._3.Comprobante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.service.certificado.CertificadoService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.service.emisor.EmisorService;
import com.magnabyte.cfdi.portal.web.cfdi.CfdiService;
import com.magnabyte.cfdi.portal.web.webservice.DocumentoWebService;

@Service("cfdiService")
public class CfdiServiceImpl implements CfdiService {
	
	private static final Logger logger = LoggerFactory.getLogger(CfdiServiceImpl.class);
	
	@Autowired
	private DocumentoWebService documentoWebService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private CertificadoService certificadoService;
	
	@Autowired
	private EmisorService emisorService;
	
	@Value("${hora.inicio}")
	private int horaInicio;
	
	@Value("${hora.cierre}")
	private int horaCierre;
	
	@Value("${generic.rfc.ventas.mostrador}")
	private String rfcVentasMostrador;
	
	@Override
	public void generarDocumento(Documento documento, HttpServletRequest request, boolean isVentasMostrador) {
		logger.debug("cfdiService...");
		CertificadoDigital certificado = certificadoService.readVigente(documento.getComprobante());
		documentoService.guardarDocumento(documento);
		if(isVentasMostrador) {
			ticketService.saveTicketVentasMostrador(documento);
		}
		if (documentoService.sellarComprobante(documento.getComprobante(), certificado)) {
			if (documentoWebService.timbrarDocumento(documento, request)) {
				documentoService.insertDocumentoCfdi(documento);
				documentoService.insertAcusePendiente(documento);
				if(documento instanceof DocumentoSucursal) {
					ticketService.updateEstadoFacturado((DocumentoSucursal) documento);
				}
			}
		}	
	}
	
	@Transactional
	@Override
	public void closeOfDay(String fechaCierre, Establecimiento establecimiento, HttpServletRequest request) {
		List<Ticket> ventas = new ArrayList<Ticket>();
		List<Ticket> devoluciones = new ArrayList<Ticket>();
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (hora > horaCierre) {
			//FIXME
			fechaCierre = "20131217";
			ticketService.closeOfDay(establecimiento, fechaCierre, ventas, devoluciones);
			
			logger.debug("devoluciones {}", devoluciones.size());
			//FIXME devoluciones
			
			Ticket ticketVentasMostrador = ticketService.crearTicketVentasMostrador(ventas, establecimiento);
			Cliente cliente = emisorService.readClienteVentasMostrador(establecimiento);
			cliente.setRfc(rfcVentasMostrador);
			int domicilioFiscal = cliente.getDomicilios().get(0).getId();
			Comprobante comprobante = documentoService.obtenerComprobantePor(cliente, ticketVentasMostrador, domicilioFiscal, establecimiento);
			Documento documento = new Documento();
			documento.setEstablecimiento(establecimiento);
			documento.setCliente(cliente);
			documento.setComprobante(comprobante);
			documento.setTipoDocumento(TipoDocumento.FACTURA);
			documento.setVentas(ventas);
			
			generarDocumento(documento, request, true);
			
		} else {
			logger.error("El cierre del dia actual es posible realizarlo hasta despues del cierre de la tienda");
			throw new PortalException("El cierre del dia actual es posible realizarlo hasta despues del cierre de la tienda");
		}
	}

}
