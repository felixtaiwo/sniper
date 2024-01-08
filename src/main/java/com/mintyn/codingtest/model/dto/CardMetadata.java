package com.mintyn.codingtest.model.dto;

import com.mintyn.codingtest.model.enums.CardType;
import com.mintyn.codingtest.model.enums.Scheme;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CardMetadata {
    private Scheme scheme;
    private CardType type;
    private String bank;
}
