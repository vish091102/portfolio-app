package com.example.portfolio.controller;

import com.example.portfolio.entity.Trade;
import com.example.portfolio.kafka.TradeProducer;
import com.example.portfolio.service.TradeService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    private TradeProducer tradeProducer;

    @PostMapping("/execute")
    public ResponseEntity<String> executeTrade(@RequestBody Trade tradeRequest) {
        try{
            String result = tradeService.executeTrade(
                    tradeRequest.getUser().getId(),
                    tradeRequest.getTradeType(),
                    tradeRequest.getQuantity(),
                    tradeRequest.getStock().getId()
            );

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to execute trade: " + e.getMessage());
        }
    }

    @PostMapping("/record")
    public ResponseEntity<String> recordTrade(@RequestBody Trade trade) throws JsonProcessingException {
        tradeProducer.sendTrade(trade);
        return ResponseEntity.ok("Trade sent to Kafka for processing.");
    }
}
