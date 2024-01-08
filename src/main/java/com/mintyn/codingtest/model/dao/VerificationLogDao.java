package com.mintyn.codingtest.model.dao;

import com.mintyn.codingtest.model.entity.VerificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface VerificationLogDao extends JpaRepository<VerificationLog, Long> {
    @Query("SELECT v.bin, COUNT(v) FROM VerificationLog v GROUP BY v.bin ORDER BY COUNT(v) ASC, v.bin")
    List<Object[]> logStat();

    default Map<String, Long> logStat(int start, int limit) {
        var result = logStat();
        limit = Math.min(result.size(), limit);

        Map<String, Long> stat = new LinkedHashMap<>();
        for (int i = start - 1; i <= limit - 1; i++) {
            var resultTuple = result.get(i);
            stat.put((String) resultTuple[0], (Long) resultTuple[1]);
        }

        return stat;

    }
}
