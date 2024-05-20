package com.web.service;


import com.web.dto.VNPayResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {

	VNPayResponse createVnPayPayment(HttpServletRequest request);

}
