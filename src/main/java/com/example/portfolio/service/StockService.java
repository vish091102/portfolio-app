package com.example.portfolio.service;

import com.example.portfolio.entity.Stock;
import com.example.portfolio.repo.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public String updateStockFromCSV(MultipartFile file) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
        String line;
        int count = 0;

        while((line = br.readLine()) != null) {
            String[] data = line.split(",");

            Long stockId = Long.parseLong(data[0].trim());
            String stockName = data[1].trim();
            double currentPrice = Double.parseDouble(data[2].trim());

            Optional<Stock> stockOptional = stockRepository.findById(stockId);
            if(stockOptional.isPresent()) {
                Stock stock = stockOptional.get();
                stock.setStockName(stockName);
                stock.setCurrPrice(currentPrice);
                stockRepository.save(stock);
                count++;
            }else {
                //add the logic to inset new stock if needed
            }
        }

        return count + " stocks successfully updated";
    }
}
