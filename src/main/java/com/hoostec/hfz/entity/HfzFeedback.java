package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzFeedback {
    private Integer id;

    private String type;

    private Date createTime;

    private String contact;

    private Integer delStatus;

    private String content;


}