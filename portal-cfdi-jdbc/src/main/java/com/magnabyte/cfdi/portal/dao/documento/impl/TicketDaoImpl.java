package com.magnabyte.cfdi.portal.dao.documento.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.documento.TicketDao;
import com.magnabyte.cfdi.portal.dao.documento.sql.DocumentoSql;
import com.magnabyte.cfdi.portal.dao.documento.sql.TicketSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion;
import com.magnabyte.cfdi.portal.model.ticket.Ticket.Transaccion.TransaccionHeader;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el acceso a datos de ticket
 */
@Repository("ticketDao")
public class TicketDaoImpl extends GenericJdbcDao 
	implements TicketDao {

	private static final Logger logger = 
			LoggerFactory.getLogger(TicketDaoImpl.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public void save(DocumentoSucursal documento) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(TicketSql.TABLE_NAME);
			simpleInsert.setGeneratedKeyName(TicketSql.ID_TICKET);
			documento.getTicket().setId(simpleInsert.executeAndReturnKey(getParameters(documento)).intValue());
		} catch (DataAccessException ex) {			
			logger.debug(messageSource.getMessage("ticket.error.save", new Object[] {ex}, null));
			throw new PortalException(messageSource.getMessage("ticket.error.save", new Object[] {ex}, null));
		}
	}
	
	@Override
	public void saveTicketsCierreDia(final Documento documento, final TipoEstadoTicket estadoTicket) {
		int[] insertCounts = getJdbcTemplate().batchUpdate(TicketSql.SAVE_TICKETS_CIERRE, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(documento.getVentas().get(i).getTransaccion().getTransaccionHeader().getIdTicket()));
				ps.setInt(2, documento.getEstablecimiento().getId());
				ps.setInt(3, Integer.parseInt(documento.getVentas().get(i).getTransaccion().getTransaccionHeader().getIdCaja()));
				ps.setTimestamp(4, new java.sql.Timestamp(FechasUtils.parseStringToDate(documento.getVentas().get(i).
						getTransaccion().getTransaccionHeader().getFechaHora(), FechasUtils.formatyyyyMMddHHmmss).getTime()));
				ps.setInt(5, estadoTicket.getId());
				ps.setInt(6, documento.getId());
				ps.setString(7, documento.getVentas().get(i).getNombreArchivo());
			}
			
			@Override
			public int getBatchSize() {
				return documento.getVentas().size();
			}
		});
		logger.info("tickets guardados {}", insertCounts.length);
	}
	
	@Override
	public void updateEstado(DocumentoSucursal documento) {
		getJdbcTemplate().update(TicketSql.UPDATE_STATUS, 
				documento.getTicket().getTipoEstadoTicket().getId(), documento.getTicket().getId());
	}
	
	@Override
	public List<String> readArticulosSinPrecio() {
		return getJdbcTemplate().queryForList(TicketSql.READ_ART_SIN_PRECIO, String.class);
	}
	
	@Override
	public List<String> readAllByDate(String fecha) {
		logger.debug(fecha);
//		select distinct dbo.TRIM(nombre_archivo) as nombre_archivo from t_ticket where convert(varchar(10), fecha_ticket, 120) = '2013-12-07'
//				and (id_status_ticket = 1 or id_status_ticket = 2)
		return getJdbcTemplate().queryForList(TicketSql.READ_FACTURADOS_DIA, 
				String.class, 
				new java.sql.Date(FechasUtils.parseStringToDate(fecha, FechasUtils.formatyyyyMMddHyphen).getTime()), 
				TipoEstadoTicket.FACTURADO.getId());
	}
	
	@Override
	public Ticket read(Ticket ticket, Establecimiento establecimiento) {
		try {
			return getJdbcTemplate().queryForObject(TicketSql.READ, MAPPER_TICKET, 
					ticket.getTransaccion().getTransaccionHeader().getIdTicket(), establecimiento.getId(),
					ticket.getTransaccion().getTransaccionHeader().getIdCaja());
		} catch (EmptyResultDataAccessException ex) {
			logger.debug("El ticket no se ha guardado");
			return null;
		}
	}
	
	@Override
	public Ticket readByDocumento(Documento documento) {
		try {
			return getJdbcTemplate().queryForObject(TicketSql.READ_BY_DOC, MAPPER_TICKET, 
					documento.getId(), TipoEstadoTicket.FACTURADO.getId());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	@Override
	public Ticket findByDocumento(Documento documento) {
		try {
			return getJdbcTemplate().queryForObject(TicketSql.FIND_BY_DOC, MAPPER_FULL_TICKET, 
					documento.getId());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	@Override
	public Integer readIdDocFromTicketFacturado(DocumentoSucursal documento) {
		return getJdbcTemplate().queryForObject(TicketSql.READ_ID_DOC_BY_TICKET, Integer.class,
				documento.getTicket().getTransaccion().getTransaccionHeader().getIdTicket(), 
				documento.getEstablecimiento().getId(),
				documento.getTicket().getTransaccion().getTransaccionHeader().getIdCaja(),
				TipoEstadoTicket.FACTURADO.getId());
	}
	
	@Override
	public Ticket readByStatus(Ticket ticket, Establecimiento establecimiento, TipoEstadoTicket estadoTicket) {
		try {
			return getJdbcTemplate().queryForObject(TicketSql.READ_BY_STATUS, MAPPER_TICKET, 
					ticket.getTransaccion().getTransaccionHeader().getIdTicket(), establecimiento.getId(),
					ticket.getTransaccion().getTransaccionHeader().getIdCaja(),
					estadoTicket.getId());
		} catch (EmptyResultDataAccessException ex) {
			logger.debug("El ticket no se ha encontrado");
			return null;
		}
	}
	
	@Override
	public DocumentoSucursal readDocByStatus(String archivoOrigen,
			TipoEstadoTicket estadoTicket) {
		try {
			return getJdbcTemplate().queryForObject(TicketSql.READ_BY_STATUS_FILENAME, MAPPER_DOC_TICKET, 
					archivoOrigen,
					estadoTicket.getId());
		} catch (EmptyResultDataAccessException ex) {
			logger.debug("El ticket no se ha encontrado");
			return null;
		}
	}
	
	//FIXME Revisar si se usa
	@Override
	public int readProcesado(String archivoOrigen,
			TipoEstadoTicket facturado, TipoEstadoTicket facturadoMostrador) {
		return getJdbcTemplate().queryForObject(TicketSql.READ_BY_STATUS_FILENAME, 
				Integer.class, archivoOrigen, facturado.getId(), facturadoMostrador.getId());
	}
	
	private static final RowMapper<DocumentoSucursal> MAPPER_DOC_TICKET = new RowMapper<DocumentoSucursal>() {
		
		@Override
		public DocumentoSucursal mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			DocumentoSucursal documento = new DocumentoSucursal();
			Ticket ticket = new Ticket();
			Transaccion transaccion = new Transaccion();
			TransaccionHeader transaccionHeader = new TransaccionHeader();
			Establecimiento establecimiento = new Establecimiento();
			documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
			ticket.setId(rs.getInt(TicketSql.ID_TICKET));
			
			transaccionHeader.setIdTicket(rs.getString(TicketSql.NO_TICKET));
			transaccionHeader.setIdCaja(rs.getString(TicketSql.NO_CAJA));
			transaccionHeader.setIdSucursal(rs.getString(EstablecimientoSql.ID_ESTABLECIMIENTO));
			establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			ticket.setEstablecimiento(establecimiento);
			
			transaccionHeader.setFecha(FechasUtils.parseDateToString(rs.getTimestamp(TicketSql.FECHA), 
					FechasUtils.formatyyyyMMddHHmmssHyphen));
			transaccionHeader.setFechaHora(FechasUtils.specificStringFormatDate(transaccionHeader.getFecha(), 
					FechasUtils.formatyyyyMMddHHmmssHyphen, FechasUtils.formatddMMyyyyHHmmssSlash));
			transaccion.setTransaccionHeader(transaccionHeader);
			ticket.setTransaccion(transaccion);
			ticket.setNombreArchivo(rs.getString(TicketSql.FILENAME));
			ticket.setTipoEstadoTicket(TipoEstadoTicket.getById(rs.getInt(TicketSql.ID_STATUS)));
			
			documento.setTicket(ticket);
			return documento;
		}
	};
	
	private static final RowMapper<Ticket> MAPPER_TICKET = new RowMapper<Ticket>() {
		
		@Override
		public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ticket ticket = new Ticket();
			ticket.setId(rs.getInt(TicketSql.ID_TICKET));
			ticket.setTipoEstadoTicket(TipoEstadoTicket.getById(rs.getInt(TicketSql.ID_STATUS)));
			return ticket;
		}
		
	};
	
	private static final RowMapper<Ticket> MAPPER_FULL_TICKET = new RowMapper<Ticket>() {
		
		@Override
		public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ticket ticket = new Ticket();
			Transaccion transaccion = new Transaccion();
			TransaccionHeader transaccionHeader = new TransaccionHeader();
			Establecimiento establecimiento = new Establecimiento();
			ticket.setId(rs.getInt(TicketSql.ID_TICKET));
			transaccionHeader.setIdTicket(rs.getString(TicketSql.NO_TICKET));
			transaccionHeader.setIdCaja(rs.getString(TicketSql.NO_CAJA));
			transaccionHeader.setIdSucursal(rs.getString(EstablecimientoSql.ID_ESTABLECIMIENTO));
			establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			ticket.setEstablecimiento(establecimiento);
			
			transaccionHeader.setFecha(FechasUtils.parseDateToString(rs.getTimestamp(TicketSql.FECHA), 
					FechasUtils.formatyyyyMMddHHmmssHyphen));
			transaccionHeader.setFechaHora(FechasUtils.specificStringFormatDate(transaccionHeader.getFecha(), 
					FechasUtils.formatyyyyMMddHHmmssHyphen, FechasUtils.formatddMMyyyyHHmmssSlash));
			transaccion.setTransaccionHeader(transaccionHeader);
			ticket.setTransaccion(transaccion);
			ticket.setNombreArchivo(rs.getString(TicketSql.FILENAME));
			ticket.setTipoEstadoTicket(TipoEstadoTicket.getById(rs.getInt(TicketSql.ID_STATUS)));
			return ticket;
		}
		
	};
	
	private MapSqlParameterSource getParameters(DocumentoSucursal documento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DocumentoSql.ID_DOCUMENTO, documento.getId());
		params.addValue(EstablecimientoSql.ID_ESTABLECIMIENTO, documento.getTicket().getEstablecimiento().getId());
		params.addValue(TicketSql.ID_STATUS, documento.getTicket().getTipoEstadoTicket().getId());
		params.addValue(TicketSql.FECHA, FechasUtils.parseStringToDate(documento
				.getTicket().getTransaccion().getTransaccionHeader().getFechaHora(), 
				FechasUtils.formatddMMyyyyHHmmssSlash));
		params.addValue(TicketSql.NO_CAJA, documento.getTicket().getTransaccion().getTransaccionHeader().getIdCaja());
		params.addValue(TicketSql.NO_TICKET, documento.getTicket().getTransaccion().getTransaccionHeader().getIdTicket());
		params.addValue(TicketSql.FILENAME, documento.getTicket().getNombreArchivo());
		
		return params;
	}

}
