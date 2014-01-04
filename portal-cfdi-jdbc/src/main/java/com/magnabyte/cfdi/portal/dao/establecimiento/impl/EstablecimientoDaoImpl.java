package com.magnabyte.cfdi.portal.dao.establecimiento.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.EstablecimientoDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;
import com.magnabyte.cfdi.portal.model.establecimiento.TipoEstablecimiento;

@Repository("establecimientoDao")
public class EstablecimientoDaoImpl extends GenericJdbcDao implements
		EstablecimientoDao {

	private static final Logger logger = LoggerFactory
			.getLogger(EstablecimientoDaoImpl.class);

	@Override
	public Establecimiento findByClave(Establecimiento establecimiento) {
		String qry = EstablecimientoSql.FIND_BY_CLAVE;
		logger.debug(qry);

		return getJdbcTemplate().queryForObject(qry, MAPPER_ESTABLECIMIENTO,
				establecimiento.getClave());
	}
	
	@Override
	public Establecimiento readByClave(Establecimiento establecimiento) {
		String qry = EstablecimientoSql.READ_BY_CLAVE;
		logger.debug(qry);

		return getJdbcTemplate().queryForObject(qry, MAPPER_ESTAB_COMPLETO,
				establecimiento.getClave());
	}
	
	@Override
	public Establecimiento readById(Establecimiento establecimiento) {
		String qry = "select * from t_establecimiento as te inner join t_ruta_establecimiento as tre on te.id_ruta_establecimiento = "
				+ "tre.id_ruta_establecimiento where te.id_establecimiento = ?";
		return getJdbcTemplate().queryForObject(qry, new RowMapper<Establecimiento>() {
			@Override
			public Establecimiento mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Establecimiento establecimiento = new Establecimiento();
				RutaRepositorio rutaRepositorio = new RutaRepositorio();

				rutaRepositorio.setRutaRepositorio(rs.getString("ruta_repo"));
				rutaRepositorio.setRutaRepoOut(rs.getString("ruta_out"));
				
				establecimiento.setRutaRepositorio(rutaRepositorio);
				return establecimiento;
			}
		}, establecimiento.getId());
	}

	@Override
	public String getRoles(Establecimiento establecimiento) {
		return getJdbcTemplate().queryForObject(EstablecimientoSql.GET_ROLES,
				new Object[] { establecimiento.getId() }, String.class);
	}
	
	@Override
	public List<Establecimiento> readAll() {
		String qry = EstablecimientoSql.READ_ALL;
		logger.debug("--readAll Establecimientos.  -"+qry);

		return getJdbcTemplate().query(qry, MAPPER_ESTABLECIMIENTO);
	}

	private static final RowMapper<Establecimiento> MAPPER_ESTABLECIMIENTO = new RowMapper<Establecimiento>() {

		@Override
		public Establecimiento mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Establecimiento establecimiento = new Establecimiento();
			//
			establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			establecimiento.setClave(rs.getString(EstablecimientoSql.CLAVE));
			establecimiento.setNombre(rs.getString(EstablecimientoSql.NOMBRE));
			establecimiento.setPassword(rs.getString(EstablecimientoSql.PASSWORD));

			return establecimiento;
		}
	};

	private static final RowMapper<Establecimiento> MAPPER_ESTAB_COMPLETO = new RowMapper<Establecimiento>() {

		@Override
		public Establecimiento mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Establecimiento establecimiento = new Establecimiento();
			EmpresaEmisor empresa = new EmpresaEmisor();
			RutaRepositorio rutaRepo = new RutaRepositorio();
			DomicilioEstablecimiento domEstablecimiento = new DomicilioEstablecimiento();
			TipoEstablecimiento tipoEstablecimiento = new TipoEstablecimiento();

			establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			establecimiento.setClave(String.valueOf(rs.getInt(EstablecimientoSql.CLAVE)));
			establecimiento.setNombre(rs.getString(EstablecimientoSql.NOMBRE));
			
			rutaRepo.setId(rs.getInt(EstablecimientoSql.ID_RUTA_ESTAB));
			rutaRepo.setRutaRepositorio(rs.getString(EstablecimientoSql.RUTA_REPOSITORIO));
			rutaRepo.setRutaRepoIn(rs.getString(EstablecimientoSql.RUTA_IN));
			rutaRepo.setRutaRepoOut(rs.getString(EstablecimientoSql.RUTA_OUT));
			rutaRepo.setRutaRepoInProc(rs.getString(EstablecimientoSql.RUTA_INPROC));
			
			domEstablecimiento.setLocalidad(rs.getString(EstablecimientoSql.LOCALIDAD));
			
			tipoEstablecimiento.setId(rs.getInt(EstablecimientoSql.ID_TIPO_ESTAB));
			tipoEstablecimiento.setNombre(rs.getString(EstablecimientoSql.NOM_ESTAB));
			tipoEstablecimiento.setRol(rs.getString(EstablecimientoSql.ROL));
			
			empresa.setId(rs.getInt(EstablecimientoSql.ID_EMISOR));
			
			establecimiento.setEmisor(empresa);
			establecimiento.setTipoEstablecimiento(tipoEstablecimiento);
			establecimiento.setRutaRepositorio(rutaRepo);
			establecimiento.setDomicilio(domEstablecimiento);

			return establecimiento;
		}
	};

}
