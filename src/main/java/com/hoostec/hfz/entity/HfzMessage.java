package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzMessage {
    private Integer id;

    private String title;

    private Integer userId;

    private Date createTime;

    private Integer readStatus;

    private String content;
    private String userName;



}