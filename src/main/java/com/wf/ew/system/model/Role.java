package com.wf.ew.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * 角色表
 */
@TableName("sys_role")
public class Role {
    @TableId
    private String roleId;  // 角色id

    private String roleName;  // 角色名称

    private String comments;  // 描述

    private Integer isDelete;  // 逻辑删除，0未删除，1已删除

    private Date createTime;  // 创建时间

    private Date updateTime;  // 修改时间

    public Role() {
    }

    public Role(String roleId, String roleName) {
        setRoleId(roleId);
        setRoleName(roleName);
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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