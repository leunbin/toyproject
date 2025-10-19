package com.example.HitLoggerTracker.repository;

import com.example.HitLoggerTracker.entity.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    List<Hit> findTop100ByOrderByAtDesc();
}
