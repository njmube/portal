package com.magnabyte.cfdi.portal.service.certificado.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.certificado.CertificadoDao;
import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.utils.FechasUtils;
import com.magnabyte.cfdi.portal.service.certificado.CertificadoService;

@Service("certificadoService")
public class CertificadoServiceImpl implements CertificadoService {

	private static final Logger logger = LoggerFactory.getLogger(CertificadoServiceImpl.class);
	
	@Autowired
	private CertificadoDao certificadoDao;
	
	@Transactional(readOnly = true)
	@Override
	public CertificadoDigital readVigente(Comprobante comprobante) {
		logger.debug("recuperando certificado vigente");
		String fechaComprobante = FechasUtils
				.parseDateToString(comprobante.getFecha().toGregorianCalendar().getTime(), 
				FechasUtils.formatyyyyMMddHHmmssHyphen);
		return certificadoDao.readVigente(fechaComprobante);
	}

}
