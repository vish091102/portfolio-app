package com.example.portfolio.dto;

import lombok.Data;

import java.util.Map;

@Data
public class StockDetailsResponse {
    private Long stockId;
    private String stockName;
    private Map<String, Double> prices;
}
