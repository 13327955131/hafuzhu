package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzPayDay {
    private Integer id;

    private String dayTime;

    private Integer amount;

    private Integer delStatus;

    private String startTime;

    private String endTime;

    private Date insertTime;
}