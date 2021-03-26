package com.hoostec.hfz.entity;

import lombok.Data;

@Data
public class HfzUserBalance {
    private Integer id;

    private Integer integral;

    private Integer profit;

    private Integer userId;

    private Integer delStatus;

    private Integer drawNum;

    private String userPhone;

    private String userName;

    private Integer firstPickStatus;    //第一次提现

    private Integer realNameStatus;     //实名状态

    private Integer dayTaskStatus;      //必做任务完成状态

    private Integer wxBindStatus;       //微信绑定状态



    public  HfzUserBalance(){

    }
    public HfzUserBalance(int userId, int integral, int profit) {
        this.userId = userId;
        this.integral = integral;
        this.profit = profit;
    }
}