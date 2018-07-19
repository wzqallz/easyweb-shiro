package com.wf.ew.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * 权限表
 */
@TableName("sys_authorities")
public class Authorities {
    @TableId
    private String authorityId;  // 权限id

    private String authorityName;  // 权限名称

    private String authority;  // 权限标识（菜单url，如果为空不会添加在shiro的权限列表中）

    private String parentId;  // 上级菜单

    private int isMenu;  // 菜单还是按钮（菜单会显示在侧导航，按钮不会显示在侧导航，只要url不是空，都会作为权限标识）

    private int orderNumber;  // 排序号

    private String menuIcon;  // 菜单图标

    private Date createTime;  // 创建时间

    private Date updateTime;  // 修改时间

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(int isMenu) {
        this.isMenu = isMenu;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
