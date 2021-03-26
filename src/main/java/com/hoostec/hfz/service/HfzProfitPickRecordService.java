package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzProfitPickRecordMapper;
import com.hoostec.hfz.entity.HfzProfitPickRecord;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hoostec.hfz.entity.HfzProfitRecord;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;;

@Service
public class HfzProfitPickRecordService {
    @Autowired
    private HfzProfitPickRecordMapper hfzProfitPickRecord;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzProfitPickRecord obj) {
        int ret = hfzProfitPickRecord.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzProfitPickRecord obj) {
        int ret = hfzProfitPickRecord.update(obj);
        return ret;
    }
 /**
     * 数量
     *
     * @return
     **/
    public int selectCount(HfzProfitPickRecord obj) {
        int ret = hfzProfitPickRecord.selectCount(obj);
        return ret;
    }
    /**
     * 昨日支出统计
     *
     * @return
     **/
    public int selectDayPay(HfzProfitPickRecord obj) {
        int ret = hfzProfitPickRecord.selectDayPay(obj);
        return ret;
    }
    /**
     * 数量 首页专用
     *
     * @return
     **/
    public int selectCountIindex(HfzProfitPickRecord obj) {
        int ret;
        String key = "HfzProfitPickRecordCount";		//自定义

        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            // 读取缓存
            ret = operations.get(key);
            return ret;
        } else {
            ret = hfzProfitPickRecord.selectCount(obj);
            // 写入缓存
            operations.set(key, ret, 10, TimeUnit.MINUTES);
            return ret;
        }


    }





    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzProfitPickRecord> selectAll(HfzProfitPickRecord obj) {
        List<HfzProfitPickRecord> ret = hfzProfitPickRecord.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzProfitPickRecord.deleteAll(ids);
        return ret;
    }
    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzProfitPickRecord> selectAll(HfzProfitPickRecord obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzProfitPickRecord> list = hfzProfitPickRecord.selectAllUser(obj);
        PageInfo<HfzProfitPickRecord> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}