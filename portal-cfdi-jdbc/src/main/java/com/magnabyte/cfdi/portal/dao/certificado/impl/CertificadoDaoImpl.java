package com.magnabyte.cfdi.portal.dao.certificado.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.certificado.CertificadoDao;
import com.magnabyte.cfdi.portal.dao.certificado.sql.CertificadoSql;

@Repository("certificadoDao")
public class CertificadoDaoImpl extends GenericJdbcDao implements
		CertificadoDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CertificadoDaoImpl.class);

	@Override
	public String obtenerCertificado() {
		String qry = CertificadoSql.READ_CERTIFICADO;
		logger.debug(qry);

		return getJdbcTemplate().queryForObject(qry, String.class);

	}

}
