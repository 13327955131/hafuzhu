package com.hoostec.hfz.service;

import com.hoostec.hfz.dao.HfzConfigAliMapper;
import com.hoostec.hfz.entity.HfzConfigAli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

;

@Service
public class HfzConfigAliService {
    @Autowired
    private HfzConfigAliMapper hfzConfigAli;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 修改
     *
     * @return
     **/
    public int update(HfzConfigAli obj) {
        // 删除缓存
        String key = "CONFIG_ALI";
        ValueOperations<String, HfzConfigAli> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            RedisOperations<String, HfzConfigAli> operation = operations.getOperations();
            operation.delete(key);
        }
        int ret = hfzConfigAli.update(obj);
        return ret;
    }


    public HfzConfigAli selectOne() {
        HfzConfigAli aliConfig;
        String key = "CONFIG_ALI";
        ValueOperations<String, HfzConfigAli> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            // 读取缓存
            aliConfig = operations.get(key);
            return aliConfig;
        } else {
            aliConfig = hfzConfigAli.selectOne();
            // 写入缓存
            operations.set(key, aliConfig);
            return aliConfig;
        }

    }
}