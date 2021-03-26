package com.hoostec.hfz.service;

import com.hoostec.hfz.dao.HfzProfitCardMapper;
import com.hoostec.hfz.entity.HfzProfitCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

;

@Service
public class HfzProfitCardService {
    @Autowired
    private HfzProfitCardMapper hfzProfitCard;


    /**
     * 修改
     *
     * @return
     **/
    public int update(HfzProfitCard obj) {
        int ret = hfzProfitCard.update(obj);
        return ret;
    }

    /**
     * 查询单个
     *
     * @return
     **/
    public HfzProfitCard selectOne() {
        return hfzProfitCard.selectOne();
    }

}