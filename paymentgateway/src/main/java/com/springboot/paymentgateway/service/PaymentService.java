package com.springboot.paymentgateway.service;

import com.springboot.paymentgateway.entity.OrderRequest;
import com.springboot.paymentgateway.entity.OrderResponse;
import com.springboot.paymentgateway.entity.PaymentResponse;

public interface PaymentService {

    OrderResponse createOrder(OrderRequest orderRequest);

    void updateOrder(PaymentResponse paymentResponse);
}
