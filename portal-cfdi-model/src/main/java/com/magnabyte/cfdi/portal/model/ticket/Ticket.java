package com.magnabyte.cfdi.portal.model.ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
		
		@XmlElement(name = "DISC_INFO")
		protected List<Ticket.Transaccion.PartidaDescuento> partidasDescuentos;
		
		@XmlElement(name = "TOTAL")
		protected Ticket.Transaccion.TransaccionTotal transaccionTotal;
		
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
		
		public List<Ticket.Transaccion.PartidaDescuento> getPartidasDescuentos() {
			if (partidasDescuentos == null) {
				return new ArrayList<Ticket.Transaccion.PartidaDescuento>();
			}
			return partidasDescuentos;
		}

		public Ticket.Transaccion.TransaccionTotal getTransaccionTotal() {
			return transaccionTotal;
		}
		
		public void setTransaccionTotal(
				Ticket.Transaccion.TransaccionTotal transaccionTotal) {
			this.transaccionTotal = transaccionTotal;
		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		public static class TransaccionHeader {
			
			@XmlElement(name = "lRetailStoreID")
			protected String idSucursal;
			
			@XmlElement(name = "lTaNmbr")
			protected String idTicket;
			
			@XmlElement(name = "lWorkstationNmbr")
			protected String idCaja;

			@XmlElement(name = "szDate")
			protected String fechaHora;

			@XmlElement(name = "szTaType")
			protected String tipoTransaccion;
			
			@XmlTransient
			protected String fecha;
			
			public String getIdSucursal() {
				return idSucursal;
			}

			public void setIdSucursal(String idSucursal) {
				this.idSucursal = idSucursal;
			}

			public String getIdTicket() {
				return idTicket;
			}

			public void setIdTicket(String idTicket) {
				this.idTicket = idTicket;
			}

			public String getIdCaja() {
				return idCaja;
			}

			public void setIdCaja(String idCaja) {
				this.idCaja = idCaja;
			}

			public String getFechaHora() {
				return fechaHora;
			}

			public void setFechaHora(String fechaHora) {
				this.fechaHora = fechaHora;
			}

			public String getTipoTransaccion() {
				return tipoTransaccion;
			}

			public void setTipoTransaccion(String tipoTransaccion) {
				this.tipoTransaccion = tipoTransaccion;
			}

			public String getFecha() {
				return fecha;
			}

			public void setFecha(String fecha) {
				this.fecha = fecha;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("TransaccionHeader [idSucursal=");
				builder.append(idSucursal);
				builder.append(", idTicket=");
				builder.append(idTicket);
				builder.append(", idCaja=");
				builder.append(idCaja);
				builder.append(", fechaHora=");
				builder.append(fechaHora);
				builder.append(", tipoTransaccion=");
				builder.append(tipoTransaccion);
				builder.append(", fecha=");
				builder.append(fecha);
				builder.append("]");
				return builder.toString();
			}

		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		public static class Partida {
			
			@XmlElement(name = "ARTICLE")
			protected Ticket.Transaccion.Partida.Articulo articulo;
			
			@XmlElement(name = "dTaPrice")
			protected BigDecimal precioUnitario;
			
			@XmlElement(name = "dTaQty")
			protected BigDecimal cantidad;
			
			@XmlElement(name = "dTaTotal")
			protected BigDecimal precioTotal;
			
			public Ticket.Transaccion.Partida.Articulo getArticulo() {
				return articulo;
			}

			public void setArticulo(Ticket.Transaccion.Partida.Articulo articulo) {
				this.articulo = articulo;
			}

			public BigDecimal getPrecioUnitario() {
				return precioUnitario;
			}
			
			public void setPrecioUnitario(BigDecimal precioUnitario) {
				this.precioUnitario = precioUnitario;
			}
		
			public BigDecimal getCantidad() {
				return cantidad;
			}

			public void setCantidad(BigDecimal cantidad) {
				this.cantidad = cantidad;
			}

			public BigDecimal getPrecioTotal() {
				return precioTotal;
			}
			
			public void setPrecioTotal(BigDecimal precioTotal) {
				this.precioTotal = precioTotal;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			public static class Articulo {
				
				@XmlElement(name = "szPOSItemID")
				protected String id;
				
				@XmlElement(name = "szDesc")
				protected String descripcion;

				@XmlElement(name = "szItemCategoryTypeCode")
				protected String tipoCategoria;
				
				@XmlElement(name = "szPOSDepartmentID")
				protected String deptoId;

				public String getId() {
					return id;
				}

				public void setId(String id) {
					this.id = id;
				}

				public String getDescripcion() {
					return descripcion;
				}

				public void setDescripcion(String descripcion) {
					this.descripcion = descripcion;
				}

				public String getTipoCategoria() {
					return tipoCategoria;
				}

				public void setTipoCategoria(String tipoCategoria) {
					this.tipoCategoria = tipoCategoria;
				}

				public String getDeptoId() {
					return deptoId;
				}

				public void setDeptoId(String deptoId) {
					this.deptoId = deptoId;
				}

				@Override
				public String toString() {
					StringBuilder builder = new StringBuilder();
					builder.append("Articulo [id=");
					builder.append(id);
					builder.append(", descripcion=");
					builder.append(descripcion);
					builder.append(", tipoCategoria=");
					builder.append(tipoCategoria);
					builder.append(", deptoId=");
					builder.append(deptoId);
					builder.append("]");
					return builder.toString();
				}
				
			}

			@Override
			public String toString() {
				return "Partida [articulo=" + articulo + ", precioUnitario=" + precioUnitario
						+ ", cantidad=" + cantidad + ", precioTotal=" + precioTotal + "]";
			}
			
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		public static class PartidaDescuento {
			
			@XmlElement(name = "szArtDptNmbr")
			protected String idArticulo;
			
			@XmlElement(name = "dTotalDiscount")
			protected BigDecimal descuentoTotal;
			
			@XmlElement(name = "dDiscValue")
			protected BigDecimal porcentajeDescuento;

			public String getIdArticulo() {
				return idArticulo;
			}

			public void setIdArticulo(String idArticulo) {
				this.idArticulo = idArticulo;
			}

			public BigDecimal getDescuentoTotal() {
				return descuentoTotal;
			}

			public void setDescuentoTotal(BigDecimal descuentoTotal) {
				this.descuentoTotal = descuentoTotal;
			}

			public BigDecimal getPorcentajeDescuento() {
				return porcentajeDescuento;
			}

			public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
				this.porcentajeDescuento = porcentajeDescuento;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("PartidaDescuento [idArticulo=");
				builder.append(idArticulo);
				builder.append(", descuentoTotal=");
				builder.append(descuentoTotal);
				builder.append(", porcentajeDescuento=");
				builder.append(porcentajeDescuento);
				builder.append("]");
				return builder.toString();
			}
			
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		public static class TransaccionTotal {
			
			@XmlElement(name = "dTotalSale")
			protected BigDecimal totalVenta;

			public BigDecimal getTotalVenta() {
				return totalVenta;
			}

			public void setTotalVenta(BigDecimal totalVenta) {
				this.totalVenta = totalVenta;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("TransaccionTotal [totalVenta=");
				builder.append(totalVenta);
				builder.append("]");
				return builder.toString();
			}
			
		}
		

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Transaccion [transaccionHeader=");
			builder.append(transaccionHeader);
			builder.append(", partidas=");
			builder.append(partidas);
			builder.append(", partidasDescuentos=");
			builder.append(partidasDescuentos);
			builder.append(", transaccionTotal=");
			builder.append(transaccionTotal);
			builder.append("]");
			return builder.toString();
		}

		
	}

	@Override
	public String toString() {
		return "Ticket [transaccion=" + transaccion + "]";
	}
	
}
