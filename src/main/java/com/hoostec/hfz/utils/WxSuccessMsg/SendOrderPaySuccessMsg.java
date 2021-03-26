package com.hoostec.hfz.utils.WxSuccessMsg;//package com.hoostec.hfz.utils.WxSuccessMsg;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.thymeleaf.engine.TemplateData;
//
//public class SendOrderPaySuccessMsg {
//	   Logger log = LoggerFactory.getLogger(getClass());
//
//	    /**
//	     * 发送模板消息
//	     * appId 公众账号的唯一标识
//	     * appSecret 公众账号的密钥
//	     * openId 用户标识
//	     */
//	    public void send_template_message(String openId,String title,String proName,String proNo,String time,String remarks,String urls) {
//	    	String appId="wxc380d3546b0f0a3c";//
//	    	String appSecret="6927a9861c1023ebe20554e758abaf68";//
//	        AccessToken token = WeixinUtil.getAccessToken(appId, appSecret);
//	        String access_token = token.getToken();
//	        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
//	        WxTemplate temp = new WxTemplate();
//	        temp.setUrl(urls);//点击模板详情的时候跳转的链接
//	        temp.setTouser(openId);
//	        temp.setTopcolor("#000000");
//	        temp.setTemplate_id("MlpQ0Kfse2GDQOauYS6_mfK-eein1MKXsJ0UfUiIJ7U");// 模板ID
//	        Map<String, TemplateData> m = new HashMap<String,TemplateData>();
//	        TemplateData first = new TemplateData();
//	        first.setColor("#000000");
//	        first.setValue(title);
//	        m.put("first", first);
//	        TemplateData name = new TemplateData();
//	        name.setColor("#000000");
//	        name.setValue(proName);
//	        m.put("keyword1", name);
//	        TemplateData wuliu = new TemplateData();
//	        wuliu.setColor("#000000");
//	        wuliu.setValue(proNo);
//	        m.put("keyword2", wuliu);
//	        TemplateData orderNo = new TemplateData();
//	        orderNo.setColor("#000000");
//	        orderNo.setValue(time);
//	        m.put("keyword3", orderNo);
//	        temp.setData(m);
//	        TemplateData remark = new TemplateData();
//	        remark.setColor("#000000");
//	        remark.setValue(remarks);
//	        m.put("remark", remark);
//	        temp.setData(m);
//	        String jsonString = JSONObject.fromObject(temp).toString();
//	        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
//	        System.out.println(jsonObject);
//	        int result = 0;
//	        if (null != jsonObject) {
//	             if (0 != jsonObject.getInt("errcode")) {
//	                 result = jsonObject.getInt("errcode");
//	                 log.error("错误 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
//	             }
//	         }
//	        log.info("模板消息发送结果："+result);
//	    }
//}
