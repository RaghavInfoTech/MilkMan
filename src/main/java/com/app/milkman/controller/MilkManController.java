package com.app.milkman.controller;

import com.app.milkman.utils.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MilkManController {

    @Autowired
    SMSService smsService;

    @GetMapping("/healthcheck")
    public String health() {
        return "SUCCESS";
    }
}
