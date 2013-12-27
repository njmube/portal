package com.magnabyte.cfdi.portal.dao.documento.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import mx.gob.sat.cfd._3.Comprobante.Conceptos.Concepto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.documento.DocumentoDetalleDao;
import com.magnabyte.cfdi.portal.dao.documento.sql.DocumentoDetalleSql;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;

@Repository("documentoDetalleDao")
public class DocumentoDetalleDaoImpl extends GenericJdbcDao
	implements DocumentoDetalleDao {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(DocumentoDetalleDaoImpl.class);

	public void save(final Documento documento) {
		 
		String qry = DocumentoDetalleSql.INSERT_DETALLE_DOC;

		try {

			getJdbcTemplate().batchUpdate(qry,new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							Concepto conceptos = documento.getComprobante()
									.getConceptos().getConcepto().get(i);
							
							ps.setInt(1, documento.getId());
							ps.setBigDecimal(2, conceptos.getImporte());
							ps.setBigDecimal(3, conceptos.getValorUnitario());
							ps.setString(4, conceptos.getDescripcion());
							ps.setString(5, conceptos.getUnidad());
							ps.setBigDecimal(6, conceptos.getCantidad());
						}

						@Override
						public int getBatchSize() {
							return documento.getComprobante().getConceptos()
									.getConcepto().size();
						}
					});
			
		} catch (DataAccessException ex) {
			logger.debug("No se pudo registrar el DocumentoDetalle "
					+ "en la base de datos.",ex);
			throw new PortalException("No se pudo registrar el DocumentoDetalle "
					+ "en la base de datos.",ex);
		}
	}
}
