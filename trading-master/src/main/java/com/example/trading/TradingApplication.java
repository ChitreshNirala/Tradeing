package com.example.trading;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.trading.entity.Trade;
import com.example.trading.entity.User;
import com.example.trading.repo.TradeRepository;
import com.example.trading.repo.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class TradingApplication {

	@Autowired
	private TradeRepository tradeRepository;
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TradingApplication.class, args);
	}

	@PostConstruct
	void inIt(){
		TimeZone.setDefault(TimeZone.getTimeZone("IST (UTC +5:30)"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime currentDateTime = LocalDateTime.now();
		String formattedDateTime = currentDateTime.format(formatter);
		LocalDateTime dateTime = LocalDateTime.parse(formattedDateTime, formatter);
System.out.println("UTC:"+dateTime);
		
		User user = new User(1, "Suraj");
		User savedUser = userRepository.save(user);
		Trade trade = new Trade(1, "Buy", savedUser, "AC", 23, 132.23, dateTime);
		final Trade save1 = tradeRepository.save(trade);
		log.info("Created :"+ save1);
	}

}
