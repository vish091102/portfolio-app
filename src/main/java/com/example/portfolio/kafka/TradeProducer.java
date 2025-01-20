package com.example.portfolio.kafka;

import com.example.portfolio.dto.TradeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TradeProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void publishTrade(TradeRequest tradeRequest) {
        try {
            String message = objectMapper.writeValueAsString(tradeRequest);
            kafkaTemplate.send("trade_topic", message);
            System.out.println("Trade message published to Kafka: " + message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish trade to Kafka: " + e.getMessage());
        }
    }
}
