package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;

@Data
public class HfzUserTaskRecord {
    private Integer id;

    private Integer userId;

    private Integer taskId;

    private String dayTime;//当天日期

    private Date completeTime;

    private Integer delStatus;

    private Integer type;       //1必做2非必做

    private Integer degree;     //1视频2下载

    private Integer type1;

    private Integer type2;

    private Integer second;     //当日第几次完成任务

    private String userName;

    private String userPhone;

    private Integer number;//求和使用

}