package com.app.milkman.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MilkManController {

    @GetMapping("/healthcheck")
    public String health(){
        return "Success";
    }
}
