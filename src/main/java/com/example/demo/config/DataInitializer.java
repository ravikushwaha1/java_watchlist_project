package com.example.demo.config;

import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private StockRepository stockRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (stockRepository.count() == 0) {
            Stock stock1 = new Stock();
            stock1.setSymbol("AAPL");
            stock1.setName("Apple Inc.");
            stock1.setCurrentPrice(new BigDecimal("150.00"));
            stock1.setMarket("NASDAQ");
            
            Stock stock2 = new Stock();
            stock2.setSymbol("GOOGL");
            stock2.setName("Alphabet Inc.");
            stock2.setCurrentPrice(new BigDecimal("2800.00"));
            stock2.setMarket("NASDAQ");
            
            Stock stock3 = new Stock();
            stock3.setSymbol("MSFT");
            stock3.setName("Microsoft Corporation");
            stock3.setCurrentPrice(new BigDecimal("330.00"));
            stock3.setMarket("NASDAQ");

            Stock stock4 = new Stock();
            stock4.setSymbol("AMZN");
            stock4.setName("Amazon.com Inc.");
            stock4.setCurrentPrice(new BigDecimal("3500.00"));
            stock4.setMarket("NASDAQ");

            Stock stock5 = new Stock();
            stock5.setSymbol("TSLA");
            stock5.setName("Tesla Inc.");
            stock5.setCurrentPrice(new BigDecimal("700.00"));
            stock5.setMarket("NASDAQ");
            
            stockRepository.save(stock1);
            stockRepository.save(stock2);
            stockRepository.save(stock3);
            stockRepository.save(stock4);
            stockRepository.save(stock5);
            
            System.out.println("✅ Sample stocks added to database");
        }
    }
}