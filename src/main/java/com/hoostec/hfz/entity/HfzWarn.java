package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzWarn {
    private Integer id;

    private Integer handleStatus;

    private String createTime;

    private String msg;

    private String userId;

    private String userName;


}