package com.magnabyte.cfdi.portal.service.certificado.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.sat.cfd._3.Comprobante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.dao.certificado.CertificadoDao;
import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.service.certificado.CertificadoService;

@Service("certificadoService")
public class CertificadoServiceImpl implements CertificadoService {

	private static final Logger logger = LoggerFactory.getLogger(CertificadoServiceImpl.class);
	
	@Autowired
	private CertificadoDao certificadoDao;
	
	@Override
	public CertificadoDigital readVigente(Comprobante comprobante) {
		logger.debug("recuperando certificado vigente");
		String fechaComprobante = obtenerFechaComprobante(comprobante.getFecha().toGregorianCalendar().getTime());
		return certificadoDao.readVigente(fechaComprobante);
	}

	private String obtenerFechaComprobante(Date fechaComprobante) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(fechaComprobante);
	}

}
