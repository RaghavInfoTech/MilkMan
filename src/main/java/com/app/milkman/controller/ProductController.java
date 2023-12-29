package com.app.milkman.controller;

import com.app.milkman.model.ProductDetails;
import com.app.milkman.model.ProductRegRequest;
import com.app.milkman.model.ProductRegResponse;
import com.app.milkman.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update")
    public ProductRegResponse updateProduct(@RequestBody ProductRegRequest productReg) {
        log.debug("Product update method invoked!!! {}", productReg);

        return productService.updateProduct(productReg);
    }

    @GetMapping("/getProducts")
    public List<ProductDetails> getAllProducts(Pageable pageable) {
        log.debug("Get all products method invoked!!! {}", pageable);

        return productService.getAllProducts(pageable);
    }
}
