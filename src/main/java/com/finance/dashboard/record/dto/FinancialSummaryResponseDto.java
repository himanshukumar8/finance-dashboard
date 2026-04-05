package com.finance.dashboard.record.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialSummaryResponseDto {

    private Double totalIncome;

    private Double totalExpense;

    private Double balance;

    private Integer totalRecords;

}