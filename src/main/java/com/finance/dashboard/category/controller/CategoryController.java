package com.finance.dashboard.category.controller;

import com.finance.dashboard.category.dto.CategoryRequestDto;
import com.finance.dashboard.category.dto.CategoryResponseDto;
import com.finance.dashboard.category.entity.Category;
import com.finance.dashboard.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponseDto createCategory(
            @RequestBody CategoryRequestDto request) {

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category saved =
                categoryService.createCategory(category);

        CategoryResponseDto response =
                new CategoryResponseDto();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setDescription(saved.getDescription());

        return response;
    }

    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {

        return categoryService.getAllCategories()
                .stream()
                .map(category -> {

                    CategoryResponseDto dto =
                            new CategoryResponseDto();

                    dto.setId(category.getId());
                    dto.setName(category.getName());
                    dto.setDescription(
                            category.getDescription());

                    return dto;

                }).collect(Collectors.toList());
    }

}