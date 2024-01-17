package com.mentormate.mentormate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailService {
 
	@Autowired
	private JavaMailSender mailSender;
 
	public void sendMail(String mail, String subject, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		simpleMailMessage.setTo(mail);
		mailSender.send(simpleMailMessage);
	}

	
 
}
