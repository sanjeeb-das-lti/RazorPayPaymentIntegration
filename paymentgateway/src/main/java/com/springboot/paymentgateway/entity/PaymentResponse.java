package com.springboot.paymentgateway.entity;

import lombok.Data;

@Data
public class PaymentResponse {

    String orderId;
    String paymentId;
}
