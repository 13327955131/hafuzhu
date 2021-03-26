package com.hoostec.hfz.service;

import com.hoostec.hfz.dao.HfzConfigWxMapper;
import com.hoostec.hfz.entity.HfzConfigAli;
import com.hoostec.hfz.entity.HfzConfigWx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

;

@Service
public class HfzConfigWxService {
    @Autowired
    private HfzConfigWxMapper hfzConfigWx;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 修改
     *
     * @return
     **/
    public int update(HfzConfigWx obj) {
        // 删除缓存
        String key = "CONFIG_WX";
        ValueOperations<String, HfzConfigAli> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            RedisOperations<String, HfzConfigAli> operation = operations.getOperations();
            operation.delete(key);
        }
        int ret = hfzConfigWx.update(obj);
        return ret;
    }


    public HfzConfigWx selectOne() {

        HfzConfigWx wxConfig;
        String key = "CONFIG_WX";
        ValueOperations<String, HfzConfigWx> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            // 读取缓存
            wxConfig = operations.get(key);
            return wxConfig;
        } else {
            wxConfig = hfzConfigWx.selectOne();
            // 写入缓存
            operations.set(key, wxConfig);
            return wxConfig;
        }

    }

}