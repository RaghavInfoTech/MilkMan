package com.app.milkman.service.impl;

import com.app.milkman.entity.Products;
import com.app.milkman.model.ProductDetails;
import com.app.milkman.model.ProductRegRequest;
import com.app.milkman.model.ProductRegResponse;
import com.app.milkman.repository.ProductsRepository;
import com.app.milkman.service.ProductService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    /**
     * Method to update Product details
     *
     * @param productReg
     * @return
     */
    @Override
    public ProductRegResponse updateProduct(ProductRegRequest productReg) {

        // Fetch product from DB
        Products product = productsRepository.findByProductId(productReg.getProductId());

        ProductRegResponse response = ProductRegResponse.builder().
                productId(productReg.getProductId()).build();
        if (ObjectUtils.isEmpty(product)) {
            //Error response
            response.setStatus(FAILED);
            response.setStatusCode(NO_FOUND_CODE);
            return response;
        }
        product.setProductName(!StringUtils.hasText(productReg.getProductName())
                ? productReg.getProductName() : product.getProductName());
        product.setProductDescription(!StringUtils.hasText(productReg.getProductDescription())
                ? productReg.getProductDescription() : product.getProductDescription());
        product.setStatus(!StringUtils.hasText(productReg.getStatus()) ? productReg.getStatus() : product.getStatus());
        product.setProductPrice(!ObjectUtils.isEmpty(productReg.getPrice())
                ? BigDecimal.valueOf(productReg.getPrice()) : product.getProductPrice());
        //Update DB record
        Products saveProduct = productsRepository.save(product);

        response.setProductName(saveProduct.getProductName());
        response.setStatus(SUCCESS);
        response.setStatusCode(SUCCESS_CODE);
        return response;
    }

    @Override
    public List<ProductDetails> getAllProducts(Pageable pageable) {

        Page<Products> products = productsRepository.findAll(pageable);
        List<ProductDetails> response = products.stream().map(ps -> copyProduct(ps)).collect(Collectors.toList());
        return response;
    }

    private ProductDetails copyProduct(Products product) {
        return ProductDetails.builder().
                productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDescription(product.getProductDescription())
                .status(product.getStatus())
                .createdBy(product.getCreatedBy())
                .createdTime(product.getCreatedTime())
                .updatedBy(product.getUpdatedBy())
                .updatedTime(product.getUpdatedTime()).build();
    }
}
