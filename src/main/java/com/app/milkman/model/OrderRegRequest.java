package com.app.milkman.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderRegRequest {
    private String orderId;
    private String customerId;
    private List<ProductOrdersReq> productOrderReqs;
    private String deliveryDate;
    private String deliveryTimeSlot;
    private String orderStatus;
    private double deliveryCharge;

}
