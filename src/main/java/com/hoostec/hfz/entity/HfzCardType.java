package com.hoostec.hfz.entity;

import lombok.Data;

@Data
public class HfzCardType {
    private Integer id;

    private String name;

    private String img;

    private String code;

    private String remark;

    private Integer delStatus;

    private Integer type;
}