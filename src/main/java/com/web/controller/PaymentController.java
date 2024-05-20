package com.web.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.dto.PlaceOrderDto;
import com.web.dto.VNPayResponse;
import com.web.service.CartService;
import com.web.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

	private final PaymentService paymentService;
	private final CartService cartService;
	
	@GetMapping("/vn-pay")
    public ResponseEntity<VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseEntity<>(paymentService.createVnPayPayment(request),HttpStatus.OK);
    }
    @GetMapping("/vn-pay-callback")
    public ResponseEntity<VNPayResponse> payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
//        String orderDetails = request.getParameter("userId");
        long userId = Long.parseLong(request.getParameter("userId"));
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        if (status.equals("00")) {
        	cartService.placeOrder(new PlaceOrderDto(userId, address, description));
        	response.sendRedirect("http://localhost:4200/payment-success");
            return new ResponseEntity<>(new VNPayResponse("00", "Success", ""), HttpStatus.OK);
        } else {
        	response.sendRedirect("http://localhost:4200/payment-failed");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
