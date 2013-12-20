package com.magnabyte.cfdi.portal.service.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Trabajo extends  QuartzJobBean {
		Tarea tarea;

		public void setTarea(Tarea tarea) {
			this.tarea = tarea;
		}
		@Autowired
		protected void executeInternal(JobExecutionContext context)
				throws JobExecutionException {
			tarea.printMe();

		}
}
