package com.hoostec.hfz.utils.wx.pay;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class WeChatWithdrawUtils {
    private static final Logger log = LoggerFactory.getLogger(WeChatWithdrawUtils.class);
    private byte[] certData;

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public WeChatWithdrawUtils() throws Exception {
        String certPath = "C:\\files\\apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }


    /**
     * 提现
     * 请求，只请求一次，不做重试
     *
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws Exception
     */
    public static String withdrawRequestOnce(Map<String, String> params, int connectTimeoutMs, int readTimeoutMs, boolean useCert) throws Exception {

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("mch_appid", params.get("mch_appid"));
        paraMap.put("mchid", params.get("mchid"));
        paraMap.put("nonce_str", OtherUtils.getNonceStr());
        paraMap.put("partner_trade_no", params.get("partner_trade_no"));
        paraMap.put("openid", params.get("openid"));
        // 校验用户姓名选项 NO_CHECK：不校验真实姓名  FORCE_CHECK：强校验真实姓名
        paraMap.put("check_name", "NO_CHECK");
        paraMap.put("amount", params.get("amount"));
        //企业付款操作说明信息。必填。
        paraMap.put("desc","哈福猪收益提现");     //付款备注信息（付款原因）
        paraMap.put("spbill_create_ip", params.get("spbill_create_ip"));

        String url = OtherUtils.formatUrlMap(paraMap, false, false);
        url = url + "&key=" + params.get("mchsecret");

        String sign = MD5Utils.MD5Encoding(url).toUpperCase();

        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        for (Map.Entry<String, String> entry : paraMap.entrySet()) {
            xml.append("<" + entry.getKey() + ">");
            xml.append(entry.getValue());
            xml.append("</" + entry.getKey() + ">" + "\n");
        }
        xml.append("<sign>");
        xml.append(sign);
        xml.append("</sign>");
        xml.append("</xml>");

        BasicHttpClientConnectionManager connManager;
        if (useCert) {
            // 证书
            char[] password = "1536344651".toCharArray();
            InputStream certStream = new WeChatWithdrawUtils().getCertStream();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new org.apache.http.conn.ssl.DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );
        } else {
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build(),
                    null,
                    null,
                    null
            );
        }
        org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(xml.toString(), "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + params.get("mchid"));  // TODO: 很重要，用来检测 sdk 的使用情况，要不要加上商户信息？
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }
}
