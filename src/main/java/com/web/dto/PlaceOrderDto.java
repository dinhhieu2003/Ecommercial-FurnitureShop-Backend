package com.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceOrderDto {
	private long userId;
	
	private String address;
	
	private String description;
}
