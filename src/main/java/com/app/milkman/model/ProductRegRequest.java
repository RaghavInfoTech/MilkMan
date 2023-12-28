package com.app.milkman.model;

import lombok.Data;

@Data
public class ProductRegRequest {
    private String productId;
    private String productName;
    private String productDescription;
    private double price;
    private String status;
}
