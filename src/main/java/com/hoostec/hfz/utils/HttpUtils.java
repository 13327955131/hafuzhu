package com.hoostec.hfz.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

    public static String getOneIp() throws Exception {
        String content = "";
        // 创建Httpclient对象,相当于打开了浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建HttpGet请求，相当于在浏览器输入地址
        HttpGet httpGet = new HttpGet("http://d.jghttp.golangapi.com/getip?num=1&type=1&pro=0&city=0&yys=0&port=1&time=1&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=");
//        HttpGet httpGet = new HttpGet("https://too.ueuz.com/frontapi/public/http/get_ip/index?type=-1&iptimelong=1&ipcount=1&protocol=0&areatype=1&area=&resulttype=txt&duplicate=2&separator=1&other=&show_city=0&show_carrier=0&show_expire=0&isp=-1&auth_key=90e36d680f1c3e6ef47bf84e3fbbb76e&timestamp=1552356444&sign=869A0A0C930B8377A258CF385D4D503C");

        CloseableHttpResponse response = null;
        try {
            // 执行请求，相当于敲完地址后按下回车。获取响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                content = EntityUtils.toString(response.getEntity(), "UTF-8");

            }
        } finally {
            if (response != null) {
                // 关闭资源
                response.close();
            }
            // 关闭浏览器
            httpclient.close();
        }
        return content;
    }

    /**
     * 设置ip 白名单
     *
     * @param ip
     * @throws Exception
     */
    public static void setWhiteIp(String ip) throws Exception {
        // 创建Httpclient对象,相当于打开了浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建HttpGet请求，相当于在浏览器输入地址
        HttpGet httpGet = new HttpGet("http://webapi.jghttp.golangapi.com/index/index/save_white?neek=8966&appkey=a2a046e1f53ac43a25f446e2dea11e6f&white=" + ip);
//        HttpGet httpGet = new HttpGet("https://too.ueuz.com/frontapi/public/http/get_ip/index?type=-1&iptimelong=1&ipcount=1&protocol=0&areatype=1&area=&resulttype=txt&duplicate=2&separator=1&other=&show_city=0&show_carrier=0&show_expire=0&isp=-1&auth_key=90e36d680f1c3e6ef47bf84e3fbbb76e&timestamp=1552356444&sign=869A0A0C930B8377A258CF385D4D503C");

        CloseableHttpResponse response = null;
        try {
            // 执行请求，相当于敲完地址后按下回车。获取响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");

            }
        } finally {
            if (response != null) {
                // 关闭资源
                response.close();
            }
            // 关闭浏览器
            httpclient.close();
        }
    }
}


