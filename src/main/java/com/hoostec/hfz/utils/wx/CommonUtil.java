package com.hoostec.hfz.utils.wx;

import com.hoostec.hfz.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公众平台通用接口工具类
 *
 * @author loo
 * @date 2018-12-10
 */
@Slf4j
public class CommonUtil {
    // 获取access_token的接口地址（GET） 限200（次/天）
    public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 通过code换取网页授权access_token 、 openid
    public static String code_openid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 拉取用户信息(需scope为 snsapi_userinfo)
    public static String user_info_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    // 根据token 获取 jsapi_ticket
    public static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    // jsapi_ticket
    public static String jsapi_ticket = null;
    // 获取jsapi_ticket 时间
    public static String ticketTime = null;
    // 全局access_token
    public static Token qjToken = null;
    // 获取 qjToken 时间
    public static String tokenTime = null;
    // 时间格式化
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 定义 超时时间
    private static int expireMinute = 100;

    /**
     * 获取用户Openid token
     *
     * @param code
     * @return
     * @throws JSONException
     * @throws ParseException
     */
    public static Token getToken(String code, String appid, String appsecret) throws JSONException {
        // 执行获取微信配置信息
        Token token = new Token();
        String url = code_openid_url.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
        JSONObject jsonObject = CommonUtil.httpRequest(url, "GET", null);
        if (jsonObject.get("errcode") == null) {
            token.setAccessToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
            token.setOpenid(jsonObject.getString("openid"));
            token.setStatus(200);
        } else {
            log.error("获取用户微信标识失败：" + jsonObject.get("errcode") + jsonObject.get("errmsg"));
            token.setStatus(500);
        }
        return token;
    }

    /**
     * 获取用户信息
     *
     * @param openId 用户标识
     * @return WeixinUserInfo
     */
    public static WxUser getUserInfo(String openId, String appid, String appsecret)
            throws JSONException, ParseException {
        WxUser weixinUserInfo = new WxUser();
        String accessToken = getAccessToken(appid, appsecret).getAccessToken();
        String url = user_info_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        JSONObject jsonObject = CommonUtil.httpRequest(url, "GET", null);
        log.info(jsonObject.toString());
        if (jsonObject.get("errcode") == null) {
            weixinUserInfo = (WxUser) JSONObject.toBean(jsonObject, WxUser.class);
        } else {
            log.error("获取用户微信信息失败：" + jsonObject.get("errcode") + jsonObject.get("errmsg"));
        }
        return weixinUserInfo;
    }

    /**
     * 获取access_token
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return
     * @throws ParseException
     */
    public static Token getAccessToken(String appid, String appsecret) throws ParseException {
        String nowDate = DateUtils.getCurrentTime();
        if (tokenTime == null || qjToken == null) {
            // 获取Token时间为当前时间
            tokenTime = nowDate;
            // 首次进入 获取Token
            qjToken = getToken(appid, appsecret);
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(tokenTime));
            // 获取Token时间+90分钟
            cal.add(Calendar.MINUTE, expireMinute);
            String dt = sdf.format(cal.getTime());
            if (dt.compareTo(nowDate) <= 0) {
                // 距上一次获取Token已经超过100分钟 重新获取Token
                tokenTime = nowDate;
                qjToken = getToken(appid, appsecret);
            }
        }
        return qjToken;
    }

    /**
     * 根据appid appsecret获取access_token
     *
     * @param appid
     * @param appsecret
     * @return
     */
    public static Token getToken(String appid, String appsecret) {
        Token accessToken = null;
        String url = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(url, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new Token();
                accessToken.setAccessToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    /**
     * 获取js_ticket
     *
     * @param access_token
     * @return
     */
    public static String getJsapiTicket(String access_token) {
        String url = jsapi_ticket_url.replace("ACCESS_TOKEN", access_token);
        JSONObject jsonObj = httpRequest(url, "GET", null);
        String errcode = jsonObj.getString("errcode");
        String ticket = null;
        if (errcode.equals("0")) {
            ticket = jsonObj.getString("ticket");
        }
        return ticket;
    }

    /**
     * 获取签名
     *
     * @param url
     * @return
     * @throws ParseException
     */
    public static Map<String, String> sign(String url, String appid, String appsecret) throws ParseException {
        // 获取当前系统时间
        String nowDate = DateUtils.getCurrentTime();
        String accessToken = null;

        if (ticketTime == null) {
            // 获取Token时间为当前时间
            ticketTime = nowDate;
            accessToken = getAccessToken(appid, appsecret).getAccessToken();
            // 调用根据token获取jsticket 方法
            jsapi_ticket = getJsapiTicket(accessToken);
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(ticketTime));
            // 获取Token时间+90分钟
            cal.add(Calendar.MINUTE, expireMinute);
            String dt = sdf.format(cal.getTime());
            if (dt.compareTo(nowDate) <= 0) {
                accessToken = getAccessToken(appid, appsecret).getAccessToken();
                jsapi_ticket = getJsapiTicket(accessToken);
            }
        }

        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", appid);
        return ret;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }

    /**
     * 随机加密
     *
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 产生随机串--由程序自己随机产生
     *
     * @return
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 由程序自己获取当前时间
     *
     * @return 07d74cbc909a95e9e9244e9b5224cba7
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
