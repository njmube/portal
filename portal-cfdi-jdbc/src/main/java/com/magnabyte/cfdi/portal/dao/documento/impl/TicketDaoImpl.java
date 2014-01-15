package com.magnabyte.cfdi.portal.dao.documento.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;

@Repository("ticketDao")
public class TicketDaoImpl extends GenericJdbcDao 
	implements TicketDao {

	private static final Logger logger = 
			LoggerFactory.getLogger(TicketDaoImpl.class);
	
	@Override
	public void save(DocumentoSucursal documento) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(TicketSql.TABLE_NAME);
			simpleInsert.setGeneratedKeyName(TicketSql.ID_TICKET);
			documento.getTicket().setId(simpleInsert.executeAndReturnKey(getParameters(documento)).intValue());
		} catch (DataAccessException ex) {			
			logger.debug("No se pudo registrar el Ticket en la base de datos.", ex);
			throw new PortalException("No se pudo registrar el Ticket en la base de datos.", ex);
		}
	}
	
	@Override
	public void saveTicketVentasMostrador(final Documento documento) {
		int[] insertCounts = getJdbcTemplate().batchUpdate(TicketSql.SAVE_TICKETS_VM, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(documento.getVentas().get(i).getTransaccion().getTransaccionHeader().getIdTicket()));
				ps.setInt(2, documento.getEstablecimiento().getId());
				ps.setInt(3, Integer.parseInt(documento.getVentas().get(i).getTransaccion().getTransaccionHeader().getIdCaja()));
				ps.setString(4, FechasUtils.specificStringFormatDate(documento.getVentas().get(i).
						getTransaccion().getTransaccionHeader().getFechaHora(), 
						"yyyyMMddHHmmss", "dd/MM/yyyy HH:mm:ss"));
				ps.setInt(5, TipoEstadoTicket.FACTURADO_MOSTRADOR.getId());
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
		return getJdbcTemplate().queryForList(TicketSql.READ_FACTURADOS_DIA, String.class, fecha);
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
	public Integer readIdDocFromTicketGuardado(DocumentoSucursal documento) {
		return getJdbcTemplate().queryForObject(TicketSql.READ_ID_DOC_BY_TICKET, Integer.class,
				documento.getTicket().getTransaccion().getTransaccionHeader().getIdTicket(), 
				documento.getEstablecimiento().getId(),
				documento.getTicket().getTransaccion().getTransaccionHeader().getIdCaja(),
				TipoEstadoTicket.GUARDADO.getId());
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
	public int readProcesado(String archivoOrigen,
			TipoEstadoTicket facturado, TipoEstadoTicket facturadoMostrador) {
		return getJdbcTemplate().queryForObject(TicketSql.READ_PROCESADO, 
				Integer.class, archivoOrigen, facturado.getId(), facturadoMostrador.getId());
	}
	
	private static final RowMapper<Ticket> MAPPER_TICKET = new RowMapper<Ticket>() {
		
		@Override
		public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ticket ticket = new Ticket();
			ticket.setId(rs.getInt(TicketSql.ID_TICKET));
			ticket.setTipoEstadoTicket(TipoEstadoTicket.getById(rs.getInt(TicketSql.ID_STATUS)));
			return ticket;
		}
	};
	
	private MapSqlParameterSource getParameters(DocumentoSucursal documento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DocumentoSql.ID_DOCUMENTO, documento.getId());
		params.addValue(EstablecimientoSql.ID_ESTABLECIMIENTO, documento.getEstablecimiento().getId());
		params.addValue(TicketSql.ID_STATUS, documento.getTicket().getTipoEstadoTicket().getId());
		params.addValue(TicketSql.FECHA, documento.getTicket().getTransaccion().getTransaccionHeader().getFechaHora());
		params.addValue(TicketSql.NO_CAJA, documento.getTicket().getTransaccion().getTransaccionHeader().getIdCaja());
		params.addValue(TicketSql.NO_TICKET, documento.getTicket().getTransaccion().getTransaccionHeader().getIdTicket());
		params.addValue(TicketSql.FILENAME, documento.getTicket().getNombreArchivo());
		
		return params;
	}

}
