package com.mintyn.codingtest.service;

import com.mintyn.codingtest.Configuration.exception.MintynException;
import com.mintyn.codingtest.model.dao.UserDao;
import com.mintyn.codingtest.model.dto.AuthToken;
import com.mintyn.codingtest.model.dto.UserDto;
import com.mintyn.codingtest.Configuration.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthToken loginUser(UserDto userDto) {
        var user = userDao.findByUsername(userDto.username()).orElseThrow(() -> new MintynException(HttpStatus.UNAUTHORIZED, "Invalid credential"));
        if (passwordEncoder.matches(userDto.password(), user.getPassword())) {
            return jwtUtils.generateToken(user.getUsername());
        }
        throw new MintynException(HttpStatus.UNAUTHORIZED, "Invalid credential");
    }
}
