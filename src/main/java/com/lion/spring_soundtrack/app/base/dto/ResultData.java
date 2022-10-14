package com.lion.spring_soundtrack.app.base.dto;

import com.lion.spring_soundtrack.util.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultData<T> {
    private String resultCode;
    private String msg;
    private T data;

    public static <T> ResultData<T> of(String resultCode, String msg, T data) {
        return new ResultData<>(resultCode, msg, data);
    }

    public static <T> ResultData<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static <T> ResultData<T> successOf(T data) {
        return of("S-1", "성공", data);
    }

    public static <T> ResultData<T> failOf(T data) {
        return of("F-1", "실패", data);
    }

    public boolean isSuccess() {
        return resultCode.startsWith("S-1");
    }

    public boolean isFail() {
        return isSuccess() == false;
    }

    public String addMsgToUrl(String url) {
        if ( isFail() ) {
            return Util.url.modifyQueryParam(url, "errorMsg", getMsg());
        }

        return Util.url.modifyQueryParam(url, "msg", getMsg());
    }
}
