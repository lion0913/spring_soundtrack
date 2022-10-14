package com.lion.spring_soundtrack.app.rebate.controller;


import com.lion.spring_soundtrack.app.base.dto.ResultData;
import com.lion.spring_soundtrack.app.rebate.entity.RebateOrderItem;
import com.lion.spring_soundtrack.app.rebate.service.RebateService;
import com.lion.spring_soundtrack.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public String makeData(String yearMonth) {
        rebateService.makeDate(yearMonth);

        return "redirect:/admin/rebate/rebateOrderItemList?yearMonth=" + yearMonth + "&msg=" + Util.url.encode("정산데이터가 성공적으로 생성되었습니다.");
    }

    @GetMapping("/rebateOrderItemList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showRebateOrderItemList(String yearMonth, Model model) {
        if (yearMonth == null) {
            yearMonth = "2022-10";
        }
        List<RebateOrderItem> items = rebateService.findRebateOrderItemsByPayDateIn(yearMonth);

        model.addAttribute("items", items);

        return "admin/rebate/rebateOrderItemList";
    }

    @PostMapping("/rebateOne/{orderItemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public String rebateOne(@PathVariable long orderItemId) {
        ResultData rebateRsData = rebateService.rebate(orderItemId);

        return rebateRsData.getMsg();
    }
}
