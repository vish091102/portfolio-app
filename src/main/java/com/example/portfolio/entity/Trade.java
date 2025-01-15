package com.example.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    //private Long userAccId;
    private String tradeType;        //Buy/Sell
    private int quantity;
    //private Long stockId;
    private double buyPrice;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getUserAccId() {
//        return userAccId;
//    }
//
//    public void setUserAccId(Long userAccId) {
//        this.userAccId = userAccId;
//    }
//
//    public String getTradeType() {
//        return tradeType;
//    }
//
//    public void setTradeType(String tradeType) {
//        this.tradeType = tradeType;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public Long getStockId() {
//        return stockId;
//    }
//
//    public void setStockId(Long stockId) {
//        this.stockId = stockId;
//    }
//
//    public double getBuyPrice() {
//        return buyPrice;
//    }
//
//    public void setBuyPrice(double buyPrice) {
//        this.buyPrice = buyPrice;
//    }
//
//    @Override
//    public String toString() {
//        return "Trade{" +
//                "id=" + id +
//                ", userAccId=" + userAccId +
//                ", tradeType='" + tradeType + '\'' +
//                ", quantity=" + quantity +
//                ", stockId=" + stockId +
//                ", buyPrice=" + buyPrice +
//                '}';
//    }
}
