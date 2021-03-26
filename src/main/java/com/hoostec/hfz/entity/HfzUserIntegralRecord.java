package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzUserIntegralRecord {
    private Integer id;

    private Integer userId;

    private Integer type;

    private Integer num;

    private String useTime;

    private String msg;

    private Integer delStatus;

    private String userName;

    private String userPhone;


}