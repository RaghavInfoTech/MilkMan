package com.app.milkman.service.impl;

import com.app.milkman.entity.Customers;
import com.app.milkman.model.CustomerAuthRequest;
import com.app.milkman.model.CustomerAuthResponse;
import com.app.milkman.model.CustomerRegRequest;
import com.app.milkman.model.CustomerRegResponse;
import com.app.milkman.repository.CustomersRepository;
import com.app.milkman.service.CustomerService;
import com.app.milkman.utils.Constants;
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

    @Override
    public CustomerRegResponse registerCustomer(CustomerRegRequest custRequest) {
        Customers customers = new Customers();
        customers.setCustomerid(UUID.randomUUID().toString());
        customers.setFirstname(custRequest.getFirstName());
        customers.setLastname(custRequest.getLastName());
        customers.setPphone(custRequest.getPrimaryPhone());
        customers.setSphone(custRequest.getSecondaryPhone());
        customers.setEmailid(custRequest.getEmailId());
        customers.setDob(custRequest.getDateOfBirth());
        customers.setAuthPin(custRequest.getAuthPin());
        customers.setAddress(custRequest.getAddress());
        customers.setLandmark(custRequest.getLandmark());
        customers.setStatus(Constants.INACTIVE);

        customers.setCreatedby(custRequest.getFirstName());
        customers.setCreatedtime(LocalDateTime.now());
        customers.setUpdatedby(custRequest.getFirstName());
        customers.setUpdatedtime(LocalDateTime.now());

        /* Insert */
        Customers customer = customersRepository.save(customers);

        CustomerRegResponse response = CustomerRegResponse.builder().build();
        response.setCustomerName(customer.getFirstname() + " " + customer.getLastname());
        response.setStatusCode(SUCCESS_CODE);
        response.setStatus(SUCCESS);

        return response;
    }

    @Override
    public CustomerAuthResponse authenticate(CustomerAuthRequest authRequest) {
        List<Customers> customers = customersRepository.getCustomersByEmailidOrPphoneAndAuthPin(authRequest.getEmailIdOrPhone(), authRequest.getEmailIdOrPhone(), authRequest.getAuthPin());
        CustomerAuthResponse response = CustomerAuthResponse.builder().build();
        if (!CollectionUtils.isEmpty(customers)) {
            response.setAuthToken(jwtService.GenerateToken(customers.get(0).getPphone()));
            response.setStatusCode(SUCCESS_CODE);
            response.setStatus(SUCCESS);
            response.setCustomerName(customers.get(0).getFirstname() + " " + customers.get(0).getLastname());
            return response;
        }
        response.setStatusCode(NO_FOUND_CODE);
        response.setStatus(FAILED);
        return response;
    }
}
