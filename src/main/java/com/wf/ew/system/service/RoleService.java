package com.wf.ew.system.service;

import com.wf.ew.system.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getByUserId(Integer userId);

    List<Role> list(boolean showDelete);

    Role getById(Integer roleId);

    boolean add(Role role);

    boolean update(Role role);

    boolean updateState(Integer roleId, int isDelete);  // 逻辑删除

    boolean delete(Integer roleId);  // 物理删除

}
