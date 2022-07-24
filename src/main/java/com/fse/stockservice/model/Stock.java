package com.fse.stockservice.model;

import com.fse.stockservice.model.request.StockRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockId;
    private int companyCode;
    private String stockName;
    private double price;
    private Timestamp stockDateTime;

    public Stock(StockRequest request, int companyCode) {
        if (request.getStockId() != null) {
            this.stockId = request.getStockId();
        }
        this.companyCode = companyCode;
        this.stockName = request.getStockName();
        this.price = request.getPrice();
    }
}
