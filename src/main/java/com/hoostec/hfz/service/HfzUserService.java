package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzUserMapper;
import com.hoostec.hfz.entity.HfzUser;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

;

@Service
public class HfzUserService {
    @Autowired
    private HfzUserMapper hfzUser;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 插入接口
     *
     * @return
     **/
    public int insert(HfzUser obj) {
        int ret = hfzUser.insert(obj);
        return ret;
    }

    /**
     * 修改
     *
     * @return
     **/
    public int update(HfzUser obj) {
        int ret = hfzUser.update(obj);
        return ret;
    }
    /**
     * 查询数量
     *
     * @return
     **/
    public int selectCount(HfzUser obj) {
        int ret = hfzUser.selectCount(obj);
        return ret;
    }
    /**
     * 查询数量Index
     *
     * @return
     **/
    public int selectCountIndex(HfzUser obj) {
        int ret;
        String key;
        if(obj.getRegisterTime()==null){
            key="userCountAll";
        }else{
            key="userCountThs";
        }

        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            // 读取缓存
            ret = operations.get(key);
            return ret;
        } else {
            ret = hfzUser.selectCount(obj);
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
    public List<HfzUser> selectAll(HfzUser obj) {
        List<HfzUser> ret = hfzUser.selectAll(obj);
        return ret;
    }
    /**
     * 查询用户有多少徒弟   只取五条
     *
     * @return
     **/
    public List<HfzUser> selectParent(HfzUser obj) {
        List<HfzUser> ret;
        String key = "PARENT";
        ValueOperations<String, List<HfzUser>> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            // 读取缓存
            ret = operations.get(key);
            return ret;
        } else {
            ret = hfzUser.selectParent(obj);
            // 写入缓存
            operations.set(key, ret, 10, TimeUnit.MINUTES);
            return ret;
        }
    }
    /**
     * 根据省份去重
     *
     * @return
     **/
    public List<HfzUser> selectProvincia(HfzUser obj) {
        List<HfzUser> ret = hfzUser.selectProvincia(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     *
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzUser.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzUser> selectAll(HfzUser obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzUser> list = hfzUser.selectAll(obj);
        PageInfo<HfzUser> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    /**
     * 查询手机号是否存在
     *
     * @param phone
     * @return
     */
    public boolean selectPhoneExist(String phone) {
        return hfzUser.selectPhoneExist(phone);
    }


}