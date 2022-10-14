package com.lion.spring_soundtrack.app.rebate.controller;


import com.lion.spring_soundtrack.app.base.dto.ResultData;
import com.lion.spring_soundtrack.app.rebate.entity.RebateOrderItem;
import com.lion.spring_soundtrack.app.rebate.service.RebateService;
import com.lion.spring_soundtrack.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/rebate")
@RequiredArgsConstructor
@Slf4j
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
        ResultData makDateResultData = rebateService.makeDate(yearMonth);
        String redirect = makDateResultData.addMsgToUrl("redirect:/admin/rebate/rebateOrderItemList?yearMonth=" + yearMonth);

        return redirect;
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
    public String rebateOne(@PathVariable long orderItemId, HttpServletRequest req) {
        ResultData rebateRsData = rebateService.rebate(orderItemId);

        String referer = req.getHeader("Referer");

        String yearMonth = Util.url.getQueryParamValue(referer, "yearMonth", "");

        String redirect = "redirect:/admin/rebate/rebateOrderItemList?yearMonth=" + yearMonth;

        redirect = rebateRsData.addMsgToUrl(redirect);

        return redirect;
    }

    @PostMapping("/rebate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String rebate(String ids, HttpServletRequest req) {

        String[] idsArr = ids.split(",");

        Arrays.stream(idsArr)
                .mapToLong(Long::parseLong)
                .forEach(id -> {
                    rebateService.rebate(id);
                });

        String referer = req.getHeader("Referer");
        String yearMonth = Util.url.getQueryParamValue(referer, "yearMonth", "");

        String redirect = "redirect:/admin/rebate/rebateOrderItemList?yearMonth=" + yearMonth;
        redirect += "&msg=" + Util.url.encode("%d건의 정산품목을 정산처리하였습니다.".formatted(idsArr.length));

        return redirect;
    }
}
