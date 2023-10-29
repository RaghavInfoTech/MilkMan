package com.app.milkman.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MilkManController {

    @GetMapping("/hello")
    public String testf(){
        return "Hi";
    }
}
