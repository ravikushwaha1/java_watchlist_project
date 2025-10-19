package com.example.demo.dto;

import java.math.BigDecimal;

public class WatchlistRequest {
    private Long userId;
    private Long stockId;
    private BigDecimal targetPrice;
    private String notes;
    
    // Default constructor
    public WatchlistRequest() {}
    
    // Parameterized constructor
    public WatchlistRequest(Long userId, Long stockId, BigDecimal targetPrice, String notes) {
        this.userId = userId;
        this.stockId = stockId;
        this.targetPrice = targetPrice;
        this.notes = notes;
    }
    
    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getStockId() { return stockId; }
    public void setStockId(Long stockId) { this.stockId = stockId; }
    
    public BigDecimal getTargetPrice() { return targetPrice; }
    public void setTargetPrice(BigDecimal targetPrice) { this.targetPrice = targetPrice; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}