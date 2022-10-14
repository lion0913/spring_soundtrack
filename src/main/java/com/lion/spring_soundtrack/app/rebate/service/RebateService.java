package com.lion.spring_soundtrack.app.rebate.service;


import com.lion.spring_soundtrack.app.base.dto.ResultData;
import com.lion.spring_soundtrack.app.cash.entity.CashLog;
import com.lion.spring_soundtrack.app.member.service.MemberService;
import com.lion.spring_soundtrack.app.order.entity.OrderItem;
import com.lion.spring_soundtrack.app.order.service.OrderService;
import com.lion.spring_soundtrack.app.rebate.entity.RebateOrderItem;
import com.lion.spring_soundtrack.app.rebate.repository.RebateOrderItemRepository;
import com.lion.spring_soundtrack.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RebateService {
    private final OrderService orderService;
    private final RebateOrderItemRepository rebateOrderItemRepository;
    private final MemberService memberService;

    @Transactional
    public void makeDate(String yearMonth) {
        int monthEndDay = Util.date.getEndDayOf(yearMonth);

        String fromDateStr = yearMonth + "-01 00:00:00.000000";
        String toDateStr = yearMonth + "-%02d 23:59:59.999999".formatted(monthEndDay);
        LocalDateTime fromDate = Util.date.parse(fromDateStr);
        LocalDateTime toDate = Util.date.parse(toDateStr);

        // 데이터 가져오기
        List<OrderItem> orderItems = orderService.findAllByPayDateBetweenOrderByIdAsc(fromDate, toDate);

        // 변환하기
        List<RebateOrderItem> rebateOrderItems = orderItems
                .stream()
                .map(this::toRebateOrderItem)
                .collect(Collectors.toList());

        // 저장하기
        rebateOrderItems.forEach(this::makeRebateOrderItem);
    }

    @Transactional
    public void makeRebateOrderItem(RebateOrderItem item) {
        RebateOrderItem oldRebateOrderItem = rebateOrderItemRepository.findByOrderItemId(item.getOrderItem().getId()).orElse(null);

        if (oldRebateOrderItem != null) {
            rebateOrderItemRepository.delete(oldRebateOrderItem);
        }

        rebateOrderItemRepository.save(item);
    }

    public RebateOrderItem toRebateOrderItem(OrderItem orderItem) {
        return new RebateOrderItem(orderItem);
    }

    public List<RebateOrderItem> findRebateOrderItemsByPayDateIn(String yearMonth) {
        int monthEndDay = Util.date.getEndDayOf(yearMonth);

        String fromDateStr = yearMonth + "-01 00:00:00.000000";
        String toDateStr = yearMonth + "-%02d 23:59:59.999999".formatted(monthEndDay);
        LocalDateTime fromDate = Util.date.parse(fromDateStr);
        LocalDateTime toDate = Util.date.parse(toDateStr);

        return rebateOrderItemRepository.findAllByPayDateBetweenOrderByIdAsc(fromDate, toDate);
    }

    @Transactional
    public ResultData rebate(long orderItemId) {
        RebateOrderItem rebateOrderItem = rebateOrderItemRepository.findByOrderItemId(orderItemId).get();

        if (rebateOrderItem.isRebateAvailable() == false) {
            return ResultData.of("F-1", "정산을 할 수 없는 상태입니다.");
        }

        int calculateRebatePrice = rebateOrderItem.calculateRebatePrice();

        ResultData<Map<String, Object>> addCashRsData = memberService.addCash(rebateOrderItem.getProduct().getAuthor(), calculateRebatePrice, "정산__%d__지급__예치금".formatted(rebateOrderItem.getOrderItem().getId()));
        CashLog cashLog = (CashLog) addCashRsData.getData().get("cashLog");

        rebateOrderItem.setRebateDone(cashLog.getId());

        return ResultData.of(
                "S-1",
                "정산성공",
                Util.mapOf(
                        "cashLogId", cashLog.getId()
                )
        );
    }
}
