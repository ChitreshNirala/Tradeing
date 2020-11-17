package com.example.trading.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//import com.example.trading.dto.UserDto;
import com.example.trading.entity.Trade;
import com.example.trading.entity.User;

@ExtendWith(SpringExtension.class)
class TradeControllerTest extends Custom {

	
	@Test
	void saveTrade() throws Exception {
		
		when(tradeService.findTradeById(Mockito.anyInt())).thenReturn(getEmptyTrade());
		when(tradeService.saveRequest(Mockito.any())).thenReturn(getTrade());
		MockHttpServletResponse response = mvc
				.perform(MockMvcRequestBuilders.post(("/trade")).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(getTrade())))
				.andReturn().getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
	private Trade getEmptyTrade() {
	
	return null;
}
	@Test
	void getUserSpecificTrades() throws Exception {
		when(tradeService.findByUserId(anyInt())).thenReturn(Arrays.asList(getTrade()));
		MockHttpServletResponse response = mvc
				.perform(MockMvcRequestBuilders.get(("/trade/users/{userID}"), 123).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void deleteAllTrades() throws Exception {
		when(tradeService.deleteRecordsAll()).thenReturn(Arrays.asList(getTrade()));
		MockHttpServletResponse response = mvc
				.perform(MockMvcRequestBuilders.delete(("/trade")).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void getAllTrades() throws Exception {
		when(tradeService.findAll()).thenReturn(Arrays.asList(getTrade()));
		MockHttpServletResponse response = mvc
				.perform(MockMvcRequestBuilders.get(("/trade")).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	LocalDateTime currentDateTime = LocalDateTime.now();
	private Trade getTrade() {
		TimeZone.setDefault(TimeZone.getTimeZone("IST (UTC +5:30)"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		String formattedDateTime = currentDateTime.format(formatter);
		final LocalDateTime dateTime = LocalDateTime.parse(formattedDateTime, formatter);
		
		final Trade trade = new Trade();
		trade.setId(123);
		trade.setPrice(132.34);
		trade.setShares(28);
		trade.setSymbol("AC");
		trade.setType("Sell");
		trade.setTimestamp(dateTime);
		trade.setUser(new User(1, "AB"));
		System.out.println(trade);
		return trade;
	}

	@Test
	void getStocks() throws Exception {
		when(tradeService.filterData(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any()))
				.thenReturn(Arrays.asList(getTrade()));
		MockHttpServletResponse response = mvc
				.perform(MockMvcRequestBuilders.get(("/trade/stocks/{stockSymbol}/trades"), "stockSymbol")
						.accept(MediaType.APPLICATION_JSON).param("stockSymbol", "stockSymbol").param("type", "type")
						.param("start", "2020-09-22 01:01:22").param("end", "2020-09-22 01:01:22"))
				.andReturn().getResponse();
		assertNotNull(response);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void getStocksBySymbol() throws Exception {
		when(tradeService.filterDataBySymbol(Mockito.anyString(), Mockito.any(), Mockito.any()))
				.thenReturn(Arrays.asList(getTrade()));
		MockHttpServletResponse response = mvc
				.perform(MockMvcRequestBuilders.get(("/trade/stocks/{stockSymbol}/price"), "stockSymbol")
						.accept(MediaType.APPLICATION_JSON).param("stockSymbol", "stockSymbol")
						.param("start", "2020-09-22 01:01:22").param("end", "2020-09-22 01:01:22"))
				.andReturn().getResponse();
		assertNotNull(response);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	

}
