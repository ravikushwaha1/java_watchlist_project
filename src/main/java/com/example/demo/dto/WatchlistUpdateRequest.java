package com.example.demo.dto;

import java.math.BigDecimal;

public class WatchlistUpdateRequest {
    private BigDecimal targetPrice;
    private String notes;
    
    // Getters and Setters
    public BigDecimal getTargetPrice() { return targetPrice; }
    public void setTargetPrice(BigDecimal targetPrice) { this.targetPrice = targetPrice; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}