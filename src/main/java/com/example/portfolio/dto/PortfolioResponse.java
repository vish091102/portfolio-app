package com.example.portfolio.dto;

import com.example.portfolio.helper.PortfolioStock;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class PortfolioResponse {
    private List<HoldingResponse> holdings;
    private double totalPortfolioHolding;
    private double totalBuyPrice;
    private double totalPL;
    private double plPercentage;

    public void calculatePortfolio(Collection<PortfolioStock> portfolioStocks) {
        List<HoldingResponse> holdingList = new ArrayList<>();
        double totalHoldingValue = 0;
        double totalBuyValue = 0;

        for (PortfolioStock stock : portfolioStocks) {
            double currentHoldingValue = stock.getQuantity() * stock.getCurrentPrice();

            double totalBuyCost = stock.getQuantity() * stock.getAverageBuyPrice();

            double gainLoss = currentHoldingValue - totalBuyCost;

            HoldingResponse holding = new HoldingResponse();
            holding.setStockId(stock.getStock().getId());
            holding.setStockName(stock.getStock().getName());
            holding.setQuantity(stock.getQuantity());
            holding.setBuyPrice(stock.getAverageBuyPrice());
            holding.setCurrentPrice(stock.getCurrentPrice());
            holding.setGainLoss(gainLoss);

            holdingList.add(holding);

            totalHoldingValue += currentHoldingValue;
            totalBuyValue += totalBuyCost;
        }

        this.holdings = holdingList;
        this.totalPortfolioHolding = totalHoldingValue;
        this.totalBuyPrice = totalBuyValue;
        this.totalPL = totalHoldingValue - totalBuyValue;
        this.plPercentage = totalBuyValue > 0 ? (this.totalPL / totalBuyValue) * 100 : 0;
    }
}
