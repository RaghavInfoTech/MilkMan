package com.app.milkman.repository;

import com.app.milkman.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrdersRepository extends JpaRepository<Orders, String>, JpaSpecificationExecutor<Orders> {

    Page<Orders> findByCustomerId(String customerId, Pageable pageable);
}