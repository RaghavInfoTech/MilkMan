package com.app.milkman.controller;

import com.app.milkman.model.CustomerAuthRequest;
import com.app.milkman.model.CustomerAuthResponse;
import com.app.milkman.model.CustomerRegRequest;
import com.app.milkman.model.CustomerRegResponse;
import com.app.milkman.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/customer")
@RestController
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public CustomerRegResponse registration(@RequestBody CustomerRegRequest custReg) {
        log.debug("Customer registration invoked!!! {}", custReg);

        return customerService.registerCustomer(custReg);
    }

    @PostMapping("/authenticate")
    public CustomerAuthResponse authenticate(@RequestBody CustomerAuthRequest authRequest) {
        log.debug("Authenticate method invoked!!! {}", authRequest);

        return customerService.authenticate(authRequest);
    }


}
