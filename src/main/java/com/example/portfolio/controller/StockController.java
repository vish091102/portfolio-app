package com.example.portfolio.controller;

import com.example.portfolio.dto.StockDetailsResponse;
import com.example.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/update")
    public ResponseEntity<String> updateStocks() {
        try {
            stockService.updateStocks();
            return new ResponseEntity<>("Stocks updated successfully from resources.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
