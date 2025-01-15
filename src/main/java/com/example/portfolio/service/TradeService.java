package com.example.portfolio.service;

import com.example.portfolio.entity.Stock;
import com.example.portfolio.entity.Trade;
import com.example.portfolio.entity.User;
import com.example.portfolio.repo.StockRepository;
import com.example.portfolio.repo.TradeRepository;
import com.example.portfolio.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserRepository userRepository;

    public String executeTrade(Long userId, String tradeType, int quantity, Long stockId) {
        //Validate User
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return "User not found!";
        }

        //Validate Stock
        Optional<Stock> stockOpt = stockRepository.findById(stockId);
        if (stockOpt.isEmpty()) {
            return "Stock not found!";
        }

        double price = stockOpt.get().getCurrPrice();

        Trade trade = new Trade();
        trade.setUser(userOpt.get());
        trade.setTradeType(tradeType);
        trade.setQuantity(quantity);
        trade.setStock(stockOpt.get());
        trade.setBuyPrice(price);

        tradeRepository.save(trade);
        return "Trade executed successfully!";
    }

    public void recordTrade(Long userId, String tradeType, int quantity, Long stockId, double buyPrice) {
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found.");
        }
        User user = userOpt.get();

        Optional<Stock> stockOpt = stockRepository.findById(stockId);
        if(stockOpt.isEmpty()) {
            throw new IllegalArgumentException("Stock not found.");
        }
        Stock stock = stockOpt.get();

        Trade trade = new Trade();
        trade.setUser(user);
        trade.setTradeType(tradeType);
        trade.setQuantity(quantity);
        trade.setStock(stock);
        trade.setBuyPrice(buyPrice);

        tradeRepository.save(trade);
        System.out.println("Trade executed successfully: " + trade);
    }
}
