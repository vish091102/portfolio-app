package com.example.portfolio.controller;

import com.example.portfolio.entity.Trade;
import com.example.portfolio.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade/api")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeTrade(@RequestBody Trade tradeRequest) {
        String result = tradeService.executeTrade(
                tradeRequest.getUserAccId(),
                tradeRequest.getTradeType(),
                tradeRequest.getQuantity(),
                tradeRequest.getStockId()
        );

        return ResponseEntity.ok(result);
    }
}
