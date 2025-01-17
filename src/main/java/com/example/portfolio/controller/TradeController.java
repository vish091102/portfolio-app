package com.example.portfolio.controller;

import com.example.portfolio.dto.TradeRequest;
import com.example.portfolio.dto.TradeResponse;
//import com.example.portfolio.kafka.TradeProducer;
import com.example.portfolio.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

//    @Autowired
//    private TradeProducer tradeProducer;

    @PostMapping("/execute")
    public ResponseEntity<TradeResponse> executeTrade(@RequestBody TradeRequest tradeRequest) {
        try{
            TradeResponse response = tradeService.executeTrade(tradeRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new TradeResponse("FAILURE", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new TradeResponse("FAILURE", "An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PostMapping("/record")
    public ResponseEntity<String> recordTrade(@RequestBody Trade trade) throws JsonProcessingException {
        tradeProducer.sendTrade(trade);
        return ResponseEntity.ok("Trade sent to Kafka for processing.");
    }*/
}
