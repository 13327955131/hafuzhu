package com.hoostec.hfz.entity;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.Date;
@Data
public class HfzLuckDrawRecord {
    private Integer id;

    private Integer userId;

    private Date drawTime;

    private String drawResult;

    private Integer delStatus;

    private String userName;

    private String prizeName;

    private String userPhone;


}