package com.hoostec.hfz.utils.wx;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class MyX509TrustManager implements X509TrustManager {

	// 检查客户端证书
	public void checkClientTrusted(X509Certificate[] chain, String authType) {
	}

	// 检查服务器端证书
	public void checkServerTrusted(X509Certificate[] chain, String authType) {
	}

	// 返回受信任的X509证书数组
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}