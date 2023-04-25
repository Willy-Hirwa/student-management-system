package com.mkr.springboot.SpringWebApp.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;



@Service
public class MailService {

    @Autowired
    @Qualifier("gmail")
    private JavaMailSender mailSender;

    public void sendMail(String toAddresses) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(toAddresses.split("[,;]"));
            message.setFrom("munezerowilly5@gmail.com", "<willy>");
            message.setSubject("Email Sent");
            message.setText("You have sent an email", false);
        };
        mailSender.send(preparator);
    }
}