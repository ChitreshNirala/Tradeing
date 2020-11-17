package com.example.trading.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TradeResponse {
	private String symbol;
	private Double highest; 
	private Double lowest; 

}
