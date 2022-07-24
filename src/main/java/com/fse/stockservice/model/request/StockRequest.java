package com.fse.stockservice.model.request;

import com.fse.stockservice.common.CommonConstant;
import com.fse.stockservice.model.Stock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StockRequest {
    private Integer stockId;
    private String stockName;
    private double price;
    private String stockDateTime;

    public StockRequest(Stock stock) {
        this.stockId = stock.getStockId();
        this.stockName = stock.getStockName();
        this.price = stock.getPrice();
        this.stockDateTime = CommonConstant.getUIDateFormatIfNotNull(stock.getStockDateTime());
    }
}
