package com.hoostec.hfz.utils;

import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by leiwei on 2018/8/10/010
 * Des 加参拦截器
 */
public class ParamCheckUtil {

    public static final String app_key = "20181120091950000001";
    public static final String app_secret = "bfac9b16f9b5faa7e4c88254745a8af250e6b5ab";
    public static final String app_sign = "bd56bb7de99787483511dbe725e14d54e16696ee";


    public static ApiResultDataUtil checkParam(HttpServletRequest request) {
        Map<String, String[]> params = new HashMap(request.getParameterMap());
        String nonce = request.getParameter("nonce");
        System.out.println(nonce);
        String timestamp = request.getParameter("curtime");
        System.out.println(timestamp);
        String checksum1 = request.getParameter("checksum");

        String sign = request.getParameter("sign");
        String app_key = request.getParameter("app_key");
        String version = request.getParameter("version");
        String queryString = "";
        params.remove("sign");

        Map<String, String> map2 = new TreeMap<String, String>();
        //身份加密
        StringBuffer sb = new StringBuffer();
        sb.append(app_secret);
        sb.append(nonce);
        sb.append(timestamp);
        String checksum = SHA1(sb.toString());

        System.out.println("checksum:" + checksum);
        System.out.println("checksum1:" + checksum1);

        if (!checksum.equals(checksum1)) {
            System.out.println("checksum:" + "false");
        } else {
            System.out.println("checksum:" + "true");
        }

        String sign1 = addSign(params);
        System.out.println("sign1:" + sign1);


        if (!sign1.equals(sign)) {
            System.out.println("sign:" + "false");
        } else {
            System.out.println("sign:" + "true");
        }


        return null;
    }

    public String getRamdom() {
        Random random = new Random();
        return random.nextInt(1000) + "";
    }

    /**
     * SHA1 安全加密算法
     *
     * @param decrypt 参数要加密的字符串
     * @return
     */
    public static String SHA1(String decrypt) {
        try {
            //指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decrypt.getBytes());
            //获取字节数组
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toLowerCase();

        } catch (Exception e) {
            return "签名错误！";
        }
    }

    /**
     * 获取当前时间戳,只有10位，本地截取
     *
     * @return
     */
    protected String getTimestamp() {
        String time = System.currentTimeMillis() / 1000 + "";
        return time;
    }

    /**
     * 对参数进行排序并MD5加密
     *
     * @param map
     * @return
     */
    protected static String addSign(Map<String, String[]> map) {
        Map<String, String[]> resultMap = sortMapByKey(map);//按Key进行排序

        StringBuffer signString = new StringBuffer();
        signString.append(app_sign);
        for (Map.Entry<String, String[]> entry : resultMap.entrySet()) {
            signString.append(entry.getValue());
        }
        System.out.println(signString);
//        Log.d("leiwei","signString = " + signString);
        //解决换行符造成md5加密错误的问题[\\s*\t\n\r]
        return DigestUtils.md5DigestAsHex(signString.toString().replaceAll("[\\n]", "").getBytes());
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String[]> sortMapByKey(Map<String, String[]> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String[]> sortMap = new TreeMap<String, String[]>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    //比较器类
    static class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }
}
