package com.magnabyte.cfdi.portal.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session) {
		return "factura/datosTicket";
	}
	
}
