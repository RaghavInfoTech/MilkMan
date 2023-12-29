package com.app.milkman.controller;

import com.app.milkman.component.EmailComponent;
import com.app.milkman.model.EmailRequest;
import com.app.milkman.model.SMSRequest;
import com.app.milkman.component.EncryptDecrypt;
import com.app.milkman.component.SMSComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MilkManController {

    @Autowired
    private SMSComponent smsService;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private EmailComponent emailComponent;

    @GetMapping("/healthCheck")
    public String health() {
        EmailRequest email = new EmailRequest();
        email.setRecipient("raghavareddy.dcn@gmail.com");
        email.setSubject("MILK MAN");
        email.setMsgBody("Sample mail ...");
        emailComponent.sendEmail(email);
        return "SUCCESS";
    }
}
