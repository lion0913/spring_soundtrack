package com.lion.spring_soundtrack.base.initData;

import com.lion.spring_soundtrack.base.member.entity.Member;
import com.lion.spring_soundtrack.base.member.service.MemberService;

public interface InitDataBefore {
    default void before(MemberService memberService) {
        Member member1 = memberService.join("user1", "1234", "user1@test.com");
        Member member2 = memberService.join("user2", "1234", "user2@test.com");
    }
}
