package com.example.portfolio.service;

import com.example.portfolio.dto.TradeRequest;
import com.example.portfolio.dto.TradeResponse;
import com.example.portfolio.entity.Stock;
import com.example.portfolio.entity.Trade;
import com.example.portfolio.repo.StockRepository;
import com.example.portfolio.repo.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private StockRepository stockRepository;

    public TradeResponse executeTrade(TradeRequest tradeRequest) {
        TradeResponse response = new TradeResponse();
        Optional<Stock> stock = stockRepository.findById(tradeRequest.getStockId());

        if(stock.isEmpty()) {
            response.setStatus("FAILURE");
            response.setMessage("Stock not found.");
            return response;
        }

        if(tradeRequest.getQuantity() <= 0) {
            response.setStatus("FAILURE");
            response.setMessage("Quantity must be greater than zero.");
            return response;
        }

        Trade trade = new Trade();
        trade.setUserAccId(tradeRequest.getUserAccId());
        trade.setTradeType(tradeRequest.getTradeTpye());
        trade.setQuantity(tradeRequest.getQuantity());
        trade.setStock(stock.get());
        tradeRepository.save(trade);

        response.setStatus("SUCCESS");
        response.setMessage("Trade recorded successfully.");
        return response;
    }

    /*public void recordTrade(Long userId, String tradeType, int quantity, Long stockId, double buyPrice) {
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
    }*/
}
