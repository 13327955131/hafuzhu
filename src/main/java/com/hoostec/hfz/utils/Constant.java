package com.hoostec.hfz.utils;

public interface Constant {
    // 删除状态
    int DEL_STATUS_ONE = 1;

    // 成功
    int SUCCESS_CODE_200 = 200;

    // token不合法或者过期
    int ERROR_CODE_1001 = 1001;

    // 手机号或密码错误
    int ERROR_CODE_1002 = 1002;

    // 账号已注册
    int ERROR_CODE_1003 = 1003;

    // 手机号未注册
    int ERROR_CODE_1004 = 1004;

    // 短信

    // 短信发送失败
    int API_ERROR_CODE_1005 = 1005;
    // 短信验证码错误
    int API_ERROR_CODE_1006 = 1006;
    // 短信验证码超时
    int API_ERROR_CODE_1007 = 1007;

    String app_sign = "";

}
