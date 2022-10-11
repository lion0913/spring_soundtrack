package com.lion.spring_soundtrack.app.song.controller;


import com.lion.spring_soundtrack.app.member.entity.Member;
import com.lion.spring_soundtrack.app.security.dto.MemberContext;
import com.lion.spring_soundtrack.app.song.entity.Song;
import com.lion.spring_soundtrack.app.song.exception.ActorCanNotModifyException;
import com.lion.spring_soundtrack.app.song.exception.ActorCanNotSeeException;
import com.lion.spring_soundtrack.app.song.form.SongForm;
import com.lion.spring_soundtrack.app.song.service.SongService;
import com.lion.spring_soundtrack.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/song")
@Slf4j
public class SongController {
    private final SongService songService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String showCreate() {
        return "song/create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@AuthenticationPrincipal MemberContext memberContext, @Valid SongForm songForm) {
        Member author = memberContext.getMember();
        Song song = songService.create(author, songForm.getSubject(), songForm.getContent());
        return "redirect:/song/" + song.getId() + "?msg=" + Util.url.encode("%d번 음원이 생성되었습니다.".formatted(song.getId()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, Model model) {
        Song song = songService.findForPrintById(id).get();

        Member actor = memberContext.getMember();

        if (songService.actorCanModify(actor, song) == false) {
            throw new ActorCanNotModifyException();
        }

        model.addAttribute("song", song);

        return "song/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, @Valid SongForm songForm, @PathVariable long id) {
        Song song = songService.findById(id).get();
        Member actor = memberContext.getMember();

        if (songService.actorCanModify(actor, song) == false) {
            throw new ActorCanNotModifyException();
        }

        songService.modify(song, songForm.getSubject(), songForm.getContent());
        return "redirect:/song/" + song.getId() + "?msg=" + Util.url.encode("%d번 음원이 생성되었습니다.".formatted(song.getId()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String detail(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long id, Model model) {
        Song song = songService.findForPrintById(id).get();

        Member actor = memberContext.getMember();

        if (songService.actorCanModify(actor, song) == false) {
            throw new ActorCanNotSeeException();
        }

        model.addAttribute("song", song);

        return "song/detail";
    }
}
