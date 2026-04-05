package com.finance.dashboard.record.controller;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

import com.finance.dashboard.category.entity.Category;
import com.finance.dashboard.category.repository.CategoryRepository;

import com.finance.dashboard.common.enums.RecordType;
import com.finance.dashboard.common.exception.ResourceNotFoundException;

import com.finance.dashboard.record.dto.FinancialRecordRequestDto;
import com.finance.dashboard.record.dto.FinancialRecordResponseDto;

import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.record.service.FinancialRecordService;

import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.repository.UserRepository;

import com.finance.dashboard.record.dto.FinancialSummaryResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/records")
public class FinancialRecordController {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FinancialRecordService recordService;

    @PostMapping
    public FinancialRecordResponseDto createRecord(
            @Valid
            @RequestBody FinancialRecordRequestDto request) {

        User user =
                userRepository.findById(
                                request.getUserId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        Category category =
                categoryRepository.findById(
                                request.getCategoryId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"));

        FinancialRecord record =
                new FinancialRecord();

        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setDescription(request.getDescription());
        record.setRecordDate(request.getRecordDate());

        record.setUser(user);
        record.setCategory(category);

        FinancialRecord saved =
                recordService.createRecord(record);

        FinancialRecordResponseDto response =
                new FinancialRecordResponseDto();

        response.setId(saved.getId());
        response.setAmount(saved.getAmount());
        response.setType(saved.getType());
        response.setDescription(saved.getDescription());
        response.setRecordDate(saved.getRecordDate());

        return response;
    }

    @GetMapping
    public Page<FinancialRecordResponseDto>
    getAllRecords(Pageable pageable) {

        Page<FinancialRecord> records =
                recordService
                        .getAllRecordsPaginated(pageable);

        return records.map(record -> {

            FinancialRecordResponseDto dto =
                    new FinancialRecordResponseDto();

            dto.setId(record.getId());
            dto.setAmount(record.getAmount());
            dto.setType(record.getType());
            dto.setDescription(
                    record.getDescription());
            dto.setRecordDate(
                    record.getRecordDate());

            return dto;
        });
    }

    @GetMapping("/user/{userId}")
    public List<FinancialRecordResponseDto>
    getRecordsByUser(
            @PathVariable UUID userId) {

        List<FinancialRecord> records =
                recordService.getRecordsByUser(userId);

        return records.stream()
                .map(record -> {

                    FinancialRecordResponseDto dto =
                            new FinancialRecordResponseDto();

                    dto.setId(record.getId());
                    dto.setAmount(record.getAmount());
                    dto.setType(record.getType());
                    dto.setDescription(record.getDescription());
                    dto.setRecordDate(record.getRecordDate());

                    return dto;

                })
                .toList();
    }

    @GetMapping("/user/{userId}/type/{type}")
    public List<FinancialRecordResponseDto>
    getRecordsByUserAndType(

            @PathVariable UUID userId,
            @PathVariable RecordType type) {

        List<FinancialRecord> records =
                recordService.getRecordsByUserAndType(
                        userId,
                        type);

        return records.stream()
                .map(record -> {

                    FinancialRecordResponseDto dto =
                            new FinancialRecordResponseDto();

                    dto.setId(record.getId());
                    dto.setAmount(record.getAmount());
                    dto.setType(record.getType());
                    dto.setDescription(record.getDescription());
                    dto.setRecordDate(record.getRecordDate());

                    return dto;

                })
                .toList();
    }

    @GetMapping("/date-range")
    public List<FinancialRecordResponseDto>
    getRecordsBetweenDates(

            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        List<FinancialRecord> records =
                recordService.getRecordsBetweenDates(
                        start,
                        end);

        return records.stream()
                .map(record -> {

                    FinancialRecordResponseDto dto =
                            new FinancialRecordResponseDto();

                    dto.setId(record.getId());
                    dto.setAmount(record.getAmount());
                    dto.setType(record.getType());
                    dto.setDescription(record.getDescription());
                    dto.setRecordDate(record.getRecordDate());

                    return dto;

                })
                .toList();
    }

    @GetMapping("/summary/{userId}")
    public FinancialSummaryResponseDto
    getFinancialSummary(
            @PathVariable UUID userId) {

        return recordService
                .getFinancialSummary(userId);
    }

    @PutMapping("/{recordId}")
    public FinancialRecordResponseDto updateRecord(
            @PathVariable UUID recordId,
            @Valid
            @RequestBody FinancialRecordRequestDto request) {

        FinancialRecord updated =
                recordService.updateRecord(
                        recordId,
                        request);

        FinancialRecordResponseDto response =
                new FinancialRecordResponseDto();

        response.setId(updated.getId());
        response.setAmount(updated.getAmount());
        response.setType(updated.getType());
        response.setDescription(
                updated.getDescription());
        response.setRecordDate(
                updated.getRecordDate());

        return response;
    }

    @DeleteMapping("/{recordId}")
    public void deleteRecord(
            @PathVariable UUID recordId) {

        recordService.deleteRecord(recordId);
    }

}