package ro.hoptrop.service.impl;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import ro.hoptrop.service.MailService;

@Service
@Async
public class MailServiceImpl implements MailService{
	private static final Logger LOG = Logger.getLogger(MailServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;
	
	
	@Override
	public void sendNewPasswordMail(String emailTo, String password) {
		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

//		final Context context = new Context();
//		context.setVariable("baseUrl", baseUrl);
//		context.setVariable("token", token);
//		final String htmlContent = templateEngine.process(PASSWORD_CHANGE_CONFIRM_HTML, context);
//		try {
//			LOG.info("Sending password change confirmation email to " + emailTo);
//			configureMessage(message, emailTo, "Schimbare parola", htmlContent);
//			mailSender.send(mimeMessage);
//		} catch (Exception e) {
//			LOG.error("Exception occurred while trying to send password change confirmation email", e);
//		}
	}
	
}
