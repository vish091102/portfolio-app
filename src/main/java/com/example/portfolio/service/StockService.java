package com.example.portfolio.service;

import com.example.portfolio.dto.StockDetailsResponse;
import com.example.portfolio.entity.Stock;
import com.example.portfolio.repo.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public StockDetailsResponse getStockDetails(Long stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);

        if(stock.isEmpty()) {
            throw new RuntimeException("Stock not found.");
        }

        StockDetailsResponse response = new StockDetailsResponse();
        response.setStockId(stock.get().getId());
        response.setStockName(stock.get().getName());

        Map<String, Double> prices = new HashMap<>();
        prices.put("open", stock.get().getOpenPrice());
        prices.put("close", stock.get().getClosePrice());
        prices.put("high", stock.get().getHighPrice());
        prices.put("low", stock.get().getLowPrice());
        prices.put("settlementPrice", stock.get().getSettlementPrice());
        response.setPrices(prices);

        return response;
    }

    public void updateStocks() {
        String fileName = "stocks.csv";

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);

            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }

            processCsvFile(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the CSV file: " + e.getMessage(), e);
        }
    }

    private void processCsvFile(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            List<Stock> stocks = new ArrayList<>();
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                Stock stock = parseCsvRowToStock(line);
                stocks.add(stock);
            }

            stockRepository.saveAll(stocks);
        }
    }

    private Stock parseCsvRowToStock(String line) {
        String[] fields = line.split(",");

        // Validate the number of columns
        if (fields.length != 7) {
            throw new IllegalArgumentException("Invalid CSV format. Each row must have 7 columns.");
        }

        Stock stock = new Stock();
        stock.setId(Long.parseLong(fields[0].trim()));
        stock.setName(fields[1].trim());
        stock.setOpenPrice(Double.parseDouble(fields[2].trim()));
        stock.setClosePrice(Double.parseDouble(fields[3].trim()));
        stock.setHighPrice(Double.parseDouble(fields[4].trim()));
        stock.setLowPrice(Double.parseDouble(fields[5].trim()));
        stock.setSettlementPrice(Double.parseDouble(fields[6].trim()));

        return stock;
    }
}
