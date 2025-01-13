package com.example.portfolio.controller;

import com.example.portfolio.entity.Stock;
import com.example.portfolio.repo.StockRepository;
import com.example.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/stocks/api")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @GetMapping("/{stockId}")
    public ResponseEntity<?> getStockDetails(@PathVariable Long stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);
        return stock.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateStocks(@RequestParam("file")MultipartFile file) {
        try {
            String response = stockService.updateStockFromCSV(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update stocks: " + e.getMessage());
        }
    }
}
