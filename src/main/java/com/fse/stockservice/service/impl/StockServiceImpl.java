package com.fse.stockservice.service.impl;

import com.fse.stockservice.common.CommonConstant;
import com.fse.stockservice.model.Stock;
import com.fse.stockservice.model.request.StockRequest;
import com.fse.stockservice.repository.StockRepository;
import com.fse.stockservice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository repository;

    @Override
    public Map<String, String> add(int companyCode, StockRequest request) {
        Stock stock = new Stock(request, companyCode);
        stock.setStockDateTime(new Timestamp(System.currentTimeMillis()));
        stock = repository.save(stock);
        Map<String, String> map = CommonConstant.getSuccessMapResponse();
        map.put("StockId", String.valueOf(stock.getStockId()));
        return map;
    }

    @Override
    public List<StockRequest> get(int companyCode, String startDate, String endDate) {
        return repository.findByCompanyCodeAndStockDateTimeBetween(
                companyCode, CommonConstant.getSqlDateFormatIfNotNull(startDate),
                CommonConstant.getSqlDateFormatIfNotNull(endDate)).stream()
                .map(StockRequest::new).collect(Collectors.toList());
    }
}
