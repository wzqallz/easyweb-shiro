package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wf.ew.common.PageResult;
import com.wf.ew.common.exception.BusinessException;
import com.wf.ew.common.exception.ParameterException;
import com.wf.ew.common.shiro.EndecryptUtil;
import com.wf.ew.common.utils.StringUtil;
import com.wf.ew.common.utils.UUIDUtil;
import com.wf.ew.system.dao.RoleMapper;
import com.wf.ew.system.dao.UserMapper;
import com.wf.ew.system.dao.UserRoleMapper;
import com.wf.ew.system.model.Role;
import com.wf.ew.system.model.User;
import com.wf.ew.system.model.UserRole;
import com.wf.ew.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public PageResult<User> list(int pageNum, int pageSize, boolean showDelete, String column, String value) {
        Wrapper<User> wrapper = new EntityWrapper<User>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        if (!showDelete) {
            wrapper.eq("state", 0);
        }
        Page<User> userPage = new Page<>(pageNum, pageSize);
        List<User> userList = userMapper.selectPage(userPage, wrapper);
        // 查询user的角色
        List<String> userIds = new ArrayList<>();
        for (User one : userList) {
            userIds.add(one.getUserId());
        }
        List<Role> roles = roleMapper.selectList(null);
        List<UserRole> userRoles = userRoleMapper.selectList(new EntityWrapper().in("user_id", userIds));
        for (User one : userList) {
            List<Role> tempUrs = new ArrayList<>();
            for (UserRole ur : userRoles) {
                if (one.getUserId().equals(ur.getUserId())) {
                    for (Role r : roles) {
                        if (ur.getRoleId().equals(r.getRoleId())) {
                            tempUrs.add(r);
                        }
                    }
                }
            }
            one.setRoles(tempUrs);
        }
        return new PageResult<>(userPage.getTotal(), userList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(User user) throws BusinessException {
        String userId = UUIDUtil.randomUUID8();
        user.setUserId(userId);
        String finalSecret = EndecryptUtil.encrytMd5(user.getPassword(), userId, 3);
        user.setPassword(finalSecret);
        user.setState(0);
        user.setCreateTime(new Date());
        try {
            boolean rs = userMapper.insert(user) > 0;
            if (rs) {
                addUserRole(userId, user.getRoles());
            }
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("账号已经存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(User user) {
        boolean rs = userMapper.updateById(user) > 0;
        if (rs) {
            userRoleMapper.delete(new EntityWrapper().eq("user_id", user.getUserId()));
            addUserRole(user.getUserId(), user.getRoles());
        }
        return rs;
    }

    private void addUserRole(String userId, List<Role> roles) {
        if (roles == null) {
            return;
        }
        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setId(UUIDUtil.randomUUID8());
            userRole.setUserId(userId);
            userRole.setRoleId(role.getRoleId());
            userRole.setCreateTime(new Date());
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public boolean updateState(String userId, int state) throws ParameterException {
        if (state != 0 && state != 1) {
            throw new ParameterException("state值需要在[0,1]中");
        }
        User user = new User();
        user.setUserId(userId);
        user.setState(state);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean updatePsw(String userId, String password) {
        User user = new User();
        user.setUserId(userId);
        String finalSecret = EndecryptUtil.encrytMd5(password, userId, 3);
        user.setPassword(finalSecret);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public User getById(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public boolean delete(String userId) {
        return userMapper.deleteById(userId) > 0;
    }
}
