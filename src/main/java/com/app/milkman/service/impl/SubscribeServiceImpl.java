package com.app.milkman.service.impl;

import com.app.milkman.entity.*;
import com.app.milkman.model.ProductOrdersReq;
import com.app.milkman.model.SubscribeRequest;
import com.app.milkman.model.SubscribeResponse;
import com.app.milkman.repository.CustomersRepository;
import com.app.milkman.repository.ProductSubscriptionsRepository;
import com.app.milkman.repository.ProductsRepository;
import com.app.milkman.repository.SubscriptionRepository;
import com.app.milkman.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.app.milkman.utils.Constants.*;

@Slf4j
@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ProductSubscriptionsRepository productSubscriptionsRepository;
    @Override
    public SubscribeResponse subscribe(SubscribeRequest subscribeRequest) {

        Customers customers = customersRepository.findByCustomerId(subscribeRequest.getCustomerId());

        Subscriptions subscriptions = new Subscriptions();
        subscriptions.setSubscriptionId(UUID.randomUUID().toString());

        // customer details
        subscriptions.setCustomerId(customers.getCustomerId());
        subscriptions.setCustomerName(customers.getFirstName() + " " + customers.getLastName());
        subscriptions.setPrimaryPhone(customers.getPrimaryPhone());
        subscriptions.setEmailId(customers.getEmailId());
        subscriptions.setAddress(customers.getAddress());
        subscriptions.setPinCode(customers.getPinCode());
        subscriptions.setLandmark(customers.getLandmark());

        //subscription detail
        subscriptions.setOrderDateTime(LocalDateTime.now());
        subscriptions.setDeliveryTimeSlot(subscribeRequest.getDeliveryTimeSlot());
        subscriptions.setDeliveryStartDate(subscribeRequest.getDeliveryStartDate());
        subscriptions.setDeliveryEndDate(subscribeRequest.getDeliveryEndDate());
        subscriptions.setDeliveryFrequency(subscribeRequest.getDeliveryFrequency());
        subscriptions.setDeliveryDays(String.join(", ", subscribeRequest.getDeliveryDays()));
        subscriptions.setDeliveryCharge(BigDecimal.valueOf(subscribeRequest.getDeliveryCharge()));
        subscriptions.setOrderStatus(ORDER_PLACED);
        subscriptions.setStatus(ACTIVE);
        subscriptions.setCreatedBy(subscriptions.getCustomerName());
        subscriptions.setCreatedTime(LocalDateTime.now());
        subscriptions.setUpdatedBy(subscriptions.getCustomerName());
        subscriptions.setUpdatedTime(LocalDateTime.now());
        //get product details
        List<ProductSubscriptions> productSubscriptions = getProductOrders(subscribeRequest.getProductOrderReqs(), subscriptions);
        double orderTotal = productSubscriptions.stream().mapToDouble(po -> po.getProductPrice().multiply(BigDecimal.valueOf(po.getQuantity())).doubleValue())
                .sum() + subscribeRequest.getDeliveryCharge();
        subscriptions.setOrderTotal(BigDecimal.valueOf(orderTotal));

        Subscriptions subscriptionDetails = subscriptionRepository.save(subscriptions);
        List<ProductSubscriptions> saveProductOrders = productSubscriptionsRepository.saveAll(productSubscriptions);

        //Response generation
        SubscribeResponse response = SubscribeResponse.builder().subscriptionId(subscriptions.getSubscriptionId()).build();
        response.setStatus(SUCCESS);
        response.setStatusCode(SUCCESS_CODE);
        return response;
    }
    private List<ProductSubscriptions> getProductOrders(List<ProductOrdersReq> productOrderReq, Subscriptions subscriptions) {

        List<ProductSubscriptions> productOrdersList = productOrderReq.stream().map(po -> {
            Products product = productsRepository.findByProductId(po.getProductId());

            ProductSubscriptions productSubscriptions = new ProductSubscriptions();
            productSubscriptions.setProductSubscriptionId(UUID.randomUUID().toString());
            productSubscriptions.setSubscriptions(subscriptions);
            productSubscriptions.setProducts(product);
            productSubscriptions.setProductName(product.getProductName());
            productSubscriptions.setProductPrice(product.getProductPrice());
            productSubscriptions.setQuantity(Long.valueOf(po.getQuantity()));

            productSubscriptions.setCreatedBy(subscriptions.getCustomerName());
            productSubscriptions.setCreatedTime(LocalDateTime.now());
            productSubscriptions.setUpdatedBy(subscriptions.getCustomerName());
            productSubscriptions.setUpdatedTime(LocalDateTime.now());
            productSubscriptions.setStatus(ACTIVE);
            return productSubscriptions;
        }).collect(Collectors.toList());
        return productOrdersList;
    }
}
