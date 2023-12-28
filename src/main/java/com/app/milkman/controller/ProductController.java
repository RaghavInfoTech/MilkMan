package com.app.milkman.controller;

import com.app.milkman.model.ProductRegRequest;
import com.app.milkman.model.ProductRegResponse;
import com.app.milkman.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product")
@Slf4j
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public ProductRegResponse registerProduct(@RequestBody ProductRegRequest productReg) {
        log.debug("Product register method invoked!!! {}", productReg);

        return productService.registerProduct(productReg);
    }
}
