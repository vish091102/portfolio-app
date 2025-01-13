package com.example.portfolio.service;

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

    public String executeTrade(Long userAccId, String tradeType, int quantity, Long stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);
        if(stock.isEmpty()) {
            return "Stock not found!";
        }

        double price = stock.get().getCurrPrice();
        Trade trade = new Trade();
        trade.setUserAccId(userAccId);
        trade.setTradeType(tradeType);
        trade.setQuantity(quantity);
        trade.setStockId(stockId);
        trade.setBuyPrice(price);

        tradeRepository.save(trade);
        return "Trade executed successfully!";
    }
}
