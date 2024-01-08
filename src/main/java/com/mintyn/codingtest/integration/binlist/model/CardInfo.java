package com.mintyn.codingtest.integration.binlist.model;

import com.mintyn.codingtest.model.dto.CardMetadata;
import com.mintyn.codingtest.model.enums.CardType;
import com.mintyn.codingtest.model.enums.Scheme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data @NoArgsConstructor
@AllArgsConstructor
public class CardInfo {
    private String scheme;
    private String type;
    private BankInfo bank;

    public CardMetadata toCardMetaData() {
        return CardMetadata.builder()
                .bank(bank.name)
                .scheme(getCardScheme())
                .type(getCardType())
                .build();
    }

    private CardType getCardType() {
        return Arrays.stream(CardType.values()).filter(s->s.name().equalsIgnoreCase(this.type)).findFirst().get();
    }

    private Scheme getCardScheme() {
        return Arrays.stream(Scheme.values()).filter(s->s.name().equalsIgnoreCase(this.scheme)).findFirst().get();
    }

    @Data @NoArgsConstructor
    @AllArgsConstructor
    public static class BankInfo {
        private String name;
    }
}

