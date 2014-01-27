package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.establecimiento.EstablecimientoDao;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;

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
	public void update (Establecimiento establecimiento) {
		establecimientoDao.update(establecimiento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void save (Establecimiento establecimiento) {
		establecimientoDao.save(establecimiento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean exist(Establecimiento establecimiento) {
		Establecimiento esta = establecimientoDao.findbyName(establecimiento);
		
		if (establecimiento.getId() != null) {
			if (esta != null) {
				if(establecimiento.getId().equals(esta.getId())){
					return false;
				}
				return true;
			}
		} else {
			if (esta != null) {
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	@Override
	public String readFechaCierreById(Establecimiento establecimiento) {
		Establecimiento estab = establecimientoDao
				.readFechaCierreById(establecimiento);
		
		if (estab.getSiguienteCierre() != null) {
			String fechaCierreSig = FechasUtils.parseDateToString(
					estab.getSiguienteCierre(), FechasUtils.formatddMMyyyyHyphen);
			return fechaCierreSig;
		}
		
		return null;
	}
}
