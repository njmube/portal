package com.magnabyte.cfdi.portal.dao.documento.impl;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.cliente.sql.ClienteSql;
import com.magnabyte.cfdi.portal.dao.documento.DocumentoDao;
import com.magnabyte.cfdi.portal.dao.documento.sql.DocumentoSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Impuestos;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumento;
import com.magnabyte.cfdi.portal.model.documento.TipoEstadoDocumentoPendiente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.TipoEstablecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.tfd.v32.TimbreFiscalDigital;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el acceso a datos de documento
 */
@Repository("documentoDao")
public class DocumentoDaoImpl extends GenericJdbcDao implements DocumentoDao {

	private static final Logger logger = 
			LoggerFactory.getLogger(DocumentoDaoImpl.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public void save(Documento documento) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(DocumentoSql.TABLE_NAME);
			simpleInsert.setGeneratedKeyName(DocumentoSql.ID_DOCUMENTO);
			documento.setId(simpleInsert.executeAndReturnKey(getParameters(documento)).intValue());
		} catch (DataAccessException ex) {			
			logger.debug(messageSource.getMessage("documento.error.save", new Object[] {ex}, null));
			throw new PortalException(messageSource.getMessage("documento.error.save", new Object[] {ex}, null));
		}
	}
	
	@Override
	public void updateDocumentoCliente(DocumentoSucursal documento) {
		getJdbcTemplate().update(DocumentoSql.UPDATE_DOC_CTE, documento.getCliente().getId(), documento.getId());
	}
	
	@Override
	public void updateDocumentoStatus(Documento documento) {
		try {
			int rowsAffected = getJdbcTemplate().update(DocumentoSql.UPDATE_DOC_STATUS, 
					documento.getTipoEstadoDocumento().getId(),
					documento.getId());
			logger.debug("archivos afectados {}", rowsAffected);
		} catch (DataAccessException e) {
			logger.debug(messageSource.getMessage("documento.error.update", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.error.update", new Object[] {e}, null));
		}
	}
	
	@Override
	public void updateDocumentoPendiente(Documento documento,
			TipoEstadoDocumentoPendiente estadoDocumento) {
		try {
			int rowsAffected = getJdbcTemplate().update(DocumentoSql.UPDATE_DOC_PENDIENTE_STATUS, 
					estadoDocumento.getId(), documento.getId());
			logger.debug("archivos afectados {}", rowsAffected);
		} catch (DataAccessException e) {
			logger.debug(messageSource.getMessage("documento.error.update", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.error.update", new Object[] {e}, null));
		}
	}

	@Override
	public void updateDocumentoXmlCfdi(Documento documento) {
		try {
			int rowsAffected = getJdbcTemplate().update(DocumentoSql.UPDATE_DOC_XML_FILE, 
					new String(documento.getXmlCfdi(), PortalUtils.encodingUTF16),
					documento.getId());
			logger.debug("archivos afectados {}", rowsAffected);
		} catch (DataAccessException e) {
			logger.debug(messageSource.getMessage("documento.error.update", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.error.update", new Object[] {e}, null));
		} catch (UnsupportedEncodingException e) {
			logger.debug(messageSource.getMessage("documento.error.update", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.error.update", new Object[] {e}, null));
		}
	}
	
	private MapSqlParameterSource getParameters(Documento documento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DocumentoSql.ID_ESTABLECIMIENTO, documento.getEstablecimiento().getId());
		params.addValue(DocumentoSql.ID_CLIENTE, documento.getCliente().getId());
		if (documento.getId_domicilio() != null) {
			params.addValue(DocumentoSql.ID_DOMICILIO_CLIENTE, documento.getId_domicilio());
		} else if (documento.getCliente().getDomicilios().get(0).getId() != null 
				&& documento.getCliente().getDomicilios().get(0).getId() > 0) {
			params.addValue(DocumentoSql.ID_DOMICILIO_CLIENTE, documento.getCliente().getDomicilios().get(0).getId());
		}
		if(documento instanceof DocumentoCorporativo){
			params.addValue(DocumentoSql.FOLIO_SAP, ((DocumentoCorporativo) documento).getFolioSap());
			params.addValue(DocumentoSql.NIT, ((DocumentoCorporativo) documento).getNit());
		}
		if (documento.getDocumentoOrigen() != null) {
			params.addValue(DocumentoSql.ID_DOCUMENTO_ORIGEN, documento.getDocumentoOrigen().getId());
		}
		params.addValue(DocumentoSql.FECHA_DOCUMENTO, documento.getFechaFacturacion());
		params.addValue(DocumentoSql.TOTAL_DESCUENTO, documento.getComprobante().getDescuento());
		params.addValue(DocumentoSql.SUBTOTAL, documento.getComprobante().getSubTotal());
		params.addValue(DocumentoSql.IVA, documento.getComprobante().getImpuestos().getTotalImpuestosTrasladados());
		params.addValue(DocumentoSql.TOTAL, documento.getComprobante().getTotal());
		params.addValue(DocumentoSql.ID_STATUS, documento.getTipoEstadoDocumento().getId());
		try {
			params.addValue(DocumentoSql.XML_FILE, new String(documento.getXmlCfdi(), PortalUtils.encodingUTF16));
		} catch (UnsupportedEncodingException e) {
			logger.debug(messageSource.getMessage("documento.error.save.xml", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.error.save.xml", new Object[] {e}, null));
		}
		return params;
	}

	@Override
	public void insertDocumentoFolio(Documento documento) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		simpleJdbcInsert.setTableName(DocumentoSql.TABLE_DOC_FOLIO);
		simpleJdbcInsert.execute(getParametersDocumentoFolio(documento));
	}
	
	private MapSqlParameterSource getParametersDocumentoFolio(Documento documento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DocumentoSql.ID_DOCUMENTO, documento.getId());
		params.addValue(DocumentoSql.SERIE, documento.getComprobante().getSerie());
		params.addValue(DocumentoSql.FOLIO, documento.getComprobante().getFolio());
		params.addValue(DocumentoSql.ID_TIPO_DOCUMENTO, documento.getTipoDocumento().getId());
		return params;
	}

	@Override
	public void insertDocumentoCfdi(Documento documento) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		simpleJdbcInsert.setTableName(DocumentoSql.TABLE_DOC_CFDI);
		simpleJdbcInsert.execute(getParametersDocumentoCfdi(documento));
	}
	
	private MapSqlParameterSource getParametersDocumentoCfdi(Documento documento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DocumentoSql.ID_DOCUMENTO, documento.getId());
		params.addValue(DocumentoSql.SELLO_EMISOR, documento.getTimbreFiscalDigital().getSelloSAT());
		params.addValue(DocumentoSql.CADENA, documento.getCadenaOriginal());
		params.addValue(DocumentoSql.SELLO_CFDI, documento.getTimbreFiscalDigital().getSelloCFD());
		params.addValue(DocumentoSql.UUID, documento.getTimbreFiscalDigital().getUUID());
		params.addValue(DocumentoSql.FECHA_HORA, documento.getTimbreFiscalDigital().getFechaTimbrado().toGregorianCalendar().getTime());
		return params;
	}
	
	@Override
	public void insertDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumento) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		simpleJdbcInsert.setTableName(DocumentoSql.TABLE_DOC_PEND);
		simpleJdbcInsert.execute(getParametersDocumentoPendiente(documento, estadoDocumento));
	}

	private MapSqlParameterSource getParametersDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DocumentoSql.ID_DOCUMENTO, documento.getId());
		params.addValue(DocumentoSql.SERIE, documento.getComprobante().getSerie());
		params.addValue(DocumentoSql.FOLIO, documento.getComprobante().getFolio());
		params.addValue(DocumentoSql.ID_TIPO_DOCUMENTO, documento.getTipoDocumento().getId());
		params.addValue(DocumentoSql.ID_ESTABLECIMIENTO, documento.getEstablecimiento().getId());
		params.addValue(DocumentoSql.ID_ESTADO_DOC, estadoDocumento.getId());
		return params;
	}
	
	@Override
	public Cliente readClienteFromDocumento(Documento documentoOrigen) {
		try {
			return getJdbcTemplate().queryForObject(DocumentoSql.READ_CLIENTE_FROM_DOC, new RowMapper<Cliente>() {
				@Override
				public Cliente mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Cliente cliente = new Cliente();
					DomicilioCliente domicilioCliente = new DomicilioCliente();
					List<DomicilioCliente> domicilios = new ArrayList<DomicilioCliente>();
					
					cliente.setId(rs.getInt(ClienteSql.ID_CLIENTE));
					cliente.setRfc(rs.getString(ClienteSql.RFC));
					cliente.setNombre(rs.getString(ClienteSql.NOMBRE));
					domicilioCliente.setId(rs.getInt(ClienteSql.ID_DOM_CLIENTE));
					domicilios.add(domicilioCliente);
					cliente.setDomicilios(domicilios);
					return cliente;
				}
			}, documentoOrigen.getId());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	@Override
	public Documento readDocumentoPendiente(Documento documento, TipoEstadoDocumentoPendiente estadoDocumento) {
		try {
			return getJdbcTemplate().queryForObject(DocumentoSql.READ_DOC_BY_ID_AND_ESTADO, new RowMapper<Documento>() {
				@Override
				public Documento mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Documento documento = new Documento();
					documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
					return documento;
				}
			}, documento.getId(), estadoDocumento.getId());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	@Override
	public List<Documento> obtenerAcusesPendientes() {
		try {
			return getJdbcTemplate().query(DocumentoSql.READ_ACUSE_PEND, new RowMapper<Documento>() {
				@Override
				public Documento mapRow(ResultSet rs, int rowNum) throws SQLException {
					Documento documento = new Documento();
					TimbreFiscalDigital timbreFiscalDigital = new TimbreFiscalDigital();
					Establecimiento establecimiento = new Establecimiento();
					Comprobante comprobante = new Comprobante();
					
					documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
					documento.setTipoDocumento(TipoDocumento.getById(rs.getInt(DocumentoSql.ID_TIPO_DOCUMENTO)));
					comprobante.setSerie(rs.getString(DocumentoSql.SERIE));
					comprobante.setFolio(rs.getString(DocumentoSql.FOLIO));
					establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
					timbreFiscalDigital.setUUID(rs.getString(DocumentoSql.UUID));
					documento.setTimbreFiscalDigital(timbreFiscalDigital);
					documento.setComprobante(comprobante);
					documento.setEstablecimiento(establecimiento);
					return documento;
				}
			}, TipoEstadoDocumentoPendiente.ACUSE_PENDIENTE.getId());
		} catch (EmptyResultDataAccessException ex) {
			logger.debug("No hay acuses pendientes");
			return null;
		}
	}
	
	@Override
	public List<Documento> obtenerDocumentosTimbrePendientes() {
		try {
			return getJdbcTemplate().query(DocumentoSql.READ_DOCUMENTOS_PENDIENTES, new RowMapper<Documento>() {
				@Override
				public Documento mapRow(ResultSet rs, int rowNum) throws SQLException {
					Documento documento = new Documento();
					Establecimiento establecimiento = new Establecimiento();
					Comprobante comprobante = new Comprobante();
					
					documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
					documento.setTipoDocumento(TipoDocumento.getById(rs.getInt(DocumentoSql.ID_TIPO_DOCUMENTO)));
					comprobante.setSerie(rs.getString(DocumentoSql.SERIE));
					comprobante.setFolio(rs.getString(DocumentoSql.FOLIO));
					establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
					documento.setComprobante(comprobante);
					documento.setEstablecimiento(establecimiento);
					return documento;
				}
			}, TipoEstadoDocumentoPendiente.TIMBRE_PENDIENTE.getId());
		} catch (EmptyResultDataAccessException ex) {
			logger.debug("No hay acuses pendientes");
			return null;
		}
	}

	@Override
	public List<Documento> getNombreDocumentoFacturado(List<Integer> idDocumentos, Integer[] tiposDocumento) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idDocumentos", idDocumentos);
		map.addValue("idTiposDocumento", Arrays.asList(tiposDocumento));
		return getNamedParameterJdbcTemplate().query(DocumentoSql.READ_DOCUMENTOS_FACTURADOS,
				map, DOCUMENTO_FACTURADO_MAPPER);
	}
	
	private static final RowMapper<Documento> DOCUMENTO_FACTURADO_MAPPER = new RowMapper<Documento>() {

		@Override
		public Documento mapRow(ResultSet rs, int rowNum) throws SQLException {
			Documento documento = new Documento();
			Comprobante comprobante = new Comprobante();
			
			comprobante.setFolio(rs.getString(DocumentoSql.FOLIO));
			comprobante.setSerie(rs.getString(DocumentoSql.SERIE));
			documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
			documento.setTipoDocumento(TipoDocumento.FACTURA);
			documento.setId_domicilio(rs.getInt(DocumentoSql.ID_DOMICILIO_CLIENTE));
			documento.setComprobante(comprobante);
			return documento;
		}
	};
	
	private static final RowMapper<Documento> DOCUMENTO_MAPPER = new RowMapper<Documento>() {

		@Override
		public Documento mapRow(ResultSet rs, int rowNum) throws SQLException {
			Documento documento = null;
			Documento documentoOrigen = null;
			String folioSap = rs.getString(DocumentoSql.FOLIO_SAP);
			String nit = rs.getString(DocumentoSql.NIT);
			
			if(folioSap != null) {
				documento  = new DocumentoCorporativo();
				((DocumentoCorporativo) documento).setFolioSap(folioSap);
				((DocumentoCorporativo) documento).setNit(nit);
			} else {
				documento = new DocumentoSucursal();
			}
			Integer idDocumentoOrigen = rs.getInt(DocumentoSql.ID_DOCUMENTO_ORIGEN);
			if (idDocumentoOrigen > 0) {
				documentoOrigen = new Documento();
				documentoOrigen.setId(idDocumentoOrigen);
				documento.setDocumentoOrigen(documentoOrigen);
			}
			Establecimiento establecimiento = new Establecimiento();
			Cliente cliente = new Cliente();
			Comprobante comprobante = new Comprobante();
			Impuestos impuesto = new Impuestos();
		
			documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
			establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			documento.setFechaFacturacion(rs.getDate(DocumentoSql.FECHA_DOCUMENTO));
			cliente.setId(rs.getInt(ClienteSql.ID_CLIENTE));
			comprobante.setSubTotal(rs.getBigDecimal(DocumentoSql.SUBTOTAL));
			impuesto.setTotalImpuestosTrasladados(rs.getBigDecimal(DocumentoSql.IVA));
			comprobante.setTotal(rs.getBigDecimal(DocumentoSql.TOTAL));
			comprobante.setDescuento(rs.getBigDecimal(DocumentoSql.TOTAL_DESCUENTO));
			documento.setId_domicilio(rs.getInt(DocumentoSql.ID_DOMICILIO_CLIENTE));
			
			comprobante.setImpuestos(impuesto);
			documento.setCliente(cliente);
			documento.setEstablecimiento(establecimiento);
			documento.setComprobante(comprobante);
			SQLXML xmlFile = rs.getSQLXML(DocumentoSql.XML_FILE);
			if (xmlFile != null) {
				try {
					documento.setXmlCfdi(xmlFile.getString().getBytes(PortalUtils.encodingUTF16));
				} catch (UnsupportedEncodingException e) {
					logger.debug("Ocurrió un error al leer el acuse xml.", e);
					throw new PortalException("Ocurrió un error al leer el acuse xml.", e);
				}
			}
			return documento;
		}
	};
	
	private static final RowMapper<Documento> DOCUMENTO_RUTA_MAPPER = new RowMapper<Documento>() {
		@Override
		public Documento mapRow(ResultSet rs, int rowNum) throws SQLException {
			Documento documento = new Documento();
			Establecimiento establecimiento = new Establecimiento();
			
			establecimiento.setId(rs.getInt(DocumentoSql.ID_ESTABLECIMIENTO));
			documento.setEstablecimiento(establecimiento);
			documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
			return documento;
		}
	};

	@Override
	public List<Documento> getDocumentoByCliente(Cliente cliente, String fechaInicial, String fechaFinal, String idEstablecimiento) {
		return getJdbcTemplate().query(DocumentoSql.READ_DOCUMENTO_RUTA, 
				DOCUMENTO_RUTA_MAPPER, cliente.getRfc(), 
				new java.sql.Date(FechasUtils.parseStringToDate(fechaInicial, FechasUtils.formatddMMyyyyHyphen).getTime()),
				new java.sql.Date(FechasUtils.parseStringToDate(fechaFinal, FechasUtils.formatddMMyyyyHyphen).getTime()),
				TipoEstadoDocumento.PROCESADO.getId(), idEstablecimiento);
	}

	@Override
	public Documento read(Documento documento) {
		return getJdbcTemplate().queryForObject(DocumentoSql.READ_DOCUMENTO_BY_ID, DOCUMENTO_MAPPER, documento.getId());
	}
	
	@Override
	public Documento readDocumentoFolio(Documento documento) {
		try {
			return getJdbcTemplate().queryForObject(DocumentoSql.READ_DOCFOLIO_BY_SERIE_FOLIO, new RowMapper<Documento>() {
				@Override
				public Documento mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Documento documento = new Documento();
					documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
					documento.setTipoDocumento(TipoDocumento.getById(rs.getInt(DocumentoSql.ID_TIPO_DOCUMENTO)));
					return documento;
				}
			}, documento.getComprobante().getSerie(), documento.getComprobante().getFolio());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	@Override
	public Documento readDocumentoFolioById(Documento documento) {
		try {
			return getJdbcTemplate().queryForObject(DocumentoSql.READ_DOCFOLIO_BY_ID, new RowMapper<Documento>() {
				@Override
				public Documento mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Documento documento = new Documento();
					Comprobante comprobante = new Comprobante();
					documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
					comprobante.setSerie(rs.getString(DocumentoSql.SERIE));
					comprobante.setFolio(rs.getString(DocumentoSql.FOLIO));
					documento.setTipoDocumento(TipoDocumento.getById(rs.getInt(DocumentoSql.ID_TIPO_DOCUMENTO)));
					documento.setComprobante(comprobante);
					return documento;
				}
			}, documento.getId());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	@Override
	public Documento readDocumentoCfdiById(Documento documento) {
		try {
			return getJdbcTemplate().queryForObject(DocumentoSql.READ_DOC_CFDI, new RowMapper<Documento>() {
				@Override
				public Documento mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Documento documento = new Documento();
					TimbreFiscalDigital timbreFiscalDigital = new TimbreFiscalDigital();
					documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
					timbreFiscalDigital.setSelloCFD(rs.getString(DocumentoSql.SELLO_CFDI));
					timbreFiscalDigital.setSelloSAT(rs.getString(DocumentoSql.SELLO_EMISOR));
					timbreFiscalDigital.setUUID(rs.getString(DocumentoSql.UUID));
					GregorianCalendar fechaTimbrado = (GregorianCalendar) GregorianCalendar.getInstance();
					fechaTimbrado.setTime(rs.getTimestamp(DocumentoSql.FECHA_HORA));
					try {
						XMLGregorianCalendar fechaTimbradoXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaTimbrado);
						timbreFiscalDigital.setFechaTimbrado(fechaTimbradoXml);
					} catch (DatatypeConfigurationException e) {
						logger.error(messageSource.getMessage("documento.error.fecha.sql", new Object[] {e}, null));
						throw new PortalException(messageSource.getMessage("documento.error.fecha.sql", new Object[] {e}, null));
					}
					documento.setTimbreFiscalDigital(timbreFiscalDigital);
					documento.setCadenaOriginal(rs.getString(DocumentoSql.CADENA));
					return documento;
				}
			}, documento.getId());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	@Override
	public void deletedDocumentoPendiente(Documento documento, 
			TipoEstadoDocumentoPendiente estadoDocumentoPendiente) {		
		getJdbcTemplate().update(DocumentoSql.DELETE_DOCUMENTO_PENDIENTE_BY_STATUS, 
				documento.getId(), estadoDocumentoPendiente.getId());
	}
	
	@Override
	public void saveAcuseCfdiXmlFile(Documento documento) {
		try {
			getJdbcTemplate().update(DocumentoSql.SAVE_ACUSE, 
					new String(documento.getXmlCfdiAcuse(), PortalUtils.encodingUTF16), 
					documento.getId());
		} catch (UnsupportedEncodingException e) {
			logger.debug(messageSource.getMessage("documento.error.update.acusexml", new Object[] {e}, null));
			throw new PortalException(messageSource.getMessage("documento.error.update.acusexml", new Object[] {e}, null));
		}
	}
	
	@Override
	public Documento findBySerie(final Documento documento) {
		try {
			return getJdbcTemplate().queryForObject(DocumentoSql.READ_BY_SERIE, new RowMapper<Documento>() {
				
				@Override
				public Documento mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Establecimiento establecimiento = new Establecimiento();
					establecimiento.setId(rs.getInt(DocumentoSql.ID_ESTABLECIMIENTO));
					TipoEstablecimiento tipoEstablecimiento = new TipoEstablecimiento();
					tipoEstablecimiento.setId(rs.getInt(EstablecimientoSql.ID_TIPO_ESTAB));
					tipoEstablecimiento.setRol(rs.getString(EstablecimientoSql.ROL));
					establecimiento.setTipoEstablecimiento(tipoEstablecimiento);
					TipoDocumento tipoDocumento = TipoDocumento.getById(rs.getInt(DocumentoSql.ID_TIPO_DOCUMENTO));
					documento.setTipoDocumento(tipoDocumento);
					documento.setEstablecimiento(establecimiento);
					return documento;
				}
				
			}, documento.getComprobante().getSerie());
		} catch (EmptyResultDataAccessException ex) {
			throw new PortalException(messageSource.getMessage("documento.serie.inexistente", null, null));
		}
	}
	
	@Override
	public Documento findBySerieFolioImporte(final Documento documento) {
		try {
			return getJdbcTemplate().queryForObject(DocumentoSql.READ_BY_SERIE_FOLIO_IMPORTE, new RowMapper<Documento>() {
				
				@Override
				public Documento mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					documento.setId(rs.getInt(DocumentoSql.ID_DOCUMENTO));
					Cliente cliente = new Cliente();
					cliente.setId(rs.getInt(DocumentoSql.ID_CLIENTE));
					documento.setCliente(cliente);
					documento.setId_domicilio(rs.getInt(DocumentoSql.ID_DOMICILIO_CLIENTE));
					documento.setTipoEstadoDocumento(TipoEstadoDocumento.getById(rs.getInt(DocumentoSql.ID_STATUS)));
					SQLXML xmlFile = rs.getSQLXML(DocumentoSql.XML_FILE);
					if (xmlFile != null) {
						try {
							documento.setXmlCfdi(xmlFile.getString().getBytes(PortalUtils.encodingUTF16));
						} catch (UnsupportedEncodingException e) {
							logger.debug(messageSource.getMessage("documento.error.lectura.acuse", new Object[] {e}, null));
							throw new PortalException(messageSource.getMessage("documento.error.lectura.acuse", new Object[] {e}, null));
						}
					}
					return documento;
				}
				
			}, documento.getComprobante().getSerie(), documento.getComprobante().getFolio(), documento.getComprobante().getTotal());
		} catch (EmptyResultDataAccessException ex) {
			throw new PortalException(messageSource.getMessage("documento.refacturacion.error", null, null));
		}
	}
}
