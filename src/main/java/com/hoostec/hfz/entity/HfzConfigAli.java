package com.hoostec.hfz.entity;

import lombok.Data;

@Data
public class HfzConfigAli {
    private Integer id;

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

    private String domain;

    private String appcode;


}