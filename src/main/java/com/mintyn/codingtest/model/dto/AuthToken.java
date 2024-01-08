package com.mintyn.codingtest.model.dto;

public record AuthToken (
        String token,
        long expiry
) {
}
