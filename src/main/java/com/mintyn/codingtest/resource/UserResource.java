package com.mintyn.codingtest.resource;

import com.mintyn.codingtest.model.dto.UserDto;
import com.mintyn.codingtest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j @RequestMapping("user")
@Validated @RequiredArgsConstructor
public class UserResource {

    private final UserService userService;
    @PostMapping
    public void registerUser (@RequestBody @Valid UserDto userDto) {
        log.trace("registering user with username {}", userDto.username());
        userService.registerUser(userDto);
    }

}
