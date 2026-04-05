package com.finance.dashboard.record.dto;

import jakarta.validation.constraints.*;
import com.finance.dashboard.common.enums.RecordType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class FinancialRecordRequestDto {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID categoryId;

    @NotNull
    @DecimalMin(
            value = "0.01",
            message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull
    private RecordType type;

    private String description;

    @NotNull
    private LocalDate recordDate;
}