package com.example.portfolio.scheduler;

import com.example.portfolio.service.StockPriceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private StockPriceUpdater stockPriceUpdater;

    @Scheduled(cron = "0 0 1 * * ?")            //run daily at 1AM
    public void updateStockPricesDaily() {
        stockPriceUpdater.updateStockPrices();
    }
}
