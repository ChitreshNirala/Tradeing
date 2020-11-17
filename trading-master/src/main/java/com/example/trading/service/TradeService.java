package com.example.trading.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trading.entity.Trade;
import com.example.trading.exception.RecordNotFoundException;
import com.example.trading.exception.TradeException;
import com.example.trading.repo.TradeRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	public Trade findTradeById(Integer id) {
		final Optional<Trade> byId = tradeRepository.findById(id);
		Trade trade = null;
		if (byId.isPresent()) {
			trade = byId.get();
		}

		return trade;
	}

	public List<Trade> findAll() {

		return tradeRepository.findAll();
	}

	public List<Trade> findByUserId(Integer userID) {
		List<Trade> allTrades = tradeRepository.findAllByUserId(userID);
		if (allTrades.isEmpty()) {
			log.error("User Not Found with id:"+userID);
			throw new RecordNotFoundException("User Not Found with id:" + userID);
		}
		return allTrades;
	}

	public Trade saveRequest(Trade request) {
		return tradeRepository.save(request);
	}

	public List<Trade> deleteRecordsAll() {
		final List<Trade> tradeList = tradeRepository.findAll();
		if (tradeList != null) {
			tradeRepository.deleteAll(tradeList);
		}

		return tradeRepository.findAll();
	}

	public List<Trade> filterData(String stockSymbol, String type, LocalDateTime startDate, LocalDateTime endDate) {
		List<Trade> list =tradeRepository.findBySymbolAndType(stockSymbol, type,startDate,endDate);
		if(!list.isEmpty()) {
		 Collections.sort(list,(list1,list2) -> list1.getId().compareTo(list2.getId()));
		}else {
			throw new RecordNotFoundException("No record found with trade symbol and trade type with given date range");
		}
		return list;
	}

	public List<Trade> filterDataBySymbol(String stockSymbol, LocalDateTime startDate, LocalDateTime endDate) {
		List<Trade> list=tradeRepository.findBySymbol(stockSymbol,startDate,endDate);
		if(!list.isEmpty()) {
			 Collections.sort(list,(list1,list2) -> list1.getPrice().compareTo(list2.getPrice()));
			}else {
				throw new RecordNotFoundException("There are no trades in the given date range");
			}
		for (Trade trade : list) {
			System.out.println(trade.getPrice());
		}
		return list;
	}
}
