package com.example.portfolio.helper;

import com.example.portfolio.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioStock {
    private Stock stock;
    private int quantity;
    private double totalBuyValue;
    private int buyQuantity;

    public double getAverageBuyPrice() {
        return buyQuantity > 0 ? totalBuyValue / buyQuantity : 0;
    }

    public double getCurrentPrice() {
        return stock.getClosePrice();
    }

    public void addQuantity(int quantity, double price) {
        this.quantity += quantity;
        this.buyQuantity += quantity;
        this.totalBuyValue += quantity * price;
    }

    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
