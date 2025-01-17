package com.example.portfolio.controller;

import com.example.portfolio.dto.StockDetailsResponse;
import com.example.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks/api")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/{stockId}")
    public ResponseEntity<StockDetailsResponse> getStockDetails(@PathVariable Long stockId) {
        StockDetailsResponse response = stockService.getStockDetails(stockId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
