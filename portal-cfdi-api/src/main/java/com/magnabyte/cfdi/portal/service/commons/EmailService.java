package com.magnabyte.cfdi.portal.service.commons;

import java.util.Map;

import org.springframework.core.io.ByteArrayResource;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Interfáz que representa el servicio de email
 */
public interface EmailService {

	void sendMail(String message, String subject, String... recipients);

	void sendMimeMail(String message, String messageHtml, String subject,
			String... recipients);

	void sendMailWithAttach(String message, String messageHtml, String subject,
			Map<String, ByteArrayResource> attach, String... recipients);

	void sendMailWithEngine(String message, String subject, String template,
			Map<String, String> model, String... recipients);
}
