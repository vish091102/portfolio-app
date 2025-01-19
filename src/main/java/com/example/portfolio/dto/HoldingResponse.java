package com.example.portfolio.dto;

import lombok.Data;

@Data
public class HoldingResponse {
    private Long stockId;
    private String stockName;
    private int quantity;
    private double buyPrice;
    private double currentPrice;
    private double gainLoss;
}
