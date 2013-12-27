package com.magnabyte.cfdi.portal.dao.documento.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.documento.TicketDao;
import com.magnabyte.cfdi.portal.dao.documento.sql.TicketSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.exception.PortalException;

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
			logger.debug("No se pudo registrar el Documento en la base de datos.", ex);
			throw new PortalException("No se pudo registrar el Documento en la base de datos.", ex);
		}
	}
	
	private MapSqlParameterSource getParameters(DocumentoSucursal documento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(EstablecimientoSql.ID_ESTABLECIMIENTO, documento.getEstablecimiento().getId());
		params.addValue(TicketSql.ID_STATUS, documento.getTicket().getStatus().getId());
		params.addValue(TicketSql.FECHA, new Date());
		params.addValue(TicketSql.NO_CAJA, documento.getTicket().getTransaccion().getTransaccionHeader().getIdCaja());
		params.addValue(TicketSql.NO_TICKET, documento.getTicket().getTransaccion().getTransaccionHeader().getIdTicket());
		
		return params;
	}

}
