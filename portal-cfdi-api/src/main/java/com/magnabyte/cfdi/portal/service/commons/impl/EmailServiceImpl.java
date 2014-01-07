package com.magnabyte.cfdi.portal.service.commons.impl;

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
			String subject, InputStreamResource[] attach, String... recipients)
			throws MessagingException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		
		logger.debug("Iniciando el envio de email");
		
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
		helper.setTo(recipients);
		helper.setText(message, messageHtml);
		helper.setSubject(subject);
		helper.setFrom(email);
		for (InputStreamResource att : attach) {
			helper.addAttachment(att.getFilename(), att);
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
