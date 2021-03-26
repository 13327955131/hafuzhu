package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzLuckDrawRecordMapper;
import com.hoostec.hfz.entity.HfzLuckDrawRecord;
import java.util.List;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzLuckDrawRecordService {
    @Autowired
    private HfzLuckDrawRecordMapper hfzLuckDrawRecord;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzLuckDrawRecord obj) {
        int ret = hfzLuckDrawRecord.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzLuckDrawRecord obj) {
        int ret = hfzLuckDrawRecord.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzLuckDrawRecord> selectAll(HfzLuckDrawRecord obj) {
        List<HfzLuckDrawRecord> ret = hfzLuckDrawRecord.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzLuckDrawRecord.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzLuckDrawRecord> selectAll(HfzLuckDrawRecord obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzLuckDrawRecord> list = hfzLuckDrawRecord.selectAllUser(obj);
        PageInfo<HfzLuckDrawRecord> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}