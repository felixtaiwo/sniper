package com.mintyn.codingtest.integration;

import com.mintyn.codingtest.model.dto.CardMetadata;
import com.mintyn.codingtest.model.enums.CardType;
import com.mintyn.codingtest.model.enums.Scheme;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("inMem") @Component
public class CardVerifierImpl implements CardVerifier {
    @Override
    public CardMetadata verifyCard(String bin) {
        return CardMetadata.builder()
                .type(CardType.DEBIT)
                .scheme(Scheme.VISA)
                .bank("Mintyn")
                .build();
    }
}
