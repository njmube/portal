package com.magnabyte.cfdi.portal.dao.factura.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mx.gob.sat.cfd._3.Comprobante;
import mx.gob.sat.cfd._3.Comprobante.Conceptos;
import mx.gob.sat.cfd._3.Comprobante.Conceptos.Concepto;
import mx.gob.sat.cfd._3.Comprobante.Emisor;
import mx.gob.sat.cfd._3.Comprobante.Receptor;
import mx.gob.sat.cfd._3.TUbicacion;
import mx.gob.sat.cfd._3.TUbicacionFiscal;

import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.factura.FacturaDao;

@Repository("facturaDao")
public class FacturaDaoImpl extends GenericJdbcDao implements FacturaDao {
	
	@Override
	public Collection<Comprobante> obteberDatosAImprimir() {

		Collection<Comprobante> listComprobante = new ArrayList<Comprobante>();
		Comprobante comprobante = new Comprobante();
		comprobante.setEmisor(getEmisor());
		comprobante.setReceptor(getReceptor());
		comprobante.setConceptos(getConceptos());
		listComprobante.add(comprobante);
		return listComprobante;
	}
	public Conceptos getConceptos() {
		Conceptos conceptos = new Comprobante.Conceptos();
		conceptos.setConcepto(getConcepto());
		return conceptos;
	}
	
	public TUbicacionFiscal getTUbicacionFiscal(){
		TUbicacionFiscal tUbicacionFiscal = new TUbicacionFiscal();
		tUbicacionFiscal.setCalle("Paseo de los tamaridos");
		tUbicacionFiscal.setCodigoPostal("05120");
		tUbicacionFiscal.setColonia("Bosques de las lomas");
		tUbicacionFiscal.setEstado("Distrito Federal");
		tUbicacionFiscal.setMunicipio("Cuajimalpa de morelos");
		tUbicacionFiscal.setNoInterior("2B");
		tUbicacionFiscal.setNoExterior("100 PB");
		tUbicacionFiscal.setPais("México");
		return tUbicacionFiscal;
		
	}
	
	public TUbicacion getTUbicacionExpedicion(){
		TUbicacion tUbicacion = new TUbicacion();
		tUbicacion.setCalle("Av. Lopez Portillo No. SMZ98");
		tUbicacion.setCodigoPostal("77530");
		tUbicacion.setColonia("Benito Juarez");
		tUbicacion.setEstado("Cancun");
		tUbicacion.setMunicipio("Quintana Roo");
		tUbicacion.setNoInterior("37 y 13");
		tUbicacion.setNoExterior("Mza. 53, Lote 1");
		tUbicacion.setPais("México");
		return tUbicacion;
		
	}
	
	public Emisor getEmisor(){
		Emisor emisor = new Emisor();
		emisor.setDomicilioFiscal(getTUbicacionFiscal());
		emisor.setExpedidoEn(getTUbicacionExpedicion());
		emisor.setNombre("MODATELAS S A P I DE CV");
		emisor.setRfc("MOD041014K13");
		return emisor;
	}
	public Receptor getReceptor(){
		Receptor receptor = new Receptor();
		receptor.setDomicilio(getTUbicacionExpedicion());
		receptor.setNombre("AUTOMOTRIZ CARIBES SA DE CV");
		receptor.setRfc("ACA820322RA6");
		return receptor;
	}
	
	public List<Concepto> getConcepto(){
		List<Concepto> conceptos = new ArrayList<Concepto>();
		Concepto concepto = new Comprobante.Conceptos.Concepto();
		concepto.setCantidad(new BigDecimal(1));
		concepto.setDescripcion("Ventas al 11%");
		concepto.setImporte(new BigDecimal(134.10));
		concepto.setUnidad("pza");
		concepto.setValorUnitario(new BigDecimal(987.1081));
		conceptos.add(concepto);
		conceptos.add(concepto);
		conceptos.add(concepto);
		conceptos.add(concepto);
		conceptos.add(concepto);
		conceptos.add(concepto);
		return conceptos; 
	}
	
	
}
