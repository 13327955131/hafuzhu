package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.CmsAdmin;
import com.hoostec.hfz.entity.HfzCardType;
import com.hoostec.hfz.entity.HfzGoods;
import com.hoostec.hfz.entity.HfzGoodsType;
import com.hoostec.hfz.service.HfzCardTypeService;
import com.hoostec.hfz.service.HfzGoodsService;
import com.hoostec.hfz.service.HfzGoodsTypeService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *   平台用户管理
 */
@RestController
@RequestMapping("hfz/goods")
public class HfzGoodsController {
    @Autowired
    private HfzGoodsService hfzGoodsService;
    @Autowired
    private HfzCardTypeService hfzCardTypeService;

    @Autowired
    private HfzGoodsTypeService hfzGoodsTypeService;

    /**
     * 商品列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('goods:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil goodList(HfzGoods obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzGoods> list = hfzGoodsService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }

    /**
     * 商品新增
     *
     * @return
     */
    @MyLog("商品新增")
    @PreAuthorize("hasAuthority('goods:add')")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDataUtil goodsAdd(HfzGoods obj) {

        hfzGoodsService.insert(obj);
            return ResultDataUtil.isOk(null);
    }

    /**
     * 商品删除
     *
     * @param request
     * @return
     */
    @MyLog("商品删除")
    @PreAuthorize("hasAuthority('goods:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(hfzGoodsService.deleteAll(ids));
    }


    /**
     * 用户更新
     *
     * @return
     */
    @MyLog("商品更新")
    @PreAuthorize("hasAuthority('goods:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(HfzGoods user) {
        // 更新用户表
        hfzGoodsService.update(user);

        return ResultDataUtil.isOk(null);
    }

    /**
     * 查询福卡类型的商品
     *
     * @return
     */
    @PreAuthorize("hasAuthority('goods:list')")
    @RequestMapping(value = "/goodsConut", method = RequestMethod.POST)
    public ResultDataUtil goodsConut(HfzGoods obj) {
        List<HfzGoods> list=null;


        //获取福卡类型的id
        HfzGoodsType card=new HfzGoodsType();
        card.setName("福卡");
        List<HfzGoodsType> listCard = hfzGoodsTypeService.selectAll(card);

        //查询福卡id下面是否有福卡商品
        if(listCard.size()>0){
            card=listCard.get(0);
            obj.setTypeId(card.getId());
            obj.setDelStatus(Constant.DEL_STATUS_ONE);
            list = hfzGoodsService.selectAll(obj);
        }

        //如果福卡id下有福卡  那么就回调  1 否则就 0
        int size=0;
          if(list!=null&&list.size()>0){
            size=card.getId();
        }

        JSONObject json=new JSONObject();
        //传过去下拉选改变事件专用
          json.put("typeId",card.getId());
        //此方法判断使用
          json.put("res",size);

        return ResultDataUtil.isOk(json);
    }



}
