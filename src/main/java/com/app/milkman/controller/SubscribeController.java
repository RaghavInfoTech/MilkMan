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

    @GetMapping("/getAllSubscriptions")
    public List<SubscriptionDetails> getAllOrders(Pageable pageable) {
        log.debug("Get all orders method invoked!!! {}", pageable);

        return subscribeService.getAllOrders(pageable);
    }

    @GetMapping("/getAllSubscriptions/{customerId}")
    public List<SubscriptionDetails> getAllOrdersByCustomerId(@PathVariable("customerId") String customerId,
                                                              Pageable pageable) {
        log.debug("Get all orders by customer id method invoked!!! {}", pageable);

        return subscribeService.getAllSubscriptionsByCustomerId(customerId, pageable);
    }
}
