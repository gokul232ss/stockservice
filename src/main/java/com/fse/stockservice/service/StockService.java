package com.fse.stockservice.service;

import com.fse.stockservice.model.request.StockRequest;

import java.util.List;
import java.util.Map;

public interface StockService {
    Map<String, String> add(int companyCode, StockRequest request);

    List<StockRequest> get(int companyCode, String startDate, String endDate);
}
