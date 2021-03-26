package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzProfitRecordMapper;
import com.hoostec.hfz.entity.HfzProfitRecord;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hoostec.hfz.entity.HfzUserBalance;
import com.hoostec.hfz.entity.HfzUserIntegralRecord;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;;

@Service
public class HfzProfitRecordService {
    @Autowired
    private HfzProfitRecordMapper hfzProfitRecord;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzProfitRecord obj) {
        int ret = hfzProfitRecord.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzProfitRecord obj) {
        int ret = hfzProfitRecord.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzProfitRecord> selectAll(HfzProfitRecord obj) {
        List<HfzProfitRecord> ret = hfzProfitRecord.selectAll(obj);
        return ret;
    }
    /**
     * 去重 求和 排序         首页专用
     *
     * @return
     **/
    public PageInfo<HfzProfitRecord> selectAllGroupSort(HfzProfitRecord obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());


        List<HfzProfitRecord> list;

        String key = "";
        if(obj.getGetTime()==null){
            key= "HfzProfitRecordGroupSort";
        }else if(obj.getGetTime()!=null){
            key= "HfzProfitRecordThisGroupSort";
        }
        ValueOperations<String, List<HfzProfitRecord>> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
      //  List<HfzProfitRecord> list = hfzProfitRecord.selectAllGroupSort(obj);

        if (hasKey) {
            // 读取缓存
            list = operations.get(key);
        } else {
            list = hfzProfitRecord.selectAllGroupSort(obj);
            // 写入缓存
            operations.set(key, list, 10, TimeUnit.MINUTES);
        }







        PageInfo<HfzProfitRecord> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzProfitRecord.deleteAll(ids);
        return ret;
    }
    /**
     * 关联用户表查询
     *
     * @return
     **/
    public List<HfzProfitRecord> selectAllUser(HfzProfitRecord obj) {
        List<HfzProfitRecord> ret = hfzProfitRecord.selectAllUser(obj);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzProfitRecord> selectAll(HfzProfitRecord obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzProfitRecord> list = hfzProfitRecord.selectAllUser(obj);
        PageInfo<HfzProfitRecord> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    /**
     * 批量查询    这个表和收益表共同查询并分页
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzProfitRecord> selectProfitRecord(HfzProfitRecord obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzProfitRecord> list = hfzProfitRecord.selectProfitRecord(obj);
        PageInfo<HfzProfitRecord> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

}