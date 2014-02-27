package com.magnabyte.cfdi.portal.service.commons.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.commons.EmailService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 * 
 * Clase que representa el servicio de email
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private static Logger logger = 
			LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;

	@Value("${email.from}")
	private String form;
	
	@Override
	public void sendMail(String message, String subject, String... recipients) {
		SimpleMailMessage msg = new SimpleMailMessage();
		
		logger.debug("Iniciando el envio de email");
		
		msg.setTo(recipients);
		msg.setText(message);
		msg.setSubject(subject);
		msg.setFrom(form);
		javaMailSender.send(msg);		
		logger.debug("Enviado");
	}

	@Override
	public void sendMimeMail(String message, String messageHtml,
			String subject, String... recipients) {
		MimeMessage msg = javaMailSender.createMimeMessage();
		logger.debug("Iniciando el envio de email");
		
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(msg, true, PortalUtils.encodingUTF8);
			helper.setTo(recipients);
			helper.setText(message, messageHtml);
			helper.setSubject(subject);
			helper.setFrom(form);
			javaMailSender.send(msg);
			logger.debug("Enviado");
		} catch (MessagingException ex) {
			logger.error("Error al enviar el email.", ex);
			throw new PortalException("Error al enviar el email", ex);
		}
	}

	@Override
	public void sendMailWithAttach(String message, String messageHtml,
			String subject, Map<String, ByteArrayResource> attach, String... recipients) {

		MimeMessage msg = javaMailSender.createMimeMessage();
		logger.debug("Iniciando el envio de email");
		
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(msg, true, PortalUtils.encodingUTF8);
			helper.setTo(recipients);
			helper.setText(message, messageHtml);
			helper.setSubject(subject);
			helper.setFrom(form);
			for(Map.Entry<String, ByteArrayResource> entry : attach.entrySet()) {			
				helper.addAttachment(entry.getKey(), entry.getValue());
			}
			javaMailSender.send(msg);
			logger.debug("Enviado");
		} catch (MessagingException ex) {
			logger.error("Error al enviar el email.", ex);
			throw new PortalException("Error al enviar el email", ex);
		}
	}

	@Override
	public void sendMailWithEngine(String message, String subject,
			String template, Map<String, String> model, String... recipients){
		throw new PortalException("Envío de mail con motor no implementado");
	}
}
