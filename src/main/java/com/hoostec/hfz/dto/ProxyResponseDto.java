package com.hoostec.hfz.dto;

import lombok.Data;

@Data
public class ProxyResponseDto {

    private String proxyIp;
    private int proxyPort;
    private int deviceNo;
    private String linkUrl;
    private String proxyUa;

    public ProxyResponseDto(String proxyIp, int proxyPort, String proxyUa, int deviceNo, String linkUrl) {
        this.proxyIp = proxyIp;
        this.proxyPort = proxyPort;
        this.proxyUa = proxyUa;
        this.deviceNo = deviceNo;
        this.linkUrl = linkUrl;
    }
}
