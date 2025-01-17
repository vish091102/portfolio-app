package com.example.portfolio.service;

import com.example.portfolio.dto.StockDetailsResponse;
import com.example.portfolio.entity.Stock;
import com.example.portfolio.repo.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public StockDetailsResponse getStockDetails(Long stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);

        if(stock.isEmpty()) {
            throw new RuntimeException("Stock not found.");
        }

        StockDetailsResponse response = new StockDetailsResponse();
        response.setStockId(stock.get().getId());
        response.setStockName(stock.get().getName());

        Map<String, Double> prices = new HashMap<>();
        prices.put("open", stock.get().getOpenPrice());
        prices.put("close", stock.get().getClosePrice());
        prices.put("high", stock.get().getHighPrice());
        prices.put("low", stock.get().getLowPrice());
        prices.put("settlementPrice", stock.get().getSettlementPrice());
        response.setPrices(prices);

        return response;
    }
}
