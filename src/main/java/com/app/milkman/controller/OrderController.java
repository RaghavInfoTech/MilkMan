package com.app.milkman.controller;

import com.app.milkman.model.OrderRegRequest;
import com.app.milkman.model.OrderRegResponse;
import com.app.milkman.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public OrderRegResponse createOrder(@RequestBody OrderRegRequest orderReq){
        log.debug("Order created method invoked!!! {}", orderReq);

    return null;
    }
}
