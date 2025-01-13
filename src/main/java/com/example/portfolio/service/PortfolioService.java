package com.example.portfolio.service;

import com.example.portfolio.entity.Stock;
import com.example.portfolio.entity.Trade;
import com.example.portfolio.repo.StockRepository;
import com.example.portfolio.repo.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private StockRepository stockRepository;

    public Map<String, Object> getUserPortfolio(Long userId) {
        List<Trade> trades = tradeRepository.findAllByUserAccId(userId);

        double totalBuyPrice = 0;
        double totalCurrentValue = 0;

        List<Map<String, Object>> holdings = new ArrayList<>();

        for(Trade trade : trades) {
            Optional<Stock> stockOpt = stockRepository.findById(trade.getStockId());
            if(stockOpt.isEmpty()) {
                continue;
            }

            Stock stock = stockOpt.get();

            double currPrice = stock.getCurrPrice();
            double buyPrice = trade.getBuyPrice();
            double currValue = currPrice * trade.getQuantity();
            double gainLoss = currValue - buyPrice;

            Map<String, Object> holding = new HashMap<>();
            holding.put("stockName", stock.getStockName());
            holding.put("quantity", trade.getQuantity());
            holding.put("buyPrice", buyPrice);
            holding.put("currentPrice", currValue);
            holding.put("gainLoss", gainLoss);

            holdings.add(holding);

            totalBuyPrice += buyPrice;
            totalCurrentValue += currValue;
        }

        Map<String, Object> portfolio = new HashMap<>();
        portfolio.put("holdings", holdings);
        portfolio.put("totalBuyPrice", totalBuyPrice);
        portfolio.put("totalCurrentValue", totalCurrentValue);
        portfolio.put("profitLoss", totalCurrentValue - totalBuyPrice);
        portfolio.put("profitLossPercentage", (totalCurrentValue - totalBuyPrice) / totalBuyPrice * 100);

        return portfolio;
    }
}
