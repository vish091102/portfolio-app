package com.example.portfolio.service;

import com.example.portfolio.dto.PortfolioResponse;
import com.example.portfolio.entity.Trade;
import com.example.portfolio.helper.PortfolioStock;
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

    public PortfolioResponse getPortfolio(Long userId) {
        List<Trade> trades = tradeRepository.findByUserAccId(userId);

        Map<Long, PortfolioStock> portfolioMap = new HashMap<>();

        for(Trade trade: trades) {
            Long stockId = trade.getStock().getId();
            PortfolioStock portfolioStock = portfolioMap.getOrDefault(stockId, new PortfolioStock(trade.getStock(), 0, 0, 0));

            if("BUY".equalsIgnoreCase(trade.getTradeType())) {
                portfolioStock.addQuantity(trade.getQuantity(), trade.getStock().getOpenPrice());
            } else if("SELL".equalsIgnoreCase(trade.getTradeType())) {
                portfolioStock.reduceQuantity(trade.getQuantity());
            }

            portfolioMap.put(stockId, portfolioStock);
        }

        PortfolioResponse response = new PortfolioResponse();
        response.calculatePortfolio(portfolioMap.values());
        return response;
    }
}
