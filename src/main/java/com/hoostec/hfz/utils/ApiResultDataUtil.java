package com.hoostec.hfz.utils;

import lombok.Data;

/**
 * Api 返回 数据 实体
 */
@Data
public class ApiResultDataUtil {

    private int code;
    private String msg;
    private Object data;

    public ApiResultDataUtil(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
