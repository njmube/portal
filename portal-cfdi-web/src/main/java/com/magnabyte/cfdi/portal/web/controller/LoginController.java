package com.magnabyte.cfdi.portal.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private static final String loginView = "login/login";

	@RequestMapping("/")
	public String root() {
		return "redirect:/menuPage";
	}
	
	@RequestMapping("/login")
	public String login(ModelMap model) {
		logger.debug("login");
		model.put("isLoginPage", true);
		return loginView;
	}
	
	@RequestMapping("/loginFailed")
	public String loginError(ModelMap model) {
		logger.debug("loginFailed");
		model.put("error", true);
		return loginView;
	}
	
	@RequestMapping("/sessionTimeout")
	public String sessionTimeout(ModelMap model) {
		logger.debug("Sesion expirada");
		return "login/sessionTimeout";
	}
	
	@RequestMapping("logout")
	public String logout(ModelMap model) {
		return "login/logoutSuccess";
	}
	
}
