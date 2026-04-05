package com.finance.dashboard.user.controller;

import com.finance.dashboard.user.dto.UserRequestDto;
import com.finance.dashboard.user.dto.UserResponseDto;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.service.UserService;
import com.finance.dashboard.common.enums.Role;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(
            @RequestBody UserRequestDto request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user.setRole(Role.VIEWER);


        User saved =
                userService.createUser(user);

        UserResponseDto response =
                new UserResponseDto();

        response.setId(saved.getId());
        response.setUsername(saved.getUsername());
        response.setEmail(saved.getEmail());

        return response;
    }

}