package com.app.milkman.service.impl;

import com.app.milkman.entity.Customers;
import com.app.milkman.model.CustomerAuthRequest;
import com.app.milkman.model.CustomerAuthResponse;
import com.app.milkman.model.CustomerRegRequest;
import com.app.milkman.model.CustomerRegResponse;
import com.app.milkman.repository.CustomersRepository;
import com.app.milkman.service.CustomerService;
import com.app.milkman.utils.Constants;
import com.app.milkman.utils.EncryptDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.app.milkman.utils.Constants.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Override
    public CustomerRegResponse registerCustomer(CustomerRegRequest custRequest) {
        Customers customers = new Customers();
        customers.setCustomerId(UUID.randomUUID().toString());
        customers.setFirstName(custRequest.getFirstName());
        customers.setLastName(custRequest.getLastName());
        customers.setPrimaryPhone(custRequest.getPrimaryPhone());
        customers.setSecondaryPhone(custRequest.getSecondaryPhone());
        customers.setEmailId(custRequest.getEmailId());
        customers.setDob(custRequest.getDateOfBirth());

        customers.setAuthPin(encryptDecrypt.encrypt(custRequest.getAuthPin(), KEY));
        customers.setAddress(custRequest.getAddress());
        customers.setPinCode(custRequest.getPincode());
        customers.setLandmark(custRequest.getLandmark());
        customers.setStatus(Constants.INACTIVE);

        customers.setCreatedBy(custRequest.getFirstName());
        customers.setCreatedTime(LocalDateTime.now());
        customers.setUpdatedBy(custRequest.getFirstName());
        customers.setUpdatedTime(LocalDateTime.now());

        /* Insert */
        Customers customer = customersRepository.save(customers);

        CustomerRegResponse response = CustomerRegResponse.builder().build();
        response.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
        response.setCustomerId(customer.getCustomerId());
        response.setStatusCode(SUCCESS_CODE);
        response.setStatus(SUCCESS);

        return response;
    }

    @Override
    public CustomerAuthResponse authenticate(CustomerAuthRequest authRequest) {
        List<Customers> customers = customersRepository.getCustomersByEmailidOrPphoneAndAuthPin(authRequest.getEmailIdOrPhone(), authRequest.getEmailIdOrPhone(), encryptDecrypt.encrypt(authRequest.getAuthPin(), KEY));
        CustomerAuthResponse response = CustomerAuthResponse.builder().build();
        if (!CollectionUtils.isEmpty(customers)) {
            response.setAuthToken(jwtService.GenerateToken(customers.get(0).getPrimaryPhone()));
            response.setStatusCode(SUCCESS_CODE);
            response.setStatus(SUCCESS);
            response.setCustomerName(customers.get(0).getFirstName() + " " + customers.get(0).getLastName());
            response.setCustomerId(customers.get(0).getCustomerId());
            return response;
        }
        response.setStatusCode(NO_FOUND_CODE);
        response.setStatus(FAILED);
        return response;
    }
}
