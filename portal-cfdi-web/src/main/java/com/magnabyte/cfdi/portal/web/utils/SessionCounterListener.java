package com.magnabyte.cfdi.portal.web.utils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionCounterListener implements HttpSessionListener {

	private static final Logger logger = LoggerFactory.getLogger(SessionCounterListener.class);
	
	private static int totalActiveSessions;
	
	public static int getTotalActiveSessions() {
		return totalActiveSessions;
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		totalActiveSessions ++;
		logger.debug("Sesion creada, total de sesiones activas {}", totalActiveSessions);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		totalActiveSessions --;
		logger.debug("Sesion destruida, total de sesiones activas {}", totalActiveSessions);
	}

}
