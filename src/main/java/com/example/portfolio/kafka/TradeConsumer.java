//package com.example.portfolio.kafka;
//
//import com.example.portfolio.entity.Trade;
//import com.example.portfolio.service.TradeService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@Service
//public class TradeConsumer {
//
//    @Autowired
//    private TradeService tradeService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @KafkaListener(topics = "trade-topic", groupId = "trade-group", containerFactory = "kafkaListenerContainerFactory")
//    public void processTrade(String message) throws JsonProcessingException {
//        log.info("Received kafka message: {}", message);
//        Trade trade = objectMapper.readValue(message, Trade.class);
//        System.out.println("Processing trade from Kafka: " + trade);
//        tradeService.recordTrade(trade.getUser().getId(), trade.getTradeType(), trade.getQuantity(), trade.getStock().getId(), trade.getBuyPrice());
//    }
//}
