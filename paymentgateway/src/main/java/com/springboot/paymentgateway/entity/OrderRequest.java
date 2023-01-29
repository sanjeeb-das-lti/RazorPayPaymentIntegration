package com.springboot.paymentgateway.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
public class OrderRequest {

    String customerName;
    String emailId;
    String phoneNumber;
    BigInteger amount;

}
