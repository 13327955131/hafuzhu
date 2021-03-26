package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzNotice {
    private Integer id;

    private String title;

    private Date createTime;

    private Integer delStatus;

    private String content;


}