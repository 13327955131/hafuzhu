package com.hoostec.hfz.entity;

import lombok.Data;

@Data
public class HfzConfigWx {
    private Integer id;

    private String appId;

    private String appSecret;

    private String mchId;

    private String mchSecret;

    private String templateId;

    private String qq;

    private String spbillCreateIp;
}