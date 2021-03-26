package com.hoostec.hfz.entity;

import java.util.List;

public class CmsMenu {
    /**
     * 
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 跳转
     */
    private String jump;

    /**
     * name
     */
    private String name;

    /**
     * 是否展开
     */
    private Integer spread;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 使用状态
     */
    private Integer isUse;

    /**
     * 删除状态
     */
    private Integer delStatus;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sequence;

    /**
     * 
     */
    private String route;
    
    
    private List<CmsMenu> children;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 标题
     * @return title 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 图标
     * @return icon 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 图标
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 跳转
     * @return jump 跳转
     */
    public String getJump() {
        return jump;
    }

    /**
     * 跳转
     * @param jump 跳转
     */
    public void setJump(String jump) {
        this.jump = jump == null ? null : jump.trim();
    }

    /**
     * name
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * name
     * @param name name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 是否展开
     * @return spread 是否展开
     */
    public Integer getSpread() {
        return spread;
    }

    /**
     * 是否展开
     * @param spread 是否展开
     */
    public void setSpread(Integer spread) {
        this.spread = spread;
    }

    /**
     * 类型
     * @return type 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型
     * @param type 类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 使用状态
     * @return is_use 使用状态
     */
    public Integer getIsUse() {
        return isUse;
    }

    /**
     * 使用状态
     * @param isUse 使用状态
     */
    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
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
     * 父级id
     * @return parent_id 父级id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 父级id
     * @param parentId 父级id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 排序
     * @return sequence 排序
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * 排序
     * @param sequence 排序
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * 
     * @return route 
     */
    public String getRoute() {
        return route;
    }

    /**
     * 
     * @param route 
     */
    public void setRoute(String route) {
        this.route = route == null ? null : route.trim();
    }

	public List<CmsMenu> getChildren() {
		return children;
	}

	public void setChildren(List<CmsMenu> children) {
		this.children = children;
	}
}