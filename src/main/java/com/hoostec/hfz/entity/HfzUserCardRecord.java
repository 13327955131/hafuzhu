package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzUserCardRecord {
    private Integer id;

    private Integer userId;

    private String cardId;

    private String cardName;

    private Integer delStatus;

    private String userName;

    private Date getTime;

    private Integer getWay;

    private Integer type;

    private String userPhone;
}