package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzUserIntegralRecordMapper;
import com.hoostec.hfz.entity.HfzUser;
import com.hoostec.hfz.entity.HfzUserIntegralRecord;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;;

@Service
public class HfzUserIntegralRecordService {
    @Autowired
    private HfzUserIntegralRecordMapper hfzUserIntegralRecord;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzUserIntegralRecord obj) {
        int ret = hfzUserIntegralRecord.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzUserIntegralRecord obj) {
        int ret = hfzUserIntegralRecord.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzUserIntegralRecord> selectAll(HfzUserIntegralRecord obj) {
        List<HfzUserIntegralRecord> ret = hfzUserIntegralRecord.selectAll(obj);
        return ret;
    }
    /**
     * 根据日期去重查询并排序  + 分页    首页专用
     *
     * @return
     **/
    public PageInfo<HfzUserIntegralRecord> selectAllGroupSort(HfzUserIntegralRecord obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());

        List<HfzUserIntegralRecord> list;

        String key = "";
        if(obj.getUseTime()==null){
            key= "UserIntegralRecordGroupSort";
        }else if(obj.getUseTime()!=null){
            key= "UserIntegralRecordThisGroupSort";
        }
        ValueOperations<String, List<HfzUserIntegralRecord>> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            // 读取缓存
            list = operations.get(key);
        } else {
            list = hfzUserIntegralRecord.selectAllGroupSort(obj);
            // 写入缓存
            operations.set(key, list, 10, TimeUnit.MINUTES);
        }
      //  List<HfzUserIntegralRecord> list = hfzUserIntegralRecord.selectAllGroupSort(obj);

        PageInfo<HfzUserIntegralRecord> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzUserIntegralRecord.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzUserIntegralRecord> selectAll(HfzUserIntegralRecord obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzUserIntegralRecord> list = hfzUserIntegralRecord.selectAllUser(obj);
        PageInfo<HfzUserIntegralRecord> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}