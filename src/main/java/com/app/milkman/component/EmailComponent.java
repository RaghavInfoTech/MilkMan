package com.app.milkman.component;

import com.app.milkman.model.EmailRequest;
import com.app.milkman.model.ParentResponse;
import jdk.jfr.Unsigned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailComponent {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public ParentResponse sendEmail(EmailRequest emailRequest) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        EmailRequest mail = new EmailRequest();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(emailRequest.getRecipient());
        simpleMailMessage.setText(emailRequest.getMsgBody());
        simpleMailMessage.setSubject(emailRequest.getSubject());

        mailSender.send(simpleMailMessage);

        return null;
    }

}
