package com.wf.ew.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * 用户角色关联表
 * 如果你的用户只对应一个角色，把前台的多选select改成单选即可，不需要改表结构
 */
@TableName("sys_user_role")
public class UserRole {
    @TableId
    private String id;  // 主键

    private String userId;  // 用户id

    private String roleId;  // 角色id

    private Date createTime;  // 创建时间

    @TableField(exist = true)
    private String roleName;  // 角色名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}