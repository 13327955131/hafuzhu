package com.hoostec.hfz.utils.WxSuccessMsg;

import com.hoostec.hfz.utils.wx.CommonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateUtil {
    private static Logger log = LoggerFactory.getLogger(TemplateUtil.class);
    // 获取设置的行业信息
    public static String get_industry_url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
    // 设置所属行业
    public static String set_industry_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    // 设置模板ID
    public static String add_template_url = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
    // 获取已设置的模板列表
    public static String get_all_private_template_url = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";
    // 删除模板
    public static String del_private_template = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN";
    // 发送模板消息
    public static String send_template_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     * * 发送模板消息
     *
     * @throws ParseException
     */
    /*public static Map<String, Object> sendMouldMsg(NavyPush obj, List<String> openIds, String template_id,
                                                   String appId, String appSecret, String url) {
        int success = 0;
        int fail = 0;
        List<String> failOpenIds = new ArrayList<String>();
        // 请求地址
        String template_url = send_template_url.replace("ACCESS_TOKEN",
                CommonUtil.getToken(appId, appSecret).getAccessToken());
        NavyPush msgUtls = new NavyPush();
        //模板id
        msgUtls.setTemplate_id(template_id);
        //跳转地址
        msgUtls.setUrl(url);

        Map<Object, Object> param = new HashMap<Object, Object>();
        Map<Object, Object> param1 = new HashMap<Object, Object>();
        param1.put("value", obj.getTitle());
        param.put("first", param1);
        Map<Object, Object> param2 = new HashMap<Object, Object>();
        param2.put("value", obj.getNo());
        param.put("keyword1", param2);
        Map<Object, Object> param3 = new HashMap<Object, Object>();
        param3.put("value", obj.getTaskName());
        param.put("keyword2", param3);
        Map<Object, Object> param4 = new HashMap<Object, Object>();
        param4.put("value", obj.getState());
        param.put("keyword3", param4);
        Map<Object, Object> param5 = new HashMap<Object, Object>();
        param5.put("value", obj.getRemarks());
        param.put("remark", param5);
        msgUtls.setData(param);

        for (String string : openIds) {
            msgUtls.setTouser(string);
            JSONObject jsonObject = JSONObject.fromObject(msgUtls);
            // 发送图文消息
            JSONObject json = CommonUtil.httpRequest(template_url, "POST", jsonObject.toString());
            // 成功
            if (json.get("errcode") != null && Integer.parseInt(json.get("errcode").toString()) == 0) {
                success += 1;
            } else {
                log.info("------------" + json.get("errmsg"));
                fail += 1;
                failOpenIds.add(string);
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> failResult = new HashMap<String, Object>();
        // 发送失败的数据
        failResult.put("count", fail);
        failResult.put("list", failOpenIds);
        Map<String, Object> sucessResult = new HashMap<String, Object>();
        // 发送成功的数据
        sucessResult.put("count", success);
        sucessResult.put("list", null);
        result.put("fail", failResult);
        result.put("seccess", sucessResult);

        return result;
    }
*/

    /**
     * 设置所属行业
     *
     * @throws ParseException
     */
    public static JSONObject getIndustry(String appId, String appSecret) throws ParseException {
        String url = get_industry_url.replace("ACCESS_TOKEN",
                CommonUtil.getAccessToken(appId, appSecret).getAccessToken());
        JSONObject json = CommonUtil.httpRequest(url, "POST", null);
        return JSONObject.fromObject(json);
    }

    /**
     * 删除已设置模板
     *
     * @throws ParseException
     */
    public static void setIndustry(String appId, String appSecret) throws ParseException {

        String url = set_industry_url.replace("ACCESS_TOKEN",
                CommonUtil.getAccessToken(appId, appSecret).getAccessToken());

        JSONObject json = CommonUtil.httpRequest(url, "POST", null);

        log.info(json.toString());

    }

    /**
     * 获取模板列表
     *
     * @throws ParseException
     */
    public static JSONObject delTemplate(String template_id, String appId, String appSecret) throws ParseException {

        String url = del_private_template.replace("ACCESS_TOKEN",
                CommonUtil.getAccessToken(appId, appSecret).getAccessToken());

        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("template_id", template_id);

        JSONObject json = CommonUtil.httpRequest(url, "POST", JSONObject.fromObject(params).toString());

        return JSONObject.fromObject(json);

    }

    /**
     * @throws ParseException
     */
    public static List<Template> getAllTemplate(String appId, String appSecret) throws ParseException {

        String url = get_all_private_template_url.replace("ACCESS_TOKEN",
                CommonUtil.getAccessToken(appId, appSecret).getAccessToken());

        JSONObject json = CommonUtil.httpRequest(url, "POST", null);

        JSONArray arry = (JSONArray) json.get("template_list");

        List<Template> templateList = JSONArray.toList(arry, new Template(), new JsonConfig());

        return templateList;

    }

    /**
     * 设置模板id
     *
     * @param template_id_short
     * @throws ParseException
     */
    public static JSONObject addTemplate(String template_id_short, String appId, String appSecret)
            throws ParseException {

        String url = add_template_url.replace("ACCESS_TOKEN",
                CommonUtil.getAccessToken(appId, appSecret).getAccessToken());
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("template_id_short", template_id_short);
        JSONObject json = CommonUtil.httpRequest(url, "POST", JSONObject.fromObject(map).toString());
        return JSONObject.fromObject(json);
    }

    public static List<Map<String, Object>> getTmpl(String user, String content) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put("value", "您有一条新的动态消息。"); // 来访用户
        param1.put("key", "first");
        list.add(param1);

        Map<String, Object> param2 = new HashMap<String, Object>();
        param2.put("value", user);
        param2.put("key", "user");
        list.add(param2);
        Map<String, Object> param3 = new HashMap<String, Object>();
        param3.put("value", content);
        param3.put("key", "ask");
        list.add(param3);
        Map<String, Object> param4 = new HashMap<String, Object>();
        param4.put("value", "请点击查看");
        param4.put("key", "remark");
        list.add(param4);
        return list;
    }

}
