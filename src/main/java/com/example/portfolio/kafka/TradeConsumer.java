package com.example.portfolio.kafka;

import com.example.portfolio.entity.Trade;
import com.example.portfolio.service.TradeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TradeConsumer {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "trade-topic", groupId = "trade-group", containerFactory = "kafkaListenerContainerFactory")
    public void processTrade(String message) throws JsonProcessingException {
        Trade trade = objectMapper.readValue(message, Trade.class);
        System.out.println("Processing trade from Kafka: " + trade);
        tradeService.recordTrade(trade.getUser().getId(), trade.getTradeType(), trade.getQuantity(), trade.getStock().getId(), trade.getBuyPrice());
    }
}
