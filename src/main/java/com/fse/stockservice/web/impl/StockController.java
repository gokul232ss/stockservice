package com.fse.stockservice.web.impl;

import com.fse.stockservice.model.request.StockRequest;
import com.fse.stockservice.service.StockService;
import com.fse.stockservice.web.StockInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StockController implements StockInterface {

    @Autowired
    private StockService service;

    @Override
    public ResponseEntity<String> getping() {
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, String>> add(int companyCode, StockRequest request) {
        return new ResponseEntity<>(service.add(companyCode, request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<StockRequest>> get(int companyCode, String startDate, String endDate) {
        return new ResponseEntity<>(service.get(companyCode, startDate, endDate), HttpStatus.OK);
    }

}
