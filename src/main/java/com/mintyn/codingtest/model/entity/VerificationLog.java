package com.mintyn.codingtest.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Entity @Table(name = "m_verification_log")
public class VerificationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bin;
    private LocalDateTime createDate;

    public VerificationLog(String bin) {
        this.bin = bin;
    }

    @PrePersist
    public void initTime() {
        createDate = LocalDateTime.now();
    }


}
