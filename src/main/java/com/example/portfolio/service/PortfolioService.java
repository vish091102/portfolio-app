package com.example.portfolio.service;

import com.example.portfolio.entity.Stock;
import com.example.portfolio.entity.Trade;
import com.example.portfolio.entity.User;
import com.example.portfolio.repo.StockRepository;
import com.example.portfolio.repo.TradeRepository;
import com.example.portfolio.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getUserPortfolio(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        List<Trade> trades = userOpt.get().getTrades();

        Map<Long, Map<String, Object>> stockHoldings = new HashMap<>();

        for (Trade trade : trades) {
            Stock stock = trade.getStock();
            long stockId = stock.getId();
            String stockName = stock.getStockName();

            Map<String, Object> holding = stockHoldings.getOrDefault(stockId, new HashMap<>());
            holding.putIfAbsent("stockName", stockName);
            holding.putIfAbsent("stockId", stockId);
            holding.putIfAbsent("quantity", 0);
            holding.putIfAbsent("buyPrice", 0.0);

            int currentQuantity = (int) holding.get("quantity");
            double totalBuyPrice = (double) holding.get("buyPrice");

            if ("BUY".equalsIgnoreCase(trade.getTradeType())) {
                int newQuantity = currentQuantity + trade.getQuantity();
                double newBuyPrice = ((totalBuyPrice * currentQuantity) + (trade.getBuyPrice() * trade.getQuantity())) / newQuantity;

                holding.put("quantity", newQuantity);
                holding.put("buyPrice", newBuyPrice);
            } else if ("SELL".equalsIgnoreCase(trade.getTradeType())) {
                int newQuantity = currentQuantity - trade.getQuantity();
                if (newQuantity < 0) {
                    throw new IllegalArgumentException("Cannot sell more shares than owned for stock: " + stockName);
                }
                holding.put("quantity", newQuantity);
            }

            stockHoldings.put(stockId, holding);
        }

        double totalBuyPrice = 0;
        double totalCurrentValue = 0;
        List<Map<String, Object>> holdings = new ArrayList<>();

        for (Map<String, Object> holding : stockHoldings.values()) {
            int quantity = (int) holding.get("quantity");
            if (quantity > 0) {
                long stockId = (long) holding.get("stockId");
                Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("Stock not found with ID: " + stockId));
                double currPrice = stock.getCurrPrice();
                double buyPrice = (double) holding.get("buyPrice");
                double currentValue = currPrice * quantity;
                double gainLoss = (currPrice - buyPrice) * quantity;

                holding.put("currentPrice", currPrice);
                holding.put("gainLoss", gainLoss);

                totalBuyPrice += buyPrice * quantity;
                totalCurrentValue += currentValue;

                holdings.add(holding);
            }
        }

        double profitLoss = totalCurrentValue - totalBuyPrice;
        double profitLossPercentage = (totalBuyPrice > 0) ? (profitLoss / totalBuyPrice) * 100 : 0;

        Map<String, Object> portfolio = new HashMap<>();
        portfolio.put("holdings", holdings);
        portfolio.put("totalBuyPrice", totalBuyPrice);
        portfolio.put("totalCurrentValue", totalCurrentValue);
        portfolio.put("profitLoss", profitLoss);
        portfolio.put("profitLossPercentage", profitLossPercentage);

        return portfolio;
    }
}
