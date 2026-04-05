package com.finance.dashboard.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(
            message = "Username is required")
    private String username;

    @Email(
            message = "Invalid email")
    @NotBlank(
            message = "Email required")
    private String email;

    @NotBlank(
            message = "Password required")
    private String password;

}