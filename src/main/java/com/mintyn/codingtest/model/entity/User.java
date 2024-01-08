package com.mintyn.codingtest.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "m_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @PrePersist
    public void initTime() {
        createDate = LocalDateTime.now();
    }


}
