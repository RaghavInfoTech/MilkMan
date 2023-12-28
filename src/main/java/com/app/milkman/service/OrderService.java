package com.app.milkman.service;

import com.app.milkman.model.OrderRegRequest;
import com.app.milkman.model.OrderRegResponse;

public interface OrderService {

    public OrderRegResponse createOrder(OrderRegRequest orderRegRequest);
}
