package com.finance.dashboard.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {

    @NotBlank(
            message = "Category name required")
    private String name;

    private String description;   // ← ADD THIS
}