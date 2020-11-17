package com.example.trading.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.trading.entity.Trade;
import com.example.trading.entity.TradeResponse;
import com.example.trading.exception.Constant;
import com.example.trading.exception.TradeException;
import com.example.trading.service.TradeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="TradeController", description="REST APIs related to Trade and User Entity!!!!")
@RestController
@RequestMapping("/trade")
public class TradeController implements Constant {

	@Autowired
	private TradeService tradeService;

	@ApiOperation(value = "Save Trades  ", response = Iterable.class, tags = "saveTrade")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 201, message = "created!"), 
	            @ApiResponse(code = 400, message = "already Exist!!!"),
	            @ApiResponse(code = 404, message = "Id is null") })
	@PostMapping
	public ResponseEntity<Trade> saveTrade(@RequestBody @Valid Trade request) {
		 Trade exist=null;
		 Trade trade=null;
		String type = request.getType();
		if (type.equalsIgnoreCase(TYPE_BUY) || type.equalsIgnoreCase(TYPE_SELL)) {
			if (request.getId()!=0 ) {
			exist = tradeService.findTradeById(request.getId());
			} else {
				throw new TradeException("Id is null");
			}
		} else {
			throw new TradeException("trade type should be Buy or Sell");
		}
		if (exist == null) {
			trade = tradeService.saveRequest(request);
		} else {
			throw new TradeException("Record already exist");
		}
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(trade);
	}

	
	@ApiOperation(value = "Get All trade of Specific user ", response = Iterable.class, tags = "getUserSpecificTrades")
	@GetMapping("/users/{userID}")
	public ResponseEntity<List<Trade>> getUserSpecificTrades(@PathVariable Integer userID) {
		List<Trade> trades = tradeService.findByUserId(userID);
		return ResponseEntity.status(HttpStatus.OK).body(trades);
	}
	
	@ApiOperation(value = "Get All trades ", response = Iterable.class, tags = "getAllTrades")
	@GetMapping
	public ResponseEntity<List<Trade>> getAllTrades() {
		List<Trade> tradeList = tradeService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(tradeList);
	}

	@ApiOperation(value = "Delete All trades ",  tags = "deleteAllTrades")
	@DeleteMapping
	public ResponseEntity<List<Trade>> deleteAllTrades() {
		List<Trade> tradeList = tradeService.deleteRecordsAll();
		return ResponseEntity.status(HttpStatus.OK).body(tradeList);
	}
	
	@ApiOperation(value = "filtered by the stock symbol and trade type in the given date range:",  tags = "getStocks")
	@GetMapping("/stocks/{stockSymbol}/trades")
	@ResponseBody
	public ResponseEntity<List<Trade>> getStocks(@PathVariable(required = true, name="stockSymbol") String stockSymbol, 
							@RequestParam(name="type") String type,
							@RequestParam(name="start") String startDate, 
							@RequestParam(name="end") String endDate  ) {
		
		TimeZone.setDefault(TimeZone.getTimeZone("IST (UTC +5:30)"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(startDate, formatter);
		LocalDateTime dateTime1 = LocalDateTime.parse(endDate, formatter);
		/*System.out.println(stockSymbol);  delete thisha
		System.out.println(type);
		System.out.println(dateTime);
		System.out.println(dateTime1);*/
		
		List<Trade> tradeList = tradeService.filterData(stockSymbol,type,dateTime,dateTime1);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(tradeList);
	}
	
	@ApiOperation(value = "Returning the highest and lowest price for the stock symbol in the given date range",  tags = "getStocksBySymbol")
	@GetMapping("/stocks/{stockSymbol}/price")
	@ResponseBody
	public ResponseEntity<TradeResponse> getStocksBySymbol(@PathVariable(required = true, name="stockSymbol") String stockSymbol,
							@RequestParam(name="start") String startDate, 
							@RequestParam(name="end") String endDate  ) {
		TimeZone.setDefault(TimeZone.getTimeZone("IST (UTC +5:30)"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(startDate, formatter);
		LocalDateTime dateTime1 = LocalDateTime.parse(endDate, formatter);
		/*System.out.println(stockSymbol);
		System.out.println(dateTime);
		System.out.println(dateTime1);*/
		TradeResponse response = new TradeResponse();
		List<Trade> tradeList = tradeService.filterDataBySymbol(stockSymbol,dateTime,dateTime1);
		response.setLowest(tradeList.get(0).getPrice());
		response.setHighest(tradeList.get(tradeList.size()-1).getPrice());
		response.setSymbol(stockSymbol);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
