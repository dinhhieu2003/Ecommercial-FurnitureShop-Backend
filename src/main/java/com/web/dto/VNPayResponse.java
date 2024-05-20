package com.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VNPayResponse {

	private String code;
    private String message;
    private String paymentUrl;
}
