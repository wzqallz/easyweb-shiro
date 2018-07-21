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
    private Integer id;  // 主键

    private Integer userId;  // 用户id

    private Integer roleId;  // 角色id

    private Date createTime;  // 创建时间

    @TableField(exist = true)
    private String roleName;  // 角色名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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