package com.hoostec.hfz.entity;

import lombok.Data;

@Data
public class HfzSign {
    private Integer id;

    private Integer userId;

    private Integer continueSignMonth;

    private String signMonth;

    private String lastSignTime;

    private Integer integralReceive;    //integral_receive

    private Integer drawReceive;        //draw_receive

    private Integer redPacketReceive;//red_packet_receive

    private String userName;

    private String userPhone;

    private Integer delStatus;


}