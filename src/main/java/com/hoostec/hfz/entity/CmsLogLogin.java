package com.hoostec.hfz.entity;

import java.util.Date;

public class CmsLogLogin {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户
     */
    private Integer userId;

    /**
     * 登陆人ip
     */
    private String ip;

    /**
     * 登录时间
     */
    private Date createTime;

    /**
     * 删除状态
     */
    private Integer delStatus;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String type;

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
     * 用户
     * @return user_id 用户
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户
     * @param userId 用户
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 登陆人ip
     * @return ip 登陆人ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 登陆人ip
     * @param ip 登陆人ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 登录时间
     * @return create_time 登录时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 登录时间
     * @param createTime 登录时间
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

    /**
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}