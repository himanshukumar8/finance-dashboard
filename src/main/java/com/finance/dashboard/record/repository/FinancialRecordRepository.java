package com.finance.dashboard.record.repository;

import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.common.enums.RecordType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, UUID> {

    List<FinancialRecord> findByUser(User user);

    List<FinancialRecord> findByUserAndType(User user, RecordType type);

    List<FinancialRecord> findByRecordDateBetween(LocalDate start, LocalDate end);

    Page<FinancialRecord> findAll(Pageable pageable);

}