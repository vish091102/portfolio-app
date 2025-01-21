package com.example.portfolio.controller;

import com.example.portfolio.dto.StockDetailsResponse;
import com.example.portfolio.service.StockPriceUpdater;
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

    @Autowired
    private StockPriceUpdater stockPriceUpdater;

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

    @PostMapping("update-prices")
    public ResponseEntity<String> updateStockPrices() {
        try {
            stockPriceUpdater.updateStockPrices();
            return ResponseEntity.ok("Stock prices updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update stock prices: " + e.getMessage());
        }
    }
}
