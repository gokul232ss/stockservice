package com.fse.stockservice.repository;

import com.fse.stockservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByCompanyCodeAndStockDateTimeBetween(int companyCode, Date fromDate, Date toDate);
}
