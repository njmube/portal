package com.magnabyte.cfdi.portal.dao.emisor.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.cfd._3.Comprobante.Emisor;
import mx.gob.sat.cfd._3.Comprobante.Emisor.RegimenFiscal;
import mx.gob.sat.cfd._3.TUbicacion;
import mx.gob.sat.cfd._3.TUbicacionFiscal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.cliente.sql.DomicilioSql;
import com.magnabyte.cfdi.portal.dao.emisor.EmisorDao;
import com.magnabyte.cfdi.portal.dao.emisor.sql.EmisorSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

@Repository
public class EmisorDaoImpl extends GenericJdbcDao implements EmisorDao{

	private static final Logger logger = LoggerFactory.getLogger(EmisorDaoImpl.class);

	@Override
	public EmpresaEmisor read(EmpresaEmisor empresa) {
		String qry = EmisorSql.READ;
		logger.debug(qry);
		return getJdbcTemplate().queryForObject(qry, new Object[]{empresa.getId()}, MAPPER_EMISOR);
	}
	
	@Override
	public TUbicacion readLugarExpedicion(Establecimiento establecimiento) {
		logger.debug(EstablecimientoSql.READ_LUGAR_EXP);
		return getJdbcTemplate().queryForObject(EstablecimientoSql.READ_LUGAR_EXP, MAPPER_LUGAR_EXP, establecimiento.getId());
	}
	
	private static final RowMapper<TUbicacion> MAPPER_LUGAR_EXP = new RowMapper<TUbicacion>() {
		
		@Override
		public TUbicacion mapRow(ResultSet rs, int rowNum) throws SQLException {
			TUbicacion tu = new TUbicacion();
			tu.setCalle(rs.getString(DomicilioSql.CALLE));
			tu.setCodigoPostal(rs.getString(DomicilioSql.CODIGO_POSTAL));
			tu.setColonia(rs.getString(DomicilioSql.COLONIA));
			tu.setEstado(rs.getString(DomicilioSql.AS_ESTADO));
			tu.setLocalidad(rs.getString(DomicilioSql.LOCALIDAD));
			tu.setMunicipio(rs.getString(DomicilioSql.MUNICIPIO));
			tu.setNoExterior(rs.getString(DomicilioSql.NO_EXTERIOR));
			tu.setNoInterior(rs.getString(DomicilioSql.NO_INTERIOR));
			tu.setPais(rs.getString(DomicilioSql.AS_PAIS));
			return tu;
		}
	};
	
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
			ubicacionFiscal.setNoInterior(rs.getString(EmisorSql.NO_INTERIOR).length() < 1 ? null : rs.getString(EmisorSql.NO_INTERIOR));
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
