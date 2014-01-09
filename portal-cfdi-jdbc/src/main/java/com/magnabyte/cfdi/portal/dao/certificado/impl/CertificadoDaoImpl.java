package com.magnabyte.cfdi.portal.dao.certificado.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.certificado.CertificadoDao;
import com.magnabyte.cfdi.portal.dao.certificado.sql.CertificadoSql;
import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.exception.PortalException;

@Repository("certificadoDao")
public class CertificadoDaoImpl extends GenericJdbcDao implements
		CertificadoDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CertificadoDaoImpl.class);

	@Override
	public CertificadoDigital readVigente(String fechaComprobante) {
		try {
			return getJdbcTemplate().queryForObject(CertificadoSql.READ_VIGENTE, new RowMapper<CertificadoDigital>() {
				@Override
				public CertificadoDigital mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					CertificadoDigital certificadoDigital = new CertificadoDigital();
					certificadoDigital.setId(rs.getInt("id_cfdi_cer"));
					certificadoDigital.setNumeroCertificado(rs.getString("certificado"));
					certificadoDigital.setRutaCertificado(rs.getString("ruta_cer"));
					certificadoDigital.setNombreCertificado(rs.getString("nombre_cer"));
					certificadoDigital.setRutaKey(rs.getString("ruta_key"));
					certificadoDigital.setNombreKey(rs.getString("nombre_key"));
					certificadoDigital.setInicioVigencia(rs.getDate("fecha_inicio"));
					certificadoDigital.setFinVigencia(rs.getDate("fecha_fin"));
					certificadoDigital.setPassword(rs.getString("passwd"));
					
					return certificadoDigital;
				}
			}, fechaComprobante);
		} catch (EmptyResultDataAccessException ex) {
			logger.error("No hay ningun certificado vigente para realiza la factura.");
			throw new PortalException("No hay ningun certificado vigente para realiza la factura.");
		}
	}
}
