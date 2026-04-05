package com.finance.dashboard.record.service;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.record.repository.FinancialRecordRepository;

import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.repository.UserRepository;

import com.finance.dashboard.common.enums.RecordType;
import com.finance.dashboard.common.exception.ResourceNotFoundException;

import com.finance.dashboard.record.dto.FinancialSummaryResponseDto;
import com.finance.dashboard.category.repository.CategoryRepository;
import com.finance.dashboard.record.dto.FinancialRecordRequestDto;
import com.finance.dashboard.category.entity.Category;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public FinancialRecord createRecord(FinancialRecord record) {

        return recordRepository.save(record);
    }

    public List<FinancialRecord> getAllRecords() {

        return recordRepository.findAll();
    }

    public List<FinancialRecord> getRecordsByUser(UUID userId) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        return recordRepository.findByUser(user);
    }

    public List<FinancialRecord> getRecordsByUserAndType(
            UUID userId,
            RecordType type) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        return recordRepository
                .findByUserAndType(user, type);
    }

    public List<FinancialRecord> getRecordsBetweenDates(
            LocalDate start,
            LocalDate end) {

        return recordRepository
                .findByRecordDateBetween(
                        start,
                        end);
    }

    public void deleteRecord(UUID recordId) {

        FinancialRecord record =
                recordRepository.findById(recordId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Record not found"));

        record.setIsDeleted(true);
        recordRepository.delete(record);
    }

    public FinancialSummaryResponseDto
    getFinancialSummary(UUID userId) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        List<FinancialRecord> records =
                recordRepository.findByUser(user);

        double totalIncome =
                records.stream()
                        .filter(record ->
                                record.getType()
                                        == RecordType.INCOME)
                        .mapToDouble(record -> record.getAmount().doubleValue())
                        .sum();

        double totalExpense =
                records.stream()
                        .filter(record ->
                                record.getType()
                                        == RecordType.EXPENSE)
                        .mapToDouble(record -> record.getAmount().doubleValue())
                        .sum();

        double balance =
                totalIncome - totalExpense;

        int totalRecords =
                records.size();

        return new FinancialSummaryResponseDto(
                totalIncome,
                totalExpense,
                balance,
                totalRecords);
    }

    public FinancialRecord updateRecord(
            UUID recordId,
            FinancialRecordRequestDto request) {

        FinancialRecord record =
                recordRepository.findById(recordId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Record not found"));

        Category category =
                categoryRepository.findById(
                                request.getCategoryId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"));

        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setDescription(
                request.getDescription());
        record.setRecordDate(
                request.getRecordDate());
        record.setCategory(category);

        return recordRepository.save(record);
    }

    public Page<FinancialRecord> getAllRecordsPaginated(
            Pageable pageable) {

        return recordRepository.findAll(pageable);
    }

}