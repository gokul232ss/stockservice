package com.fse.stockservice.web;

import com.fse.stockservice.model.request.StockRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/stock")
public interface StockInterface {

    @GetMapping("/getping")
    ResponseEntity<String> getping();

    @PostMapping("/add/{companyCode}")
    ResponseEntity<Map<String, String>> add(
            @PathVariable("companyCode") int companyCode,
            @RequestBody StockRequest request);

    @GetMapping("/get/{companyCode}/{startDate}/{endDate}")
    ResponseEntity<List<StockRequest>> get(
            @PathVariable("companyCode") int companyCode,
            @PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate);
}
