package com.springboot.paymentgateway.entity;

import lombok.Data;

@Data
public class OrderResponse {

    String secretKey;
    String razorpayOrderId;
    Long applicationFee;
    String secretId;
    String paymentGatewayName;

}
