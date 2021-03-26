package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzUserTaskRecordMapper;
import com.hoostec.hfz.entity.HfzFeedback;
import com.hoostec.hfz.entity.HfzUserTaskRecord;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;;

@Service
public class HfzUserTaskRecordService {
    @Autowired
    private HfzUserTaskRecordMapper hfzUserTaskRecord;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 任务数求和
     * 
     * @return
     **/
    public int selectNumber(HfzUserTaskRecord obj) {
        int ret = hfzUserTaskRecord.selectNumber(obj);
        return ret;
    }
    /**
     * 任务数量
     *
     * @return
     **/
    public int selectCount(HfzUserTaskRecord obj) {
        int ret = hfzUserTaskRecord.selectCount(obj);
        return ret;
    }
    /**
     * 任务数量
     *
     * @return
     **/
    public int selectCountIndex(HfzUserTaskRecord obj) {
        int ret;
        String key="";
        if(obj.getType()==1&&obj.getDegree()==null&&obj.getDayTime()==null){
            key="taskVideoAllRedis";
        }else if(obj.getType()==1 && obj.getDegree()!=null && null==obj.getDayTime()){
            key="taskVideoDownloadAllRedis";

        }else if(obj.getType()==2&&obj.getDegree()==null&&obj.getDayTime()==null){
            key="taskBrowseAllRedis";
        }else if(obj.getType()==2&&obj.getDegree()!=null&&obj.getDayTime()==null){
            key="taskBrowseDownloadAllRedis";

        }else if(obj.getType()==1&&obj.getDegree()==null&&obj.getDayTime()!=null){
            key="taskVideoThsRedis";
         }else if(obj.getType()==1&&obj.getDegree()!=null&&obj.getDayTime()!=null){
            key="taskVideoDownloadThsRedis";

        }else if(obj.getType()==2&&obj.getDegree()==null&&obj.getDayTime()!=null){
            key="taskBrowseThsRedis";
        }else if(obj.getType()==2&&obj.getDegree()!=null&&obj.getDayTime()!=null){
            key="taskBrowseDownloadThsRedis";
        }

        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            // 读取缓存
            ret = operations.get(key);
            return ret;
        } else {
            ret = hfzUserTaskRecord.selectCount(obj);
            // 写入缓存
            operations.set(key, ret, 10, TimeUnit.MINUTES);
            return ret;
        }

       // int ret = hfzUserTaskRecord.selectCount(obj);
    }

    /**
     * 插入接口
     *
     * @return
     **/
    public int insert(HfzUserTaskRecord obj) {
        int ret = hfzUserTaskRecord.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzUserTaskRecord obj) {
        int ret = hfzUserTaskRecord.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzUserTaskRecord> selectAll(HfzUserTaskRecord obj) {
        List<HfzUserTaskRecord> ret = hfzUserTaskRecord.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzUserTaskRecord.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzUserTaskRecord> selectAll(HfzUserTaskRecord obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzUserTaskRecord> list = hfzUserTaskRecord.selectAllUserTask(obj);
        PageInfo<HfzUserTaskRecord> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}