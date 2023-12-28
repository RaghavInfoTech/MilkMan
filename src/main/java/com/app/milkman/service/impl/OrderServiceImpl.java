package com.app.milkman.service.impl;

import com.app.milkman.entity.Customers;
import com.app.milkman.entity.Orders;
import com.app.milkman.entity.ProductOrders;
import com.app.milkman.entity.Products;
import com.app.milkman.model.OrderRegRequest;
import com.app.milkman.model.OrderRegResponse;
import com.app.milkman.model.ProductOrdersReq;
import com.app.milkman.repository.CustomersRepository;
import com.app.milkman.repository.OrdersRepository;
import com.app.milkman.repository.ProductOrdersRepository;
import com.app.milkman.repository.ProductsRepository;
import com.app.milkman.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.app.milkman.utils.Constants.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductOrdersRepository productOrdersRepository;

    @Override
    public OrderRegResponse createOrder(OrderRegRequest orderRegRequest) {

        Customers customers = customersRepository.findByCustomerId(orderRegRequest.getCustomerId());

        Orders orders = new Orders();
        orders.setOrderId(UUID.randomUUID().toString());

        // customer details
        orders.setCustomerId(customers.getCustomerId());
        orders.setCustomerName(customers.getFirstName() + " " + customers.getLastName());
        orders.setPrimaryPhone(customers.getPrimaryPhone());
        orders.setEmailId(customers.getEmailId());
        orders.setAddress(customers.getAddress());
        orders.setPinCode(customers.getPinCode());
        orders.setLandmark(customers.getLandmark());

        //order detail
        orders.setOrderDateTime(LocalDateTime.now());
        orders.setDeliveryTimeSlot(orderRegRequest.getDeliveryTimeSlot());
        orders.setDeliveryDate(orderRegRequest.getDeliveryDate());
        orders.setOrderStatus(ORDER_PLACED);
        List<ProductOrders> productOrders = getProductOrders(orderRegRequest.getProductOrderReqs(), orders);

        //Order total calculation
        double orderTotal = productOrders.stream().mapToDouble(po -> po.getProductPrice().multiply(BigDecimal.valueOf(po.getQuantity())).doubleValue())
                .sum() + orderRegRequest.getDeliveryCharge();
        orders.setOrderTotal(BigDecimal.valueOf(orderTotal));

        Orders saveOrder = ordersRepository.save(orders);
        List<ProductOrders> saveProductOrders = productOrdersRepository.saveAll(productOrders);

        //Response generation
        OrderRegResponse response = OrderRegResponse.builder().orderId(saveOrder.getOrderId()).build();
        response.setStatus(SUCCESS);
        response.setStatusCode(SUCCESS_CODE);
        return response;
    }


    private List<ProductOrders> getProductOrders(List<ProductOrdersReq> productOrderReq, Orders order) {

        List<ProductOrders> productOrdersList = productOrderReq.stream().map(po -> {
            Products product = productsRepository.findByProductId(po.getProductId());

            ProductOrders productOrder = new ProductOrders();
            productOrder.setProductOrderId(UUID.randomUUID().toString());
            productOrder.setOrders(order);
            productOrder.setProducts(product);
            productOrder.setProductName(product.getProductName());
            productOrder.setProductPrice(product.getProductPrice());
            productOrder.setQuantity(Long.valueOf(po.getQuantity()));

            productOrder.setCreatedBy(order.getCustomerName());
            productOrder.setCreatedTime(LocalDateTime.now());
            productOrder.setUpdatedBy(order.getCustomerName());
            productOrder.setUpdatedTime(LocalDateTime.now());
            productOrder.setStatus(ACTIVE);
            return productOrder;
        }).collect(Collectors.toList());
        return productOrdersList;
    }


}
