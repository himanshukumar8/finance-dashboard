package com.finance.dashboard.record.dto;

import com.finance.dashboard.common.enums.RecordType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class FinancialRecordResponseDto {

    private UUID id;

    private BigDecimal amount;

    private RecordType type;

    private String description;

    private LocalDate recordDate;

}