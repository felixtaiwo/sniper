package com.mintyn.codingtest.service;

import com.mintyn.codingtest.integration.CardVerifier;
import com.mintyn.codingtest.model.dao.VerificationLogDao;
import com.mintyn.codingtest.model.dto.ApiResponse;
import com.mintyn.codingtest.model.dto.CardMetadata;
import com.mintyn.codingtest.model.dto.VerificationStat;
import com.mintyn.codingtest.model.entity.VerificationLog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CardService {
    @Value("${verifier}")
    private String verifier;
    private final CardVerifier binlist;
    private final CardVerifier inMem;
    private final VerificationLogDao verificationLogDao;

    public CardService(@Qualifier("binlist") CardVerifier binlist, @Qualifier("inMem") CardVerifier inMem, VerificationLogDao verificationLogDao) {
        this.binlist = binlist;
        this.inMem = inMem;
        this.verificationLogDao = verificationLogDao;
    }

    public ApiResponse<CardMetadata> verifyCard (String bin){
        logVerificationRequest(bin);
        var cardMetadata = getVerifier().verifyCard(bin);
        return ApiResponse.<CardMetadata>builder()
                .success(true)
                .payload(CardMetadata.builder()
                        .type(cardMetadata.getType())
                        .bank(cardMetadata.getBank())
                        .scheme(cardMetadata.getScheme())
                        .build())
                .build();
    }

    public void logVerificationRequest (String bin){
        var log = new VerificationLog(bin);
        verificationLogDao.save(log);
    }

    public VerificationStat retrieveVerificationStat(int start, int limit) {
        Map<String, Long> stat = verificationLogDao.logStat(start, limit);
        return new VerificationStat(true, stat, start, limit, verificationLogDao.count());
    }

    public CardVerifier getVerifier() {
        switch (verifier){
            case "binlist" -> {
                return binlist;
            }
            default -> {
                return inMem;
            }
        }
    }
}
