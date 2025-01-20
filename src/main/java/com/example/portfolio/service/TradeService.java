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

    public TradeResponse recordTrade(TradeRequest tradeRequest) {
        TradeResponse response = new TradeResponse();

        Optional<Stock> stock = stockRepository.findById(tradeRequest.getStockId());

        if(stock.isEmpty()) {
            throw new IllegalArgumentException("Stock not found for Id: " + tradeRequest.getStockId());
        }
        if(tradeRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        Trade trade = new Trade();
        trade.setUserAccId(tradeRequest.getUserAccId());
        trade.setTradeType(tradeRequest.getTradeTpye());
        trade.setQuantity(tradeRequest.getQuantity());
        trade.setStock(stock.get());
        tradeRepository.save(trade);

        response.setStatus("SUCCESS");
        response.setMessage("Trade executed successfully.");

        return response;
    }
}
