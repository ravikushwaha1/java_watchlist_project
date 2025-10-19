package com.example.demo.service;

import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class StockService {
    
    @Autowired
    private StockRepository stockRepository;
    
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }
    
    public Optional<Stock> getStockById(Long id) {
        return stockRepository.findById(id);
    }
    
    public Stock createStock(Stock stock) {
        if (stockRepository.existsBySymbol(stock.getSymbol())) {
            throw new RuntimeException("Stock symbol already exists: " + stock.getSymbol());
        }
        return stockRepository.save(stock);
    }
    
    public void updateStockPrices() {
        List<Stock> stocks = stockRepository.findAll();
        Random random = new Random();
        
        for (Stock stock : stocks) {
            // Simulate price change between -5% to +5%
            BigDecimal currentPrice = stock.getCurrentPrice();
            if (currentPrice != null) {
                double changePercent = (random.nextDouble() * 10) - 5;
                BigDecimal newPrice = currentPrice.multiply(
                    BigDecimal.valueOf(1 + changePercent / 100)
                ).setScale(2, RoundingMode.HALF_UP);
                stock.setCurrentPrice(newPrice);
            }
        }
        stockRepository.saveAll(stocks);
    }
    
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}