package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.establecimiento.EstablecimientoDao;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.SerieFolioEstablecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.TipoEstablecimiento;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;
import com.magnabyte.cfdi.portal.service.establecimiento.DomicilioEstablecimientoService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.establecimiento.RutaEstablecimientoService;
import com.magnabyte.cfdi.portal.service.establecimiento.SerieFolioEstablecimientoService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el servicio de establecimiento
 */
@Service("establecimientoService")
public class EstablecimientoServiceImpl implements EstablecimientoService {

	@Autowired
	private EstablecimientoDao establecimientoDao;
	
	@Autowired
	private  SerieFolioEstablecimientoService serieFolioEstablecimientoService;
	
	@Autowired
	private RutaEstablecimientoService rutaEstablecimientoService;
	
	@Autowired
	private DomicilioEstablecimientoService domicilioEstablecimientoService;
	
	@Transactional(readOnly = true)
	@Override
	public Establecimiento findByClave(Establecimiento establecimiento) {
		return establecimientoDao.findByClave(establecimiento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Establecimiento readByClave(Establecimiento establecimiento) {
		return establecimientoDao.readByClave(establecimiento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Establecimiento read(Establecimiento establecimiento) {
		return establecimientoDao.read(establecimiento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Establecimiento readRutaById(Establecimiento establecimiento) {
		return establecimientoDao.readRutaById(establecimiento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Establecimiento> readAll() {
		return establecimientoDao.readAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Establecimiento readAllById (Establecimiento establecimiento)  {
		return establecimientoDao.readAllById(establecimiento);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<SerieFolioEstablecimiento>  readSerieFolioEstablecimiento(Establecimiento establecimiento) {
		return establecimientoDao.readSerieFolioEstablecimiento(establecimiento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void update (Establecimiento establecimiento) {
		domicilioEstablecimientoService.update(establecimiento.getDomicilio());
		rutaEstablecimientoService.update(establecimiento.getRutaRepositorio());
		establecimientoDao.update(establecimiento);
	}
	
	@Override
	public void updateFechaCierre(Establecimiento establecimiento, String fechaCierre) {
		Date fechaUltimoCierre = FechasUtils
				.parseStringToDate(fechaCierre, FechasUtils.formatddMMyyyyHyphen);
		Calendar fechaCierreTemp = Calendar.getInstance();
		fechaCierreTemp.setTime(fechaUltimoCierre);
		fechaCierreTemp.add(Calendar.DAY_OF_MONTH, 1);
		Date fechaCierreSiguiente = fechaCierreTemp.getTime();
		establecimientoDao.updateFechaCierre(establecimiento, fechaUltimoCierre, fechaCierreSiguiente);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void save (Establecimiento establecimiento) {
//		FIXME Revisar la asignacion del tipo de establecimiento y emisor
		TipoEstablecimiento  tipoEstablecimiento = new TipoEstablecimiento();
		EmpresaEmisor empresaEmisor = new EmpresaEmisor();
		empresaEmisor.setId(1);
		tipoEstablecimiento.setId(2);
		establecimiento.setTipoEstablecimiento(tipoEstablecimiento);
		establecimiento.setEmpresaEmisor(empresaEmisor);
		domicilioEstablecimientoService.save(establecimiento.getDomicilio());
		rutaEstablecimientoService.save(establecimiento.getRutaRepositorio());
		establecimientoDao.save(establecimiento);
		insertSerieFolio(establecimiento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean exist(Establecimiento establecimiento) {
		Establecimiento establecimientoBD = establecimientoDao.findbyName(establecimiento);
		
		if (establecimiento.getId() != null) {
			if (establecimientoBD != null) {
				if(establecimiento.getId().equals(establecimientoBD.getId())){
					return false;
				}
				return true;
			}
		} else {
			if (establecimientoBD != null) {
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	@Override
	public String readFechaCierreById(Establecimiento establecimiento) {
		Establecimiento establecimientoBD = establecimientoDao
				.readFechaCierreById(establecimiento);
		
		if (establecimientoBD.getSiguienteCierre() != null) {
			String fechaCierreSig = FechasUtils.parseDateToString(
					establecimientoBD.getSiguienteCierre(), FechasUtils.formatddMMyyyyHyphen);
			return fechaCierreSig;
		}
		
		return null;
	}
	
	@Override
	public void updateSerieFolio (Establecimiento establecimiento) {
			establecimientoDao.updateSerieFolio(establecimiento);
			insertSerieFolio(establecimiento);
	}
	
	@Override
	public boolean existSerie(String serie) {
		return establecimientoDao.existSerie(serie);
	}
	
	/**
	 * Metodo para insertar en la tabla de Establecimiento serie
	 * @param {@link Establecimiento}
	 */
	@Override
	public void insertSerieFolio (Establecimiento establecimiento){
		for(int i = 0; i < 2; i ++) {
			if (i == 0 ) {
				establecimiento.getSerieFolioEstablecimientoLista().get(i).setTipoDocumento(TipoDocumento.FACTURA);
			} else {
				establecimiento.getSerieFolioEstablecimientoLista().get(i).setTipoDocumento(TipoDocumento.NOTA_CREDITO);
			}
			establecimiento.getSerieFolioEstablecimientoLista().get(i).setEstablecimiento(establecimiento);
			serieFolioEstablecimientoService.save(establecimiento.getSerieFolioEstablecimientoLista().get(i));
		}
	}
	
}
