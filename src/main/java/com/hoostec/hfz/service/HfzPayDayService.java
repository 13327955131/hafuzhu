package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzPayDayMapper;
import com.hoostec.hfz.entity.HfzPayDay;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hoostec.hfz.entity.HfzProfitRecord;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;;

@Service
public class HfzPayDayService {
    @Autowired
    private HfzPayDayMapper hfzPayDay;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzPayDay obj) {
        int ret = hfzPayDay.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzPayDay obj) {
        int ret = hfzPayDay.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzPayDay> selectAll(HfzPayDay obj) {
        List<HfzPayDay> ret = hfzPayDay.selectAll(obj);
        return ret;
    }
    /**
     * 首页专用   金额倒叙
     *
     * @return
     **/
    public List<HfzPayDay> selectAllPay(HfzPayDay obj) {


        List<HfzPayDay> ret;
        String key = "selectAllPayRedis";		//自定义
        ValueOperations<String, List<HfzPayDay>> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            // 读取缓存
            ret = operations.get(key);
            return ret;
        } else {
            ret =hfzPayDay.selectAllPay(obj);
            // 写入缓存
            operations.set(key, ret, 10, TimeUnit.MINUTES);
            return ret;
        }


       /* List<HfzPayDay> ret = hfzPayDay.selectAllPay(obj);
        return ret;*/
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzPayDay.deleteAll(ids);
        return ret;
    }
    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzPayDay> selectAll(HfzPayDay obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzPayDay> list = hfzPayDay.selectAll(obj);
        PageInfo<HfzPayDay> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

}