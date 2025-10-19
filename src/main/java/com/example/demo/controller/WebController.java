package com.example.demo.controller;

import com.example.demo.dto.WatchlistRequest;
import com.example.demo.model.Stock;
import com.example.demo.model.User;
import com.example.demo.model.Watchlist;
import com.example.demo.service.StockService;
import com.example.demo.service.UserService;
import com.example.demo.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class WebController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private WatchlistService watchlistService;
    
    // Home page
    @GetMapping
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Stock Watchlist");
        return "index";
    }
    
    // Users management page
    @GetMapping("/users")
    public String usersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "users";
    }
    
    // Create use
    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/users";
    }
    
    // Stocks page
    @GetMapping("/stocks")
    public String stocksPage(Model model) {
        model.addAttribute("stocks", stockService.getAllStocks());
        return "stocks";
    }
    
    // Update stock prices
    @PostMapping("/stocks/update-prices")
    public String updateStockPrices() {
        stockService.updateStockPrices();
        return "redirect:/stocks";
    }
    
    // Watchlist page
    @GetMapping("/watchlist")
    public String watchlistPage(Model model) {
        List<User> users = userService.getAllUsers();
        List<Stock> stocks = stockService.getAllStocks();
        
        model.addAttribute("users", users);
        model.addAttribute("stocks", stocks);
        model.addAttribute("watchlistRequest", new WatchlistRequest());
        
        if (!users.isEmpty()) {
            Long firstUserId = users.get(0).getUserId();
            model.addAttribute("userWatchlist", watchlistService.getUserWatchlist(firstUserId));
            model.addAttribute("selectedUserId", firstUserId);
        }
        
        return "watchlist";
    }
    
    // Get user's watchlist
    @GetMapping("/watchlist/user/{userId}")
    public String getUserWatchlist(@PathVariable Long userId, Model model) {
        List<User> users = userService.getAllUsers();
        List<Stock> stocks = stockService.getAllStocks();
        
        model.addAttribute("users", users);
        model.addAttribute("stocks", stocks);
        model.addAttribute("watchlistRequest", new WatchlistRequest());
        model.addAttribute("userWatchlist", watchlistService.getUserWatchlist(userId));
        model.addAttribute("selectedUserId", userId);
        
        return "watchlist";
    }
    
    // Add to watchlist
    @PostMapping("/watchlist/add")
    public String addToWatchlist(@ModelAttribute WatchlistRequest request) {
        watchlistService.addToWatchlist(request);
        return "redirect:/watchlist/user/" + request.getUserId();
    }
    
    // Remove from watchlist
    @PostMapping("/watchlist/remove/{watchlistId}")
    public String removeFromWatchlist(@PathVariable Long watchlistId, @RequestParam Long userId) {
        watchlistService.removeFromWatchlist(watchlistId);
        return "redirect:/watchlist/user/" + userId;
    }
    
    // Reports page
    @GetMapping("/reports")
    public String reportsPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        
        if (!users.isEmpty()) {
            Long firstUserId = users.get(0).getUserId();
            model.addAttribute("selectedUserId", firstUserId);
            model.addAttribute("report", watchlistService.generatePerformanceReport(firstUserId));
        }
        
        return "reports";
    }
    
    // Generate report for specific user
    @GetMapping("/reports/user/{userId}")
    public String generateReport(@PathVariable Long userId, Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("selectedUserId", userId);
        model.addAttribute("report", watchlistService.generatePerformanceReport(userId));
        
        return "reports";
    }
}