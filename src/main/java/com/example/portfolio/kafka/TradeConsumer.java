package com.example.portfolio.kafka;

import com.example.portfolio.dto.TradeRequest;
import com.example.portfolio.service.TradeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TradeConsumer {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "trade_topic", groupId = "trade-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeTrade(String message, Acknowledgment acknowledgment) {
        try {
            log.info("Received kafka message: {}", message);
            TradeRequest tradeRequest = objectMapper.readValue(message, TradeRequest.class);
            tradeService.recordTrade(tradeRequest);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            System.err.println("Failed to process trade message: " + e.getMessage());
        }
    }
}
