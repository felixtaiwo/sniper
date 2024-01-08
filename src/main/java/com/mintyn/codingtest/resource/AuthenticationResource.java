package com.mintyn.codingtest.resource;

import com.mintyn.codingtest.model.dto.AuthToken;
import com.mintyn.codingtest.model.dto.UserDto;
import com.mintyn.codingtest.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j @RequestMapping("auth")
@Validated @RequiredArgsConstructor
public class AuthenticationResource {

    private final AuthService authService;

    @PostMapping("login")
    public AuthToken login (@RequestBody @Valid UserDto userDto){
        log.trace("login request received from user {}", userDto.username());
        return authService.loginUser(userDto);
    }
}
