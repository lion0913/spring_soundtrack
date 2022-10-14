package com.lion.spring_soundtrack.app.rebate.controller;


import com.lion.spring_soundtrack.app.rebate.service.RebateService;
import com.lion.spring_soundtrack.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/rebate")
@RequiredArgsConstructor
public class AdminRebateController {
    private final RebateService rebateService;

    @GetMapping("/makeData")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showMakeData() {
        return "admin/rebate/makeData";
    }

    @PostMapping("/makeData")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public String makeData(String yearMonth) {
        rebateService.makeDate(yearMonth);

        return "redirect:/admin/rebate/rebateOrderItemList?yearMonth=" + yearMonth + "&msg=" + Util.url.encode("정산데이터가 성공적으로 생성되었습니다.");
    }

    @GetMapping("/rebateOrderItemList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showRebateOrderItemList(String yearMonth) {
        if (yearMonth == null) {
            yearMonth = "2022-10";
        }

        return "admin/rebate/rebateOrderItemList";
    }
}
