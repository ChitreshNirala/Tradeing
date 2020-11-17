package com.example.trading.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.AssertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.trading.entity.Trade;
import com.example.trading.exception.RecordNotFoundException;
import com.example.trading.repo.TradeRepository;

@ExtendWith(SpringExtension.class)
public class TradeServiceTest {
	@InjectMocks
	private TradeService tradeService;

	@Mock
	private TradeRepository tradeRepository;

	@Test
	void testFindTradeById() {
		when(tradeRepository.findById(Mockito.anyInt())).thenReturn(Optional.<Trade>of(getTrade()));
		Trade tradeById = tradeService.findTradeById(Integer.MAX_VALUE);
		assertNotNull(tradeById);
		assertEquals(123, tradeById.getId());
	}

	@Test
	void testFindTradeByIdForNull() {
		when(tradeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Trade tradeById = tradeService.findTradeById(Integer.MAX_VALUE);
		assertNull(tradeById);
	}

	@Test
	void testFindAll() {
		when(tradeRepository.findAll()).thenReturn(Arrays.asList(getTrade()));
		List<Trade> findAll = tradeService.findAll();
		assertNotNull(findAll);
		assertEquals(123, findAll.get(0).getId());
	}

	@Test
	void findByUserId() {
		when(tradeRepository.findAllByUserId(Mockito.anyInt())).thenReturn(Arrays.asList(getTrade()));
		List<Trade> userId = tradeService.findByUserId(Integer.MAX_VALUE);
		assertNotNull(userId);
		assertEquals(123, userId.get(0).getId());
	}

	@Test
	void findByUserIdException() {
		when(tradeRepository.findAllByUserId(Mockito.anyInt())).thenReturn(Arrays.asList());
		RecordNotFoundException assertThrows = assertThrows(RecordNotFoundException.class, () -> {
			tradeService.findByUserId(Integer.MAX_VALUE);
		});
		assertNotNull(assertThrows);
		assertNotEquals("User Not Found with id:" + 123, assertThrows.getMessage());

	}

	@Test
	void saveRequest() {
		when(tradeRepository.save(Mockito.any())).thenReturn(getTrade());
		Trade request = tradeService.saveRequest(getTrade());
		assertNotNull(request);
		assertEquals(getTrade().getId(), request.getId());
	}

	@Test
	void deleteRecordsAll() {
		when(tradeRepository.findAll()).thenReturn(Arrays.asList(getTrade()));
		List<Trade> deleteRecordsAll = tradeService.deleteRecordsAll();
		Assertions.assertFalse(deleteRecordsAll.isEmpty());
	}

	@Test
	void filterData() {
		when(tradeRepository.findBySymbolAndType(Mockito.anyString(), Mockito.anyString(), Mockito.any(),
				Mockito.any())).thenReturn(Arrays.asList(getTrade()));
		List<Trade> filterData = tradeService.filterData("stockSymbol", "type", LocalDateTime.now(),
				LocalDateTime.now());
		assertNotNull(filterData);
		assertEquals(123, filterData.get(0).getId());
		//exception condition
	}
	//ye maine try lkiya
	@Test
	void filterDataException() {
		when(tradeRepository.findBySymbolAndType(Mockito.anyString(), Mockito.anyString(), Mockito.any(),
				Mockito.any())).thenReturn(Arrays.asList());
		//List<Trade> findBySymbolAndType = tradeRepository.findBySymbolAndType("symbol", "type", LocalDateTime.now(),LocalDateTime.now());
		RecordNotFoundException assertThrows = assertThrows(RecordNotFoundException.class, () -> {
			tradeService.filterData("symbol", "type", LocalDateTime.now(), LocalDateTime.now());
		});
		assertNotNull(assertThrows);
		assertEquals("No record found with trade symbol and trade type with given date range",assertThrows.getMessage());		
		
	}

	@Test
	void filterDataBySymbol() {
		when(tradeRepository.findBySymbol(Mockito.anyString(), Mockito.any(), Mockito.any()))
				.thenReturn(Arrays.asList(getTrade()));
		List<Trade> filterDataBySymbol = tradeService.filterDataBySymbol("stockSymbol", LocalDateTime.now(),
				LocalDateTime.now());
		assertNotNull(filterDataBySymbol);
		assertEquals(123, filterDataBySymbol.get(0).getId());
	}

	private Trade getTrade() {
		final Trade trade = new Trade();
		trade.setId(123);
		return trade;
	}

}
