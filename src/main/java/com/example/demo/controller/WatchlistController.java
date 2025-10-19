package com.example.demo.controller;

import com.example.demo.dto.WatchlistRequest;
import com.example.demo.dto.WatchlistUpdateRequest;
import com.example.demo.model.Watchlist;
import com.example.demo.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    
    @Autowired
    private WatchlistService watchlistService;
    
    @GetMapping("/user/{userId}")
    public List<Watchlist> getUserWatchlist(@PathVariable Long userId) {
        return watchlistService.getUserWatchlist(userId);
    }
    
    @PostMapping
    public ResponseEntity<Watchlist> addToWatchlist(@RequestBody WatchlistRequest request) {
        try {
            Watchlist watchlist = watchlistService.addToWatchlist(request);
            return ResponseEntity.ok(watchlist);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Watchlist> updateWatchlist(
            @PathVariable Long id, 
            @RequestBody WatchlistUpdateRequest request) {
        try {
            Watchlist watchlist = watchlistService.updateWatchlist(id, request);
            return ResponseEntity.ok(watchlist);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromWatchlist(@PathVariable Long id) {
        watchlistService.removeFromWatchlist(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/reports/user/{userId}")
    public ResponseEntity<String> generatePerformanceReport(@PathVariable Long userId) {
        try {
            String report = watchlistService.generatePerformanceReport(userId);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating report: " + e.getMessage());
        }
    }
}