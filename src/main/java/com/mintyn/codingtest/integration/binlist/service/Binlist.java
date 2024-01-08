package com.mintyn.codingtest.integration.binlist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mintyn.codingtest.Configuration.exception.MintynException;
import com.mintyn.codingtest.integration.CardVerifier;
import com.mintyn.codingtest.integration.binlist.model.CardInfo;
import com.mintyn.codingtest.model.dto.CardMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Qualifier("binlist")
@Component @Slf4j @RequiredArgsConstructor
public class Binlist implements CardVerifier {
    private final ObjectMapper objectMapper;
    @Value("${binlistUrl}")
    private String binlistUrl;
    private final WebClient webClient = WebClient.create();

    @Override
    public CardMetadata verifyCard(String bin) {
        var fullUrl = "%s/%s".formatted(binlistUrl, bin);
        var stringCardInfo = webClient.get().uri(fullUrl).retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            var cardInfo = objectMapper.readValue(stringCardInfo, CardInfo.class);
            return cardInfo.toCardMetaData();
        } catch (Exception e) {
            throw new MintynException(HttpStatus.BAD_REQUEST, "Invalid BIN");
        }
    }
}
