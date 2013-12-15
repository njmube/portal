package com.magnabyte.cfdi.portal.model.ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TAS")
public class Ticket {

	@XmlElement(name = "NEW_TA", required = true)
	protected Ticket.Transaccion transaccion;
	
	public Ticket.Transaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Ticket.Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Transaccion {
		
		@XmlElement(name = "HEADER", required = true)
		protected Ticket.Transaccion.TransaccionHeader transaccionHeader;
		
		@XmlElement(name = "ART_SALE")
		protected List<Ticket.Transaccion.Partida> partidas;
		
		public Ticket.Transaccion.TransaccionHeader getTransaccionHeader() {
			return transaccionHeader;
		}

		public void setTransaccionHeader(
				Ticket.Transaccion.TransaccionHeader transaccionHeader) {
			this.transaccionHeader = transaccionHeader;
		}

		public List<Ticket.Transaccion.Partida> getPartidas() {
			if (partidas == null) {
				return new ArrayList<Ticket.Transaccion.Partida>();
			}
			return partidas;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		public static class TransaccionHeader {
			
			@XmlElement(name = "lRetailStoreID")
			protected Integer idSucursal;
			
			@XmlElement(name = "lTaNmbr")
			protected Integer idTicket;
			
			@XmlElement(name = "lWorkstationNmbr")
			protected Integer idCaja;

			public Integer getIdSucursal() {
				return idSucursal;
			}

			public void setIdSucursal(Integer idSucursal) {
				this.idSucursal = idSucursal;
			}

			public Integer getIdTicket() {
				return idTicket;
			}

			public void setIdTicket(Integer idTicket) {
				this.idTicket = idTicket;
			}

			public Integer getIdCaja() {
				return idCaja;
			}

			public void setIdCaja(Integer idCaja) {
				this.idCaja = idCaja;
			}

			@Override
			public String toString() {
				return "TransaccionHeader [idSucursal=" + idSucursal
						+ ", idTicket=" + idTicket + ", idCaja=" + idCaja + "]";
			}
			
		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		public static class Partida {
			
			@XmlElement(name = "ARTICLE")
			protected Ticket.Transaccion.Partida.Articulo articulo;
			
			@XmlElement(name = "dTaPrice")
			protected BigDecimal precio;
			
			@XmlElement(name = "dTaQty")
			protected Integer cantidad;
			
			@XmlElement(name = "dTaTotal")
			protected BigDecimal total;
			
			public Ticket.Transaccion.Partida.Articulo getArticulo() {
				return articulo;
			}

			public void setArticulo(Ticket.Transaccion.Partida.Articulo articulo) {
				this.articulo = articulo;
			}

			public BigDecimal getPrecio() {
				return precio;
			}

			public void setPrecio(BigDecimal precio) {
				this.precio = precio;
			}

			public Integer getCantidad() {
				return cantidad;
			}

			public void setCantidad(Integer cantidad) {
				this.cantidad = cantidad;
			}

			public BigDecimal getTotal() {
				return total;
			}

			public void setTotal(BigDecimal total) {
				this.total = total;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			public static class Articulo {
				
				@XmlElement(name = "szPOSItemID")
				protected Integer id;
				
				@XmlElement(name = "szDesc")
				protected String descripcion;

				public Integer getId() {
					return id;
				}

				public void setId(Integer id) {
					this.id = id;
				}

				public String getDescripcion() {
					return descripcion;
				}

				public void setDescripcion(String descripcion) {
					this.descripcion = descripcion;
				}

				@Override
				public String toString() {
					return "Articulo [id=" + id + ", descripcion="
							+ descripcion + "]";
				}
				
			}

			@Override
			public String toString() {
				return "Partida [articulo=" + articulo + ", precio=" + precio
						+ ", cantidad=" + cantidad + ", total=" + total + "]";
			}
			
		}

		@Override
		public String toString() {
			return "Transaccion [transaccionHeader=" + transaccionHeader
					+ ", partidas=" + partidas + "]";
		}
		
	}

	@Override
	public String toString() {
		return "Ticket [transaccion=" + transaccion + "]";
	}
	
}
