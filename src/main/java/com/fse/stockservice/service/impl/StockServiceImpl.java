package com.fse.stockservice.service.impl;

import com.fse.stockservice.common.CommonConstant;
import com.fse.stockservice.exception.CommonInternalException;
import com.fse.stockservice.model.Stock;
import com.fse.stockservice.model.request.StockRequest;
import com.fse.stockservice.repository.StockRepository;
import com.fse.stockservice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private RabbitMqServiceImpl rabbitMqService;

    @Value("${com.fse.exchange}")
    private String fseExchange;

    @Value("${com.fse.stock.routingkey}")
    private String routingKey;

    @Override
    public Map<String, String> add(int companyCode, StockRequest request)
            throws CommonInternalException {
        if (request.getPrice() != 0) {
            Stock stock = new Stock(request, companyCode);
            stock.setStockDateTime(new Timestamp(System.currentTimeMillis()));
            stock = repository.save(stock);
            Map<String, String> map = CommonConstant.getSuccessMapResponse();
            map.put("StockId", String.valueOf(stock.getStockId()));
            rabbitMqService.publishMessage(fseExchange, routingKey, stock);
            return map;
        } else {
            throw new CommonInternalException("Price value cannot be 0", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<StockRequest> get(int companyCode, String startDate, String endDate)
            throws CommonInternalException {
        List<Stock> stockList = repository.findByCompanyCodeAndStockDateTimeBetween(
                companyCode, CommonConstant.getSqlDateFormatIfNotNull(startDate),
                CommonConstant.getSqlDateFormatIfNotNull(endDate));
        if (!stockList.isEmpty()) {
            return stockList.stream()
                    .map(StockRequest::new).collect(Collectors.toList());
        } else {
            throw new CommonInternalException("Not records found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void deleteAllCompanyStock(Map<String, Object> mapData) {
        repository.deleteByCompanyCode(Integer.parseInt(mapData.get("companyCode").toString()));
    }
}
