package com.lion.spring_soundtrack.app.base.initData;

import com.lion.spring_soundtrack.app.member.entity.Member;
import com.lion.spring_soundtrack.app.member.service.MemberService;
import com.lion.spring_soundtrack.app.product.service.ProductService;
import com.lion.spring_soundtrack.app.song.entity.Song;
import com.lion.spring_soundtrack.app.song.service.SongService;

public interface InitDataBefore {
    default void before(MemberService memberService, SongService songService, ProductService productService) {
        Member member1 = memberService.join("user1", "1234", "user1@test.com");
        Member member2 = memberService.join("user2", "1234", "user2@test.com");

        Song song1 = songService.create(member1, "노래 1", "내용 1");
        Song song2 = songService.create(member1, "노래 2", "내용 2");
        Song song3 = songService.create(member2, "노래 3", "내용 3");
        Song song4 = songService.create(member2, "노래 4", "내용 4");
        Song song5 = songService.create(member1, "노래 5", "내용 5");
        Song song6 = songService.create(member1, "노래 6", "내용 6");
        Song song7 = songService.create(member2, "노래 7", "내용 7");
        Song song8 = songService.create(member2, "노래 8", "내용 8");

        productService.create(song1, "그리움", 1_900);
        productService.create(song3, "미련", 2_900);
        productService.create(song5, "슬픔", 3_900);
        productService.create(song7, "바다", 4_900);
        productService.create(song8, "안녕", 5_900);

        memberService.addCash(member1, 10_000, "충전__무통장입금");
        memberService.addCash(member1, 20_000, "충전__무통장입금");
        memberService.addCash(member1, -5_000, "출금__일반");
        memberService.addCash(member1, 1_000_000, "충전__무통장입금");

        memberService.addCash(member2, 2_000_000, "충전__무통장입금");
    }
}
