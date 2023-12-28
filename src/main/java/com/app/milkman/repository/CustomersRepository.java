package com.app.milkman.repository;

import com.app.milkman.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Void>, JpaSpecificationExecutor<Customers> {

    List<Customers> getCustomersByEmailidOrPphoneAndAuthPin(String emailId, String phone, String authPin);

}