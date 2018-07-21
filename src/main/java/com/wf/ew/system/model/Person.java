package com.wf.ew.system.model;

import java.util.Date;

/**
 * 人员表
 * <p>
 * 用户(user)跟人员(person)的区别就是：用户只存放账号、密码等基本信息，人员存放跟业务相关的信息，这样便于做统一用户认证
 * 如果是学校之类的系统，person表就不止一个，比如分学生表和教师表，所以这里的person没有写mapper和service，
 * 如果你的系统有多个人员类型，请自行建多个表，删除person即可，如果是企业系统，人员表只要一个，请对person表进行字段补充，建议不要修改用户表user的字段
 * </p>
 */
public class Person {
    private Integer personId;  // 人员id

    private String trueName;  // 真实姓名

    private String departmentId;  // 部门id

    private String positionId;  // 岗位id(如果你的系统不复杂，岗位id可以用用户表的角色代替，如果你的系统组织架构很复杂，岗位是树形结构，单独建立岗位表)

    private Date birthday;  // 出生日期

    private String idCard;  // 身份证号

    private Date createTime;  // 入职时间

    private Date updateTime;  // 修改时间

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
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