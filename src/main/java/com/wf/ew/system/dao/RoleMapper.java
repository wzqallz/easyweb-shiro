package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectByUserId(Integer userId);
}
