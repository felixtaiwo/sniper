package com.mintyn.codingtest.resource;

import com.mintyn.codingtest.model.dto.ApiResponse;
import com.mintyn.codingtest.model.dto.CardMetadata;
import com.mintyn.codingtest.model.dto.VerificationStat;
import com.mintyn.codingtest.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequestMapping("card-scheme")
@Validated @RequiredArgsConstructor
public class CardResource {
    private final CardService cardService;

    @GetMapping("{bin}")
    public ApiResponse<CardMetadata> verifyCard (@PathVariable @NotNull @Valid @Size(min = 6, max = 8)
                                                String bin) {
        log.trace("verifying card with bin {}", bin);
        return cardService.verifyCard(bin);
    }

    @GetMapping("stat")
    public VerificationStat retrieveVerificationStat (@RequestParam @Positive @Valid int start,
                                                      @RequestParam @Positive @Valid int limit){
        log.trace("retrieving verification stat with start {} and limit {}", start, limit);
        return cardService.retrieveVerificationStat(start, limit);
    }

}
