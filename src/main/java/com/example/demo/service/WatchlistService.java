package com.example.demo.service;

import com.example.demo.dto.WatchlistRequest;
import com.example.demo.dto.WatchlistUpdateRequest;
import com.example.demo.model.User;
import com.example.demo.model.Stock;
import com.example.demo.model.Watchlist;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class WatchlistService {
    
    @Autowired
    private WatchlistRepository watchlistRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private StockRepository stockRepository;
    
    public List<Watchlist> getUserWatchlist(Long userId) {
        return watchlistRepository.findByUserUserId(userId);
    }
    
    public Watchlist addToWatchlist(WatchlistRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Stock stock = stockRepository.findById(request.getStockId())
            .orElseThrow(() -> new RuntimeException("Stock not found"));
        
        if (watchlistRepository.existsByUserUserIdAndStockStockId(
            request.getUserId(), request.getStockId())) {
            throw new RuntimeException("Stock already in watchlist");
        }
        
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setStock(stock);
        watchlist.setTargetPrice(request.getTargetPrice());
        watchlist.setNotes(request.getNotes());
        
        return watchlistRepository.save(watchlist);
    }
    
    public Watchlist updateWatchlist(Long watchlistId, WatchlistUpdateRequest request) {
        Watchlist watchlist = watchlistRepository.findById(watchlistId)
            .orElseThrow(() -> new RuntimeException("Watchlist item not found"));
        
        watchlist.setTargetPrice(request.getTargetPrice());
        watchlist.setNotes(request.getNotes());
        
        return watchlistRepository.save(watchlist);
    }
    
    public void removeFromWatchlist(Long watchlistId) {
        watchlistRepository.deleteById(watchlistId);
    }
    
    public String generatePerformanceReport(Long userId) {
        List<Watchlist> watchlist = getUserWatchlist(userId);
        StringBuilder report = new StringBuilder();
        
        report.append("Stock Watchlist Performance Report\n");
        report.append("==================================\n");
        
        for (Watchlist item : watchlist) {
            Stock stock = item.getStock();
            BigDecimal currentPrice = stock.getCurrentPrice();
            BigDecimal targetPrice = item.getTargetPrice();
            
            report.append(String.format("Stock: %s (%s)\n", stock.getName(), stock.getSymbol()));
            report.append(String.format("Current Price: $%.2f\n", currentPrice));
            
            if (targetPrice != null) {
                String status = currentPrice.compareTo(targetPrice) >= 0 ? 
                    "Target REACHED" : "Below Target";
                report.append(String.format("Target Price: $%.2f - %s\n", targetPrice, status));
            }
            
            if (item.getNotes() != null && !item.getNotes().isEmpty()) {
                report.append(String.format("Notes: %s\n", item.getNotes()));
            }
            report.append("---\n");
        }
        
        return report.toString();
    }
}