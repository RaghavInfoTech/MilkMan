package com.app.milkman.controller;

import com.app.milkman.model.*;
import com.app.milkman.service.OrderService;
import com.app.milkman.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @PostMapping("/create")
    public SubscribeResponse createOrder(@RequestBody SubscribeRequest subscribeRequest) {
        log.debug("Order created method invoked!!! {}", subscribeRequest);

        return subscribeService.subscribe(subscribeRequest);
    }



}
