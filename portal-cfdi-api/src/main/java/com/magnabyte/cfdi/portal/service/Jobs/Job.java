package com.magnabyte.cfdi.portal.service.Jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Job extends QuartzJobBean {

	Tarea tarea;
	
	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		tarea.printMe();
		
	}

}
