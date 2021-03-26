package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzProfitRecord {
    private Integer id;

    private Integer userId;

    private String amount;

    private String getTime;

    private Integer getWay;

    private Integer delStatus;

    private String userName;

    private String userPhone;
    private String applyTime;
    private String msg;
    private String type;
    private String status;


}