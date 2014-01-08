package com.magnabyte.cfdi.portal.service.commons;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.core.io.ByteArrayResource;

public interface EmailService {

	void sendMail(String message, String subject, String... recipients);

	void sendMimeMail(String message, String messageHtml, String subject,
			String... recipients) throws MessagingException;

	void sendMailWithAttach(String message, String messageHtml, String subject,
			Map<String, ByteArrayResource> attach, String... recipients)
			throws MessagingException;

//	void sendMailWithEngine(String message, String subject, String template,
//			Map model, String... recipients) throws MessagingException,
//			IOException, TemplateException;
}
