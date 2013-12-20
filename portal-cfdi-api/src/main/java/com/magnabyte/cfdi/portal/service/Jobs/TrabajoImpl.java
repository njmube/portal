package com.magnabyte.cfdi.portal.service.Jobs;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service("Trabajo")
public class TrabajoImpl implements Trabajo {
	
	private static final Logger logger = Logger.getLogger(TrabajoImpl.class);

		public void executeInternal(){
			logger.debug("---- Ejecutando la tarea");
		}
		
}
