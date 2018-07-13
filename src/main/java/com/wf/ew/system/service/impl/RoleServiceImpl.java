package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wf.ew.common.exception.ParameterException;
import com.wf.ew.common.utils.UUIDUtil;
import com.wf.ew.system.dao.RoleAuthoritiesMapper;
import com.wf.ew.system.dao.RoleMapper;
import com.wf.ew.system.dao.UserRoleMapper;
import com.wf.ew.system.model.Role;
import com.wf.ew.system.model.UserRole;
import com.wf.ew.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthoritiesMapper roleAuthoritiesMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String[] getRoleIds(String userId) {
        List<UserRole> userRoles = userRoleMapper.selectList(new EntityWrapper().eq("user_id", userId));
        String[] roleIds = new String[userRoles.size()];
        for (int i = 0; i < userRoles.size(); i++) {
            roleIds[i] = userRoles.get(i).getRoleId();
        }
        return roleIds;
    }

    @Override
    public List<Role> list(boolean showDelete) {
        Wrapper wrapper = new EntityWrapper();
        if (!showDelete) {
            wrapper.eq("is_delete", 0);
        }
        return roleMapper.selectList(wrapper.orderBy("create_time", true));
    }

    @Override
    public boolean add(Role role) {
        role.setRoleId(UUIDUtil.randomUUID8());
        role.setCreateTime(new Date());
        return roleMapper.insert(role) > 0;
    }

    @Override
    public boolean update(Role role) {
        return roleMapper.updateById(role) > 0;
    }

    @Override
    public boolean updateState(String roleId, int isDelete) {
        if (isDelete != 0 && isDelete != 1) {
            throw new ParameterException("isDelete值需要在[0,1]中");
        }
        Role role = new Role();
        role.setRoleId(roleId);
        role.setIsDelete(isDelete);
        boolean rs = roleMapper.updateById(role) > 0;
        if (rs) {  //删除角色的权限
            roleAuthoritiesMapper.delete(new EntityWrapper().eq("role_id", roleId));
        }
        return rs;
    }

    @Override
    public Role getById(String roleId) {
        return roleMapper.selectById(roleId);
    }

    @Override
    public boolean delete(String roleId) {
        return roleMapper.deleteById(roleId) > 0;
    }
}
