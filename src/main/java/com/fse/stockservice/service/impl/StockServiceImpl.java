package com.fse.stockservice.service.impl;

import com.fse.stockservice.common.CommonConstant;
import com.fse.stockservice.model.Stock;
import com.fse.stockservice.model.request.StockRequest;
import com.fse.stockservice.repository.StockRepository;
import com.fse.stockservice.service.StockService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private RabbitTemplate template;

    @Value("${com.fse.exchange}")
    private String fseExchange;

    @Value("${com.fse.stock.routingkey}")
    private String routingKey;

    @Override
    public Map<String, String> add(int companyCode, StockRequest request) {
        Stock stock = new Stock(request, companyCode);
        stock.setStockDateTime(new Timestamp(System.currentTimeMillis()));
        stock = repository.save(stock);
        Map<String, String> map = CommonConstant.getSuccessMapResponse();
        map.put("StockId", String.valueOf(stock.getStockId()));
        template.convertAndSend(fseExchange, routingKey, stock);
        return map;
    }

    @Override
    public List<StockRequest> get(int companyCode, String startDate, String endDate) {
        return repository.findByCompanyCodeAndStockDateTimeBetween(
                companyCode, CommonConstant.getSqlDateFormatIfNotNull(startDate),
                CommonConstant.getSqlDateFormatIfNotNull(endDate)).stream()
                .map(StockRequest::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllCompanyStock(Map<String, Object> mapData) {
        repository.deleteByCompanyCode(Integer.parseInt(mapData.get("companyCode").toString()));
    }
}
