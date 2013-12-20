package com.magnabyte.cfdi.portal.dao.factura.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import mx.gob.sat.cfd._3.Comprobante;
import mx.gob.sat.cfd._3.Comprobante.Conceptos;
import mx.gob.sat.cfd._3.Comprobante.Conceptos.Concepto;
import mx.gob.sat.cfd._3.Comprobante.Emisor;
import mx.gob.sat.cfd._3.Comprobante.Emisor.RegimenFiscal;
import mx.gob.sat.cfd._3.Comprobante.Receptor;
import mx.gob.sat.cfd._3.TUbicacion;
import mx.gob.sat.cfd._3.TUbicacionFiscal;

import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.factura.FacturaDao;

@Repository("facturaDao")
public class FacturaDaoImpl extends GenericJdbcDao implements FacturaDao {

	@Override
	public List<Comprobante> obteberDatosAImprimir() {

		List<Comprobante> listComprobante = new ArrayList<Comprobante>();
		Comprobante comprobante = new Comprobante();
		comprobante.setEmisor(getEmisor());
		comprobante.setReceptor(getReceptor());
		comprobante.setConceptos(getConceptos());
		comprobante.setDescuento(new BigDecimal(0.00));
		comprobante.setFormaDePago("PAGO EN UNA SOLA EXHIBICION");
		comprobante.setCertificado("00001000000102481624");
		comprobante.setFolio("DS4118");
		comprobante.setSubTotal(new BigDecimal(518.93));
		comprobante.setTotal(new BigDecimal(5765.22));
		comprobante.setMetodoDePago("Efectivo");
		comprobante.setFolioFiscalOrig("B1DO5FC8-5BC1-4307-97BD-4F47744DE4E1");
		comprobante.setNumCtaPago("1234");
		comprobante.setMoneda("MXP");
		comprobante.setSerieFolioFiscalOrig("00001000000300171291");
		comprobante.setTipoCambio("1.0000");
		Date today = new Date();
		XMLGregorianCalendar xml = toXMLGregorianCalendar(today);
		comprobante.setFechaFolioFiscalOrig(xml);
		listComprobante.add(comprobante);

		return listComprobante;
	}

	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gCalendar);
		} catch (DatatypeConfigurationException ex) {
		}
		return xmlCalendar;
	}

	public Conceptos getConceptos() {
		Conceptos conceptos = new Comprobante.Conceptos();
		getConcepto(conceptos.getConcepto());
		return conceptos;
	}

	public TUbicacionFiscal getTUbicacionFiscal() {
		TUbicacionFiscal tUbicacionFiscal = new TUbicacionFiscal();
		tUbicacionFiscal.setCalle("Paseo de los tamaridos");
		tUbicacionFiscal.setCodigoPostal("05120");
		tUbicacionFiscal.setColonia("Bosques de las lomas");
		tUbicacionFiscal.setEstado("Distrito Federal");
		tUbicacionFiscal.setMunicipio("Cuajimalpa de morelos");
		tUbicacionFiscal.setNoInterior("2B");
		tUbicacionFiscal.setNoExterior("100 PB");
		tUbicacionFiscal.setPais("México");
		// tUbicacionFiscal.setLocalidad("Gustavo A. Madero");
		return tUbicacionFiscal;

	}

	public TUbicacion getTUbicacionExpedicion() {
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

	public Emisor getEmisor() {
		Emisor emisor = new Emisor();
		emisor.setDomicilioFiscal(getTUbicacionFiscal());
		emisor.setExpedidoEn(getTUbicacionExpedicion());
		emisor.setNombre("MODATELAS S A P I DE CV");
		emisor.setRfc("MOD041014K13");
		getRegimen(emisor.getRegimenFiscal());
		return emisor;
	}

	public Receptor getReceptor() {
		Receptor receptor = new Receptor();
		receptor.setDomicilio(getTUbicacionExpedicion());
		receptor.setNombre("AUTOMOTRIZ CARIBES SA DE CV");
		receptor.setRfc("ACA820322RA6");
		return receptor;
	}

	public List<Concepto> getConcepto(List<Concepto> conceptos) {

		for (int i = 0; i < 17; i++) {
			Concepto concepto = new Comprobante.Conceptos.Concepto();
			concepto.setCantidad(new BigDecimal(1));
			concepto.setDescripcion("Ventas al 11%");
			concepto.setImporte(new BigDecimal(134.10));
			concepto.setUnidad("pza");
			concepto.setValorUnitario(new BigDecimal(987.1081));

			conceptos.add(concepto);
		}
		return conceptos;
	}

	public List<RegimenFiscal> getRegimen(List<RegimenFiscal> regimenes) {
		RegimenFiscal regimen = new RegimenFiscal();
		regimen.setRegimen("REGIMEN GENERAL DE LEY DE PERSONAS MORALES");
		regimenes.add(regimen);
		return regimenes;
	}

}
