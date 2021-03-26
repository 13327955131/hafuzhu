package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzVersion {
    private Integer id;

    private String version;

    private String url;

    private Date createTime;

    private Integer delStatus;

    private String content;


}