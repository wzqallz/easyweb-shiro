package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wf.ew.common.utils.UUIDUtil;
import com.wf.ew.system.dao.AuthoritiesMapper;
import com.wf.ew.system.dao.RoleAuthoritiesMapper;
import com.wf.ew.system.model.Authorities;
import com.wf.ew.system.model.RoleAuthorities;
import com.wf.ew.system.service.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {
    @Autowired
    private AuthoritiesMapper authoritiesMapper;
    @Autowired
    private RoleAuthoritiesMapper roleAuthoritiesMapper;

    @Override
    public List<Authorities> listByUserId(String userId) {
        return authoritiesMapper.listByUserId(userId);
    }

    @Override
    public List<Authorities> list() {
        return authoritiesMapper.selectList(new EntityWrapper<Authorities>().orderBy("order_number", true));
    }

    @Override
    public List<String> listByRoleId(List<String> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return new ArrayList<>();
        }
        return authoritiesMapper.listByRoleId(roleIds);
    }

    @Override
    public List<String> listByRoleId(String roleId) {
        List<String> roleIds = new ArrayList<>();
        if (roleId != null && !roleId.trim().isEmpty()) {
            roleIds.add(roleId);
        }
        return listByRoleId(roleIds);
    }

    @Override
    public boolean add(Authorities authorities) {
        authorities.setAuthorityId(UUIDUtil.randomUUID8());
        authorities.setCreateTime(new Date());
        return authoritiesMapper.insert(authorities) > 0;
    }

    @Override
    public boolean update(Authorities authorities) {
        return authoritiesMapper.updateById(authorities) > 0;
    }

    @Override
    public boolean delete(String authorityId) {
        roleAuthoritiesMapper.delete(new EntityWrapper<RoleAuthorities>().eq("authority_id", authorityId));
        return authoritiesMapper.deleteById(authorityId) > 0;
    }

    @Override
    public boolean add(List<Authorities> authorities) {
        authoritiesMapper.delete(null);
        for (Authorities one : authorities) {
            one.setCreateTime(new Date());
            authoritiesMapper.insert(one);
        }
        roleAuthoritiesMapper.deleteTrash();
        return true;
    }

    @Override
    public boolean addRoleAuth(String roleId, String authId) {
        RoleAuthorities roleAuthorities = new RoleAuthorities();
        roleAuthorities.setId(UUIDUtil.randomUUID8());
        roleAuthorities.setRoleId(roleId);
        roleAuthorities.setAuthorityId(authId);
        roleAuthorities.setCreateTime(new Date());
        return roleAuthoritiesMapper.insert(roleAuthorities) > 0;
    }

    @Override
    public boolean deleteRoleAuth(String roleId, String authId) {
        return roleAuthoritiesMapper.delete(new EntityWrapper<RoleAuthorities>().eq("role_id", roleId).eq("authority_id", authId)) > 0;
    }

}
