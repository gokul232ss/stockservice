package com.fse.stockservice.config;

import com.fse.stockservice.common.CommonConstant;
import com.fse.stockservice.service.StockService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ListeningQueue {

    @Autowired
    private StockService service;

    @RabbitListener(queues = CommonConstant.DELETE_STOCK_QUEUE)
    public void consumeMessageFromQueue(Map<String, Object> mapData) {
        service.deleteAllCompanyStock(mapData);
    }
}
