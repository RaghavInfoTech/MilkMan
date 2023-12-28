package com.app.milkman.service;

import com.app.milkman.model.CustomerAuthRequest;
import com.app.milkman.model.CustomerAuthResponse;
import com.app.milkman.model.CustomerRegResponse;
import com.app.milkman.model.CustomerRegRequest;

public interface CustomerService {

    CustomerRegResponse registerCustomer(CustomerRegRequest custRegRequest);

    CustomerAuthResponse authenticate(CustomerAuthRequest authRequest);
}
