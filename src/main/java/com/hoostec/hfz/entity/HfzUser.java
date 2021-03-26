package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzUser {
    private Integer id;

    private String nickName;

    private String headImg;

    private String phone;

    private String realName;

    private Integer sex;

    private Integer level;

    private String address;

    private String wxId;

    private String invateCode;

    private Integer parentId;

    private String password;

    private String registerTime;

    private Integer delStatus;

    private String deviceToken;

    private String sfz;

    private Integer userStatus;

    private String lastLoginIp;//last_login_ip

    private String provincia;   //新增字段   省份

    private String name;//临时使用
    private Integer number; //临时使用   存做重复数

    private String region;//省份   临时使用


    public HfzUser(String phone) {
        this.phone = phone;
    }

    public HfzUser() {
    }

    public HfzUser(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public HfzUser(String phone, String password, String invateCode) {
        this.phone = phone;
        this.password = password;
        this.invateCode = invateCode;
    }
}