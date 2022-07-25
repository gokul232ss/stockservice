package com.fse.stockservice.service.impl;

import com.fse.stockservice.model.Stock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqServiceImpl {

    @Autowired
    private RabbitTemplate template;

    public void publishMessage(String fseExchange, String routingKey, Stock stock) {
        template.convertAndSend(fseExchange, routingKey, stock);
    }
}
