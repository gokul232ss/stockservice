package com.fse.stockservice.service;

import com.fse.stockservice.exception.CommonInternalException;
import com.fse.stockservice.model.request.StockRequest;

import java.util.List;
import java.util.Map;

public interface StockService {
    Map<String, String> add(int companyCode, StockRequest request) throws CommonInternalException;

    List<StockRequest> get(int companyCode, String startDate, String endDate) throws CommonInternalException;

    void deleteAllCompanyStock(Map<String, Object> mapData);
}
