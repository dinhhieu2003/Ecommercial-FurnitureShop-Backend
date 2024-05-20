package com.web.service.impl;

import org.springframework.stereotype.Service;

import com.web.config.VnpayConfig;
import com.web.dto.VNPayResponse;
import com.web.service.PaymentService;
import com.web.utils.VNPayUtil;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

	private final VnpayConfig vnPayConfig;

	@Override
	public VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        
        // params of User Order details
        long userId = Long.parseLong(request.getParameter("userId"));
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(String.valueOf(userId), address, description);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        
        return new VNPayResponse("ok","success", paymentUrl);
    }
}
