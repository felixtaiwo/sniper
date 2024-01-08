package com.mintyn.codingtest.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto (
        @NotNull @Size(min = 2)
        String username,
        @NotNull
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^\\w\\d\\s]).{8,}$",
                message = "Password must be at least 8 characters, with 1 uppercase, 1 lowercase, 1 number, and 1 special character.")
        String password
) {
}
