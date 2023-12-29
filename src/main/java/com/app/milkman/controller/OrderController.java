package com.app.milkman.controller;

import com.app.milkman.model.OrderDetails;
import com.app.milkman.model.OrderRegRequest;
import com.app.milkman.model.OrderRegResponse;
import com.app.milkman.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public OrderRegResponse createOrder(@RequestBody OrderRegRequest orderReq) {
        log.debug("Order created method invoked!!! {}", orderReq);

        return orderService.createOrder(orderReq);
    }

    @GetMapping("/getAllOrders")
    public List<OrderDetails> getAllOrders(Pageable pageable) {
        log.debug("Get all orders method invoked!!! {}", pageable);

        return orderService.getAllOrders(pageable);
    }

    @GetMapping("/getAllOrders/{customerId}")
    public List<OrderDetails> getAllOrdersByCustomerId(@PathVariable("customerId") String customerId,
                                                       Pageable pageable) {
        log.debug("Get all orders by customer id method invoked!!! {}", pageable);

        return orderService.getAllOrdersByCustomerId(customerId, pageable);
    }

}
