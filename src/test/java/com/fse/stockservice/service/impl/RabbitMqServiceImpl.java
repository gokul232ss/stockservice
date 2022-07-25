package com.fse.stockservice.service.impl;

import com.fse.stockservice.model.Stock;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class RabbitMqServiceImpl {

    public void publishMessage(String fseExchange, String routingKey, Stock stock) {
    }
}
