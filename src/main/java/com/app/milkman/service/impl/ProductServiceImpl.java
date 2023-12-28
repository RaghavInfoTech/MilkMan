package com.app.milkman.service.impl;

import com.app.milkman.entity.Products;
import com.app.milkman.model.ProductRegRequest;
import com.app.milkman.model.ProductRegResponse;
import com.app.milkman.repository.ProductsRepository;
import com.app.milkman.service.ProductService;
import com.app.milkman.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.app.milkman.utils.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductsRepository productsRepository;

    @Override
    public ProductRegResponse registerProduct(ProductRegRequest productReg) {

        ProductRegResponse response = ProductRegResponse.builder().productName(productReg.getProductName()).build();
        try {
            Products product = new Products();
            product.setProductId(UUID.randomUUID().toString());
            product.setProductName(productReg.getProductName());
            product.setProductDescription(productReg.getProductDescription());
            product.setProductPrice(BigDecimal.valueOf(productReg.getPrice()));

            product.setCreatedBy(ADMIN);
            product.setCreatedTime(LocalDateTime.now());
            product.setUpdatedBy(ADMIN);
            product.setUpdatedTime(LocalDateTime.now());

            // Insert Product
            Products productSave = productsRepository.save(product);

            response.setProductId(productSave.getProductId());
            response.setStatusCode(SUCCESS_CODE);
            response.setStatus(SUCCESS);
        } catch (Exception exception) {
            response.setStatusCode(INTERNAL_ERROR_CODE);
            response.setStatus(FAILED);
            response.setErrorMsg(exception.getMessage());
        }
        return response;
    }
}
