package com.example.trading.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.trading.entity.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

	@Query(value= "SELECT * FROM trade t WHERE t.symbol=:stockSymbol and type=:type and t.timestamp between :startDate and :endDate",nativeQuery = true )
	List<Trade> findBySymbolAndType(@Param("stockSymbol")String stockSymbol, @Param("type")String type,@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);

	@Query(value = "SELECT * FROM trade t where t.userid =?1 order by id asc ", nativeQuery = true)
	List<Trade> findAllByUserId(Integer userID);

	@Query(value="SELECT * FROM trade t WHERE t.symbol=:stockSymbol and t.timestamp between :startDate and :endDate",nativeQuery = true )
	List<Trade> findBySymbol(String stockSymbol, LocalDateTime startDate, LocalDateTime endDate);

	
}
