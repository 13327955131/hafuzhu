package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzProfitPickRecord {
    private Integer id;

    private Integer userId;

    private String amount;

    private Date applyTime;

    private Integer verifyStatus;

    private Integer payStatus;

    private Date operateTime;

    private String msg;

    private Integer delStatus;

    private String userName;

    private String userPhone;

    private String results;//审核结果备注信息


}