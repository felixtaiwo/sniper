package com.mintyn.codingtest.model.dao;

import com.mintyn.codingtest.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsername (String username);
    boolean existsByUsername (String username);
}
