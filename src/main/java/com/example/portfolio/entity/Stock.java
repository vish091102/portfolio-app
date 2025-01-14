package com.example.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockName;
    private double currPrice;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getStockName() {
//        return stockName;
//    }
//
//    public void setStockName(String stockName) {
//        this.stockName = stockName;
//    }
//
//    public double getCurrPrice() {
//        return currPrice;
//    }
//
//    public void setCurrPrice(double currPrice) {
//        this.currPrice = currPrice;
//    }
//
//    @Override
//    public String toString() {
//        return "Stock{" +
//                "id=" + id +
//                ", stockName='" + stockName + '\'' +
//                ", currPrice=" + currPrice +
//                '}';
//    }
}
