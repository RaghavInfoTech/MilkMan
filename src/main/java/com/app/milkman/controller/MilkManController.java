package com.app.milkman.controller;

import com.app.milkman.model.SMSRequest;
import com.app.milkman.component.EncryptDecrypt;
import com.app.milkman.component.SMSComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MilkManController {

    @Autowired
    SMSComponent smsService;

    @Autowired
    EncryptDecrypt encryptDecrypt;

    @GetMapping("/healthcheck")
    public String health() {

        return "SUCCESS";
    }
}
