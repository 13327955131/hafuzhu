package com.hoostec.hfz.entity;

import lombok.Data;

@Data
public class HfzUserCard {
    private Integer id;

    private Integer userId;

    private String cardCode;

    private Integer cardNum;

    private Integer cardLevel;

    private Integer delStatus;

    private String userName;

    private String cardName;

    private String userPhone;


}