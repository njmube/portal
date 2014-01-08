package com.magnabyte.cfdi.portal.web.interceptor;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CloseOfDayInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(CloseOfDayInterceptor.class);
	
	@Value("${hora.inicio}")
	private int horaInicio;
	
	@Value("${hora.cierre}")
	private int horaCierre;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("pre handle");
		logger.debug("inicio {}", horaInicio);
		logger.debug("cierre {}", horaCierre);
		
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		logger.debug("la hour {}", hour);
		
		if (hour >= horaInicio && hour < horaCierre) {
			return true;
		} else {
			response.sendRedirect("loginFailed");
			return false;
		}
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		logger.debug("post handle, termino el proceso de cierre");
	}
}
