package com.magnabyte.cfdi.portal.dao.emisor.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.cfd._3.Comprobante.Emisor;
import mx.gob.sat.cfd._3.Comprobante.Emisor.RegimenFiscal;
import mx.gob.sat.cfd._3.TUbicacionFiscal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.emisor.EmisorDao;
import com.magnabyte.cfdi.portal.dao.emisor.sql.EmisorSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;

@Repository
public class EmisorDaoImpl extends GenericJdbcDao implements EmisorDao{

	private static final Logger logger = LoggerFactory.getLogger(EmisorDaoImpl.class);

	@Override
	public EmpresaEmisor read(EmpresaEmisor empresa) {
		String qry = EmisorSql.READ;
		logger.debug(qry);
		return getJdbcTemplate().queryForObject(qry, new Object[]{empresa.getId()}, MAPPER_EMISOR);
	}
	
	private static final RowMapper<EmpresaEmisor> MAPPER_EMISOR = new RowMapper<EmpresaEmisor>() {

		@Override
		public EmpresaEmisor mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			EmpresaEmisor empresaEmisor = new EmpresaEmisor();
			TUbicacionFiscal ubicacionFiscal = new TUbicacionFiscal();
			RegimenFiscal regimenFiscal = new RegimenFiscal();
			Emisor emisor = new Emisor();
			
			empresaEmisor.setId(rs.getInt(EstablecimientoSql.ID_EMISOR));
			
			emisor.setNombre(rs.getString(EmisorSql.NOMBRE));
			emisor.setRfc(rs.getString(EmisorSql.RFC));
			
			regimenFiscal.setRegimen(rs.getString(EmisorSql.REGIMEN_FISCAL));
			emisor.getRegimenFiscal().add(regimenFiscal);
			
			ubicacionFiscal.setCalle(rs.getString(EmisorSql.CALLE));
			ubicacionFiscal.setNoExterior(rs.getString(EmisorSql.NO_EXTERIOR));
			ubicacionFiscal.setNoInterior(rs.getString(EmisorSql.NO_INTERIOR));
			ubicacionFiscal.setPais(rs.getString(EmisorSql.PAIS));
			ubicacionFiscal.setEstado(rs.getString(EmisorSql.ESTADO));
			ubicacionFiscal.setMunicipio(rs.getString(EmisorSql.MUNICIPIO));
			ubicacionFiscal.setColonia(rs.getString(EmisorSql.COLONIA));
			ubicacionFiscal.setCodigoPostal(rs.getString(EmisorSql.CODIGO_POSTAL));
			ubicacionFiscal.setLocalidad(rs.getString(EmisorSql.LOCALIDAD));
			emisor.setDomicilioFiscal(ubicacionFiscal);			
			empresaEmisor.setEmisor(emisor);
			
			return empresaEmisor;
		}
	};
	
}
