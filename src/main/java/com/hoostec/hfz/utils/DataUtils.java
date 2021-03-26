package com.hoostec.hfz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 *
 * @Date: 20180619
 * @author: loo
 * @version: 1.00
 */
public class DataUtils {

    /**
     * 获取访问者ip地址
     *
     * @return
     */
    public static String getIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 因为有些有些登录是通过代理，所以取第一个（第一个为真是ip）
        int index = ip.indexOf(',');
        if (index != -1) {
            ip = ip.substring(0, index);
        }
        return ip;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 获取请求地址参数
     *
     * @param request
     * @return
     */
    public static String getQueryString(HttpServletRequest request) {
        String queryString = "";
        if ("get".equalsIgnoreCase(request.getMethod())) {
            queryString = request.getQueryString();
        } else {
            Map<String, String[]> params = request.getParameterMap();
            for (String key : params.keySet()) {
                String[] values = params.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    queryString += key + "=" + value + "&";
                }
            }
            if (queryString.length() > 1) {
                // 去掉最后一个空格
                queryString = queryString.substring(0, queryString.length() - 1);
            }
        }
        if (queryString == null) {
            queryString = "";
        }
        return queryString;
    }

    /**
     * 获取request参数
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getRequestParams(HttpServletRequest request) throws Exception {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        return result;
    }

    /**
     * 获取指定位数的随机数字
     *
     * @param length 随机数长度
     * @return
     */
    public static String getRandom(int length) {
        if (length <= 0) {
            length = 1;
        }
        StringBuilder res = new StringBuilder();
        Random random = new Random();
        int i = 0;
        while (i < length) {
            res.append(random.nextInt(10));
            i++;
        }
        return res.toString();
    }

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomChar(int length) {
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(36)]);
        }
        return buffer.toString();
    }

    /**
     * 将Map转为JSON字符串
     *
     * @param map
     * @return
     */
    public static String mapToJsonString(Map<String, String> map) {
        // 将MAP转为JSON
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        // 将JSON转换为字符串
        String jsonStr = new Gson().toJson(jsonObject);
        return jsonStr;
    }


    /**
     * 网络类型
     *
     * @param req
     * @return
     */
    public static String getNetType(HttpServletRequest req) {
        String netType = "";
        String ua = req.getHeader("User-Agent");
        String[] s = ua.split(" ");
        if (ua.contains("Windows")) {
            netType = "WIFI";
        } else {
            for (String net : s) {
                if (net.contains("NetType/")) {
                    netType = net.split("/")[1];
                }
            }
        }
        return netType;
    }

    /**
     * 是否安卓
     *
     * @param req
     * @return
     */
    public static boolean isAndroid(HttpServletRequest req) {
        String ua = req.getHeader("User-Agent");
        // 是否安卓
        boolean isAndroid = false;
        if (ua.contains("Android") || ua.contains("'Linux'")) {
            isAndroid = true;
        }
        return isAndroid;
    }
}
