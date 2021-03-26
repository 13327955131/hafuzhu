package com.hoostec.hfz.entity;

import lombok.Data;

@Data
public class HfzGoodsType {
    private Integer id;

    private String name;

    private Integer parentId;

    private Integer delStatus;

    private String nickName;

}