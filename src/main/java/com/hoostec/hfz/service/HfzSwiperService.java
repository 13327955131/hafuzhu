package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzSwiperMapper;
import com.hoostec.hfz.entity.HfzSwiper;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class HfzSwiperService {
    @Autowired
    private HfzSwiperMapper HfzSwiper;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzSwiper obj) {
        int ret = HfzSwiper.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzSwiper obj) {
        int ret = HfzSwiper.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzSwiper> selectAll(HfzSwiper obj) {
        List<HfzSwiper> ret = HfzSwiper.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = HfzSwiper.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzSwiper> selectAll(HfzSwiper obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzSwiper> list = HfzSwiper.selectAll(obj);
        PageInfo<HfzSwiper> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}