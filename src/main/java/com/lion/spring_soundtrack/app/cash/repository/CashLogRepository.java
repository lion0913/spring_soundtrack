package com.lion.spring_soundtrack.app.cash.repository;

import com.lion.spring_soundtrack.app.cash.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog, Long> {
}
