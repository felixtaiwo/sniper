package com.mintyn.codingtest.model.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class VerificationStat extends ApiResponse <Map<String, Long>> {
    private final int start;
    private final int limit;
    private final long size;


    public VerificationStat(boolean success, Map<String, Long> payload, int start, int limit, long size) {
        super(success, payload);
        this.start = start;
        this.limit = limit;
        this.size = size;
    }
}
