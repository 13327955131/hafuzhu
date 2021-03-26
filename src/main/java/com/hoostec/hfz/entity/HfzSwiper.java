package com.hoostec.hfz.entity;

import lombok.Data;

import java.util.Date;
@Data
public class HfzSwiper {
    private Integer id;

    private String img;

    private  String url;

    private  String title;

    private  String content;

    private Integer delStatus;  //del_status

    private Integer type;


}


