package com.springboot.paymentgateway.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.springboot.paymentgateway.constants.OrderStates;
import com.springboot.paymentgateway.constants.PaymentStates;
import com.springboot.paymentgateway.entity.OrderRequest;
import com.springboot.paymentgateway.entity.OrderResponse;
import com.springboot.paymentgateway.entity.Orders;
import com.springboot.paymentgateway.entity.PaymentResponse;
import com.springboot.paymentgateway.repository.OrderDetailsRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final String SECRET_ID = "rzp_test_d9znqqdbmnkUEC";
    private static final String SECRET_KEY = "nKtaqV6NVw8DIDcYe7UDu2we";
    private static final String RAZORPAY_1 = "RazorPay1";
    @Autowired
    OrderDetailsRepository orderDetailsRepo;
    OrderResponse orderResponse = new OrderResponse();
    private RazorpayClient razorpayClient;

    /**
     * Create Order Service
     *
     * @param orderRequest
     * @return
     */
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        try {
            razorpayClient = new RazorpayClient(SECRET_ID, SECRET_KEY);
            Order order = createRazorpayOrder(orderRequest.getAmount());

            System.out.println("--------------------------");
            String orderId = order.get("id");
            System.out.println("Order Id: " + orderId);
            System.out.println("--------Setting Order Response------------------");
            orderResponse.setRazorpayOrderId(orderId);
            orderResponse.setApplicationFee(orderRequest.getAmount().longValue());
            orderResponse.setPaymentGatewayName(RAZORPAY_1);
            orderResponse.setSecretId(SECRET_ID);
            orderResponse.setSecretKey(SECRET_KEY);

            Orders orderDetails = new Orders();
            orderDetails.setCustomerName(orderRequest.getCustomerName());
            orderDetails.setEmailId(orderRequest.getEmailId());
            orderDetails.setPhoneNumber(orderRequest.getPhoneNumber());
            orderDetails.setRazorpayOrderId(orderResponse.getRazorpayOrderId());
            orderDetails.setApplicationFee(orderResponse.getApplicationFee());
            orderDetails.setOrderStatus(OrderStates.CREATED);
            orderDetails.setRazorpayPaymentId("");
            orderDetails.setPaymentStatus(PaymentStates.PENDING);
            orderDetails.setPaymentGatewayName("RAZORPAY_1");
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            orderDetails.setCreatedDate(date);
            orderDetails.setLastUpdatedDate(date);

            orderDetailsRepo.save(orderDetails);

            return orderResponse;
        } catch (RazorpayException ex) {
            System.out.println(ex.getMessage());
        }
        return orderResponse;
    }

    /**
     * Updates order with the payment id returned from Razorpay
     * @param paymentResponse
     */
    @Override
    public void updateOrder(PaymentResponse paymentResponse) {
        Orders orderDetails = orderDetailsRepo
                .findById(paymentResponse.getOrderId())
                .orElse(null);
        try {
            if (null != orderDetails) {
                orderDetails.setRazorpayPaymentId(paymentResponse.getPaymentId());
                orderDetails.setOrderStatus(OrderStates.PAID);
                orderDetails.setPaymentStatus(PaymentStates.SUCCESS);
                java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
                orderDetails.setLastUpdatedDate(date);
                orderDetailsRepo.save(orderDetails);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Connect to Razorpay Service to create an order with test data
     *
     * @param amount
     * @return
     * @throws RazorpayException
     */
    private Order createRazorpayOrder(BigInteger amount) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount.multiply(new BigInteger("100")));
        options.put("currency", "INR");
        options.put("receipt", "txn_999901");
        options.put("payment_capture", 1);

        return razorpayClient.orders.create(options);
    }
}
