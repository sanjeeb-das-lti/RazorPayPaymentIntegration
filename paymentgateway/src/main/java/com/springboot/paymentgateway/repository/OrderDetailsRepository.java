package com.springboot.paymentgateway.repository;

import com.springboot.paymentgateway.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<Orders, String> {
}
