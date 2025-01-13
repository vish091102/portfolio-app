package com.example.portfolio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userAccId;
    private String tradeType;        //Buy/Sell
    private int quantity;
    private Long stockId;
    private double buyPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserAccId() {
        return userAccId;
    }

    public void setUserAccId(Long userAccId) {
        this.userAccId = userAccId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", userAccId=" + userAccId +
                ", tradeType='" + tradeType + '\'' +
                ", quantity=" + quantity +
                ", stockId=" + stockId +
                ", buyPrice=" + buyPrice +
                '}';
    }
}
