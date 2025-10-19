package com.example.demo.repository;

import com.example.demo.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUserUserId(Long userId);
    Optional<Watchlist> findByUserUserIdAndStockStockId(Long userId, Long stockId);
    boolean existsByUserUserIdAndStockStockId(Long userId, Long stockId);
}