//package com.example.portfolio.kafka;
//
//import com.example.portfolio.entity.Trade;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TradeProducer {
//
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public void sendTrade(Trade trade) throws JsonProcessingException {
//        kafkaTemplate.send("trade-topic", objectMapper.writeValueAsString(trade));
//        System.out.println("Trade sent to Kafka: " + trade);
//    }
//}
