package com.example.trading.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "trade")
@Table(name = "trade")
@AllArgsConstructor
@NoArgsConstructor
public class Trade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "Trade Id",name="id",required=true,value="Integer value")
	@Id
	private Integer id;
	
	@ApiModelProperty(notes = "Trade Type",name="type",required=true,value="Buy or Sell")
	private String type;
	
	@ApiModelProperty(notes = "User entity MApping",name="user",required=true,value="user class")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userID")
	private User user;
	
	@ApiModelProperty(notes = "trade Symbol",name="symbol",required=true,value="trade symbol")
	private String symbol;
	
	@ApiModelProperty(notes = "Range of share between 10 to 30",name="shares",required=true,value="10-30")
	@Min(value = 10, message = "shares should not be less than 10")
	@Max(value = 30, message = "shares should not be greater than 30")
	private Integer shares;
	
	@ApiModelProperty(notes = "price of share between 130.42 to 195.65",name="price",required=true,value="130.42 to 195.65")
	@Digits(integer = 6, fraction = 2)
	@DecimalMin(value = "130.42", inclusive = true)
	@DecimalMax(value = "195.65", inclusive = true)
	private Double price;
	
	@ApiModelProperty(notes = "created date and time in yyyy-MM-dd HH:mm:ss format",name="timestamp",required=true,value="date and time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;

}
