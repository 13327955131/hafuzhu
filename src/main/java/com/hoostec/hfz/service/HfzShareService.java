package com.hoostec.hfz.service;

import com.hoostec.hfz.dao.HfzShareMapper;
import com.hoostec.hfz.entity.HfzShare;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzShareService {
    @Autowired
    private HfzShareMapper hfzShare;

    /**
     * 修改
     *
     * @return
     **/
    public int update(HfzShare obj) {
        int ret = hfzShare.update(obj);
        return ret;
    }

    /**
     * 查询
     *
     * @return
     **/
    public HfzShare selectOne() {
        return hfzShare.selectOne();
    }

}