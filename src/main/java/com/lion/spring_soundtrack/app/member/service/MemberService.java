package com.lion.spring_soundtrack.app.member.service;

import com.lion.spring_soundtrack.app.base.dto.ResultData;
import com.lion.spring_soundtrack.app.cash.entity.CashLog;
import com.lion.spring_soundtrack.app.cash.service.CashService;
import com.lion.spring_soundtrack.app.member.entity.Member;
import com.lion.spring_soundtrack.app.member.exception.AlreadyJoinException;
import com.lion.spring_soundtrack.app.member.repository.MemberRepository;
import com.lion.spring_soundtrack.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CashService cashService;

    public Member join(String username, String password, String email) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new AlreadyJoinException();
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        memberRepository.save(member);

        return member;
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public ResultData<Map<String, Object>> addCash(Member member, long price, String eventType) {
        CashLog cashLog = cashService.addCash(member, price, eventType);

        long newRestCash = member.getRestCash() + cashLog.getPrice();
        member.setRestCash(newRestCash);
        memberRepository.save(member);

        return ResultData.of(
                "S-1",
                "성공",
                Util.mapOf(
                        "cashLog", cashLog,
                        "newRestCash", newRestCash
                )
        );
    }

    public long getRestCash(Member member) {
//        return member.getRestCash();
        Member foundMember = findByUsername(member.getUsername()).get();
//
        return foundMember.getRestCash();
    }
}
