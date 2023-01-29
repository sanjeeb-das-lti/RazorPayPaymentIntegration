package com.springboot.paymentgateway.controller;

import com.springboot.paymentgateway.entity.OrderRequest;
import com.springboot.paymentgateway.entity.OrderResponse;
import com.springboot.paymentgateway.entity.PaymentResponse;
import com.springboot.paymentgateway.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping("/createOrder")
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {

        return paymentService.createOrder(orderRequest);
    }

    @PutMapping("/updateOrder")
    public void updateOrder(@RequestBody PaymentResponse paymentResponse){
        paymentService.updateOrder(paymentResponse);
    }


}
