package com.hoostec.hfz.entity;

import java.util.Date;

public class CmsLogHandle {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 请求地址
     */
    private String method;

    /**
     * 
     */
    private String url;

    /**
     * 参数
     */
    private String param;

    /**
     * 操作
     */
    private String operation;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 删除状态
     */
    private Integer delStatus;

    /**
     * ID
     * @return id ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * ID
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户名称
     * @return username 用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名称
     * @param username 用户名称
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 请求地址
     * @return method 请求地址
     */
    public String getMethod() {
        return method;
    }

    /**
     * 请求地址
     * @param method 请求地址
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 参数
     * @return param 参数
     */
    public String getParam() {
        return param;
    }

    /**
     * 参数
     * @param param 参数
     */
    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    /**
     * 操作
     * @return operation 操作
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 操作
     * @param operation 操作
     */
    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    /**
     * ip地址
     * @return ip ip地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * ip地址
     * @param ip ip地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 操作时间
     * @return create_time 操作时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 操作时间
     * @param createTime 操作时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 删除状态
     * @return del_status 删除状态
     */
    public Integer getDelStatus() {
        return delStatus;
    }

    /**
     * 删除状态
     * @param delStatus 删除状态
     */
    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }
}