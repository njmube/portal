package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.establecimiento.EstablecimientoDao;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;

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
	public Establecimiento readById(Establecimiento establecimiento) {
		return establecimientoDao.readById(establecimiento);
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

	@Override
	public String readFechaCierreById(Establecimiento establecimiento) {
		Establecimiento estab = establecimientoDao
				.readFechaCierreById(establecimiento);
		
		DateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		String fechaCierreSig = formatoFecha.format(estab.getSiguienteCierre());
		
		return fechaCierreSig;
	}
}
