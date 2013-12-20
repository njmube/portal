package com.magnabyte.cfdi.portal.web.controller;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.ui.ModelMap;

public class TareaProgramada extends QuartzJobBean {

	DocumentoController documentoController;
	
	public void setDocumentoController(DocumentoController documentoController) {
		this.documentoController = documentoController;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		documentoController.reporte(new ModelMap());
		
	}

}
