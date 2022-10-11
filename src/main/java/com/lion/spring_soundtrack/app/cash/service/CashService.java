package com.lion.spring_soundtrack.app.cash.service;

import com.lion.spring_soundtrack.app.cash.entity.CashLog;
import com.lion.spring_soundtrack.app.cash.repository.CashLogRepository;
import com.lion.spring_soundtrack.app.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashService {
    private final CashLogRepository cashLogRepository;

    public CashLog addCash(Member member, long price, String eventType) {
        CashLog cashLog = CashLog.builder()
                .member(member)
                .price(price)
                .eventType(eventType)
                .build();

        return cashLogRepository.save(cashLog);
    }
}
