package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzGoodsOrder {
    private Integer id;

    private Integer userId;

    private Integer goodsId;

    private Date exchangeTime;

    private Integer num;

    private Integer orderStatus;

    private String orderCode;

    private Integer delStatus;

    private String userName;

    private String remarks;

    private String orderNo;
    //用户实体类
    private HfzUserAddress hfzUserAddress;

    private String province;

    private String city;

    private String county;

    private String address;

    private String phone;

    private String name;

    private String userPhone;

    private String goodsName;

    @Override
    public String toString() {
        return "HfzGoodsOrder{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", exchangeTime=" + exchangeTime +
                ", num=" + num +
                ", orderStatus=" + orderStatus +
                ", orderCode='" + orderCode + '\'' +
                ", delStatus=" + delStatus +
                ", userName='" + userName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", hfzUserAddress=" + hfzUserAddress +


                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", goodsName='" + goodsName + '\'' +
                '}';
    }
}