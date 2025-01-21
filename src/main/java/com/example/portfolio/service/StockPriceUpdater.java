package com.example.portfolio.service;

import com.example.portfolio.entity.Stock;
import com.example.portfolio.repo.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StockPriceUpdater {
    @Autowired
    private StockRepository stockRepository;

    private static final String FILE_URL = "https://drive.google.com/uc?export=download&id=1UC8Wx-N8MZI1AdfdmVdXET3ZHcnkvSiT";

    public void updateStockPrices() {
        try {
            File csvFile = downloadCsvFile(FILE_URL);

            List<Stock> updatedStocks = processCsvFile(csvFile);

            stockRepository.saveAll(updatedStocks);

            log.info("Stock price updated successfully.");
        } catch (Exception e) {
            System.err.println("Failed to update stock prices: " + e.getMessage());
        }
    }

    private File downloadCsvFile(String fileUrl) throws IOException {
        File tempFile = File.createTempFile("stock_price", ".csv");
        try (InputStream in = new URL(fileUrl).openStream()) {
            Files.copy(in, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return tempFile;
    }

    private List<Stock> processCsvFile(File csvFile) throws IOException {
        List<Stock> updatedStocks = new ArrayList<>();
        try (Reader reader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record: csvParser) {
                Long stockId = Long.parseLong(record.get("id"));
                Optional<Stock> stockOpt = stockRepository.findById(stockId);

                if(stockOpt.isPresent()) {
                    Stock stock = stockOpt.get();
                    stock.setOpenPrice(Double.parseDouble(record.get("open_price")));
                    stock.setClosePrice(Double.parseDouble(record.get("close_price")));
                    stock.setHighPrice(Double.parseDouble(record.get("high_price")));
                    stock.setLowPrice(Double.parseDouble(record.get("low_price")));
                    stock.setSettlementPrice(Double.parseDouble(record.get("settlement_price")));
                    updatedStocks.add(stock);
                }
            }
        }

        return updatedStocks;
    }
}
