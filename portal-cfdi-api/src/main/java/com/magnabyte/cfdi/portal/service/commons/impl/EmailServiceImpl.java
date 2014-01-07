package com.magnabyte.cfdi.portal.service.commons.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.service.commons.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private static Logger logger = 
			LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;

	@Value("${email.from}")
	private String email;
	
	@Override
	public void sendMail(String message, String subject, String... recipients) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMimeMail(String message, String messageHtml,
			String subject, String... recipients) throws MessagingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMailWithAttach(String message, String messageHtml,
			String subject, Map<String, InputStreamResource> attach, String... recipients)
			throws MessagingException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		
		logger.debug("Iniciando el envio de email");
		
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
		helper.setTo(recipients);
		helper.setText(message, messageHtml);
		helper.setSubject(subject);
		helper.setFrom(email);
		for(Map.Entry<String, InputStreamResource> entry : attach.entrySet()) {
			logger.debug(entry.getKey() + "/" +entry.getValue());
			helper.addAttachment(entry.getKey(), entry.getValue());
		}
		javaMailSender.send(msg);
	}

//	@Override
//	public void sendMailWithEngine(String message, String subject,
//			String template, Map model, String... recipients)
//			throws MessagingException, IOException, TemplateException {
//		// TODO Auto-generated method stub
//		
//	}
}
