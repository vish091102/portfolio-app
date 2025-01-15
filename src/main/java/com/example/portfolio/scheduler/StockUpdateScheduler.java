package com.example.portfolio.scheduler;

import com.example.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class StockUpdateScheduler {

    @Autowired
    private StockService stockService;

    private static final String STOCK_CSV_URL = "download_url_link";

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateStockPrices() {
        System.out.println("Starting stock price update...");
        try {
            BufferedReader reader = downloadCSVFile(STOCK_CSV_URL);

            if(reader != null) {
                System.out.println("Stock prices successfully updated.");
            } else {
                System.out.println("Failed to retrieve CSV File.");
            }
        } catch (Exception e) {
            System.err.println("Error during stock price update: " + e.getMessage());
        }
    }

    private BufferedReader downloadCSVFile(String stockFileUrl) throws Exception {

        URL url = new URL(stockFileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK) {
            return new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        }else {
            System.err.println("Failed to get CSV File. HTTP response code: " + responseCode);
            return null;
        }
    }
}
