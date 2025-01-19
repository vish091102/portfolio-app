package com.example.portfolio.dto;

import lombok.Data;

@Data
public class TradeRequest {

    private Long userAccId;
    private String tradeTpye;         //BUY/SELL
    private int quantity;
    private Long stockId;
}
