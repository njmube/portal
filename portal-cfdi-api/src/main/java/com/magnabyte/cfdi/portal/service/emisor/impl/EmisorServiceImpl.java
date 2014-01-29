package com.magnabyte.cfdi.portal.service.emisor.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.emisor.EmisorDao;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Emisor;
import com.magnabyte.cfdi.portal.model.cfdi.v32.TUbicacion;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.service.emisor.EmisorService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el servicio de emisor
 */
@Service("emisorService")
public class EmisorServiceImpl implements EmisorService {

	private static final Logger logger = LoggerFactory.getLogger(EmisorServiceImpl.class);

	@Autowired
	private EmisorDao emisorDao;
	
	@Transactional(readOnly = true)
	@Override
	public Cliente readClienteVentasMostrador(Establecimiento establecimiento) {
		logger.debug("readClienteVentasMostrador...");
		EmpresaEmisor empresaEmisor = emisorDao.read(establecimiento.getEmpresaEmisor());
		Cliente clienteVentasMostrador = new Cliente();
		DomicilioCliente domicilioCliente = new DomicilioCliente();
		Estado estado = new Estado();
		Pais pais = new Pais();
		List<DomicilioCliente> domicilios = new ArrayList<DomicilioCliente>();
		
		clienteVentasMostrador.setRfc(empresaEmisor.getEmisor().getRfc());
		clienteVentasMostrador.setNombre(empresaEmisor.getEmisor().getNombre());
		
		pais.setNombre(empresaEmisor.getEmisor().getDomicilioFiscal().getPais());
		estado.setNombre(empresaEmisor.getEmisor().getDomicilioFiscal().getEstado());
		estado.setPais(pais);
		
		domicilioCliente.setId(0);
		domicilioCliente.setCalle(empresaEmisor.getEmisor().getDomicilioFiscal().getCalle());
		domicilioCliente.setNoExterior(empresaEmisor.getEmisor().getDomicilioFiscal().getNoExterior());
		domicilioCliente.setNoInterior(empresaEmisor.getEmisor().getDomicilioFiscal().getNoInterior());
		domicilioCliente.setEstado(estado);
		domicilioCliente.setMunicipio(empresaEmisor.getEmisor().getDomicilioFiscal().getMunicipio());
		domicilioCliente.setColonia(empresaEmisor.getEmisor().getDomicilioFiscal().getColonia());
		domicilioCliente.setCodigoPostal(empresaEmisor.getEmisor().getDomicilioFiscal().getCodigoPostal());
		
		domicilios.add(domicilioCliente);
		clienteVentasMostrador.setDomicilios(domicilios);
		
		return clienteVentasMostrador;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Emisor getEmisorPorEstablecimiento(Establecimiento establecimiento) {
		EmpresaEmisor empresaEmisor = emisorDao.read(establecimiento.getEmpresaEmisor());
		TUbicacion expedidoEn = emisorDao.readLugarExpedicion(establecimiento);
		if (expedidoEn.getNoExterior() != null && expedidoEn.getNoExterior().trim().isEmpty()) 
			expedidoEn.setNoExterior(null);
		if (expedidoEn.getNoInterior() != null && expedidoEn.getNoInterior().trim().isEmpty()) {
			expedidoEn.setNoInterior(null);
		}
		
		if (empresaEmisor.getEmisor().getDomicilioFiscal().getNoInterior() != null
				&& empresaEmisor.getEmisor().getDomicilioFiscal().getNoInterior().trim().isEmpty()) {
			empresaEmisor.getEmisor().getDomicilioFiscal().setNoInterior(null);
		}
		
		empresaEmisor.getEmisor().setExpedidoEn(expedidoEn);
		return empresaEmisor.getEmisor();
	}
}
