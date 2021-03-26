package com.hoostec.hfz.entity;

import lombok.Data;

@Data
public class HfzUserAddress {
    private Integer id;

    private String province;

    private String city;

    private String county;

    private String address;

    private String phone;

    private String name;

    private Integer userId;


}