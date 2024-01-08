package com.mintyn.codingtest.service;

import com.mintyn.codingtest.Configuration.exception.MintynException;
import com.mintyn.codingtest.model.dao.UserDao;
import com.mintyn.codingtest.model.dto.UserDto;
import com.mintyn.codingtest.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {
        if (userDao.existsByUsername(userDto.username())) {
            throw new MintynException(HttpStatus.CONFLICT, "username already exists");
        }
        var encodedPassword = passwordEncoder.encode(userDto.password());
        var user = new User(userDto.username(), encodedPassword);
        userDao.save(user);
    }
}
