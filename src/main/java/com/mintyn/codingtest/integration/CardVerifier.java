package com.mintyn.codingtest.integration;

import com.mintyn.codingtest.model.dto.CardMetadata;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component @Validated
public interface CardVerifier {
    @Cacheable("card-info")
    CardMetadata verifyCard (@NotBlank String bin);
}
