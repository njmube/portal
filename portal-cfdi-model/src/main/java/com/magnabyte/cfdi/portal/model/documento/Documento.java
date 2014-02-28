package com.magnabyte.cfdi.portal.model.documento;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.tfd.v32.TimbreFiscalDigital;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public class Documento implements Serializable, Cloneable {

	private static final long serialVersionUID = 383916791240840326L;

	private Integer id;
	private Integer id_domicilio;
	private Comprobante comprobante;
	private Cliente cliente;
	private String cadenaOriginal;
	private Date fechaFacturacion;
	private Establecimiento establecimiento;
	private TimbreFiscalDigital timbreFiscalDigital;
	private TipoDocumento tipoDocumento;
	private String nombre;
	private List<Ticket> ventas;
	private List<Ticket> devoluciones;
	private byte[] xmlCfdi;
	private byte[] xmlCfdiAcuse;
	private boolean ventasMostrador;
	private Documento documentoOrigen;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public TimbreFiscalDigital getTimbreFiscalDigital() {
		return timbreFiscalDigital;
	}

	public void setTimbreFiscalDigital(TimbreFiscalDigital timbreFiscalDigital) {
		this.timbreFiscalDigital = timbreFiscalDigital;
	}

	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<Ticket> getVentas() {
		return ventas;
	}

	public void setVentas(List<Ticket> ventas) {
		this.ventas = ventas;
	}

	public List<Ticket> getDevoluciones() {
		return devoluciones;
	}

	public void setDevoluciones(List<Ticket> devoluciones) {
		this.devoluciones = devoluciones;
	}

	public byte[] getXmlCfdi() {
		return xmlCfdi;
	}

	public void setXmlCfdi(byte[] xmlCfdi) {
		this.xmlCfdi = xmlCfdi;
	}

	public byte[] getXmlCfdiAcuse() {
		return xmlCfdiAcuse;
	}

	public void setXmlCfdiAcuse(byte[] xmlCfdiAcuse) {
		this.xmlCfdiAcuse = xmlCfdiAcuse;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isVentasMostrador() {
		return ventasMostrador;
	}

	public void setVentasMostrador(boolean ventasMostrador) {
		this.ventasMostrador = ventasMostrador;
	}

	public Integer getId_domicilio() {
		return id_domicilio;
	}

	public void setId_domicilio(Integer id_domicilio) {
		this.id_domicilio = id_domicilio;
	}

	public Documento getDocumentoOrigen() {
		return documentoOrigen;
	}

	public void setDocumentoOrigen(Documento documentoOrigen) {
		this.documentoOrigen = documentoOrigen;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Documento cloned = (Documento) super.clone();
		if (cloned instanceof DocumentoSucursal) {
			((DocumentoSucursal) cloned).setTicket((Ticket) ((DocumentoSucursal) cloned).getTicket().clone());
		}
		return cloned;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Documento [id=");
		builder.append(id);
		builder.append(", comprobante=");
		builder.append(comprobante);
		builder.append(", cliente=");
		builder.append(cliente);
		builder.append(", cadenaOriginal=");
		builder.append(cadenaOriginal);
		builder.append(", fechaFacturacion=");
		builder.append(fechaFacturacion);
		builder.append(", establecimiento=");
		builder.append(establecimiento);
		builder.append(", timbreFiscalDigital=");
		builder.append(timbreFiscalDigital);
		builder.append(", tipoDocumento=");
		builder.append(tipoDocumento);
		builder.append("]");
		return builder.toString();
	}

}
