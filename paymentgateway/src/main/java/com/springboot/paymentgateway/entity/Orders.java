package com.springboot.paymentgateway.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Data
public class Orders {

    @Id
    @Column(name = "razorpayOrderId")
    String razorpayOrderId;
    @Column(name = "customerName")
    String customerName;
    @Column(name = "emailId")
    String emailId;
    @Column(name = "phoneNumber")
    String phoneNumber;
    @Column(name = "applicationFee")
    Long applicationFee;
    @Column(name = "paymentGatewayName")
    String paymentGatewayName;
    @Column(name = "orderStatus")
    String orderStatus;
    @Column(name = "razorpay_payment_id")
    String razorpayPaymentId;
    @Column(name = "paymentStatus")
    String paymentStatus;
    @Column(name = "createdDate")
    Timestamp createdDate;
    @Column(name = "lastUpdatedDate")
    Timestamp lastUpdatedDate;

}
