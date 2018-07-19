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
    private UserRoleMapper userRoleMapper;

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public PageResult<User> list(int pageNum, int pageSize, boolean showDelete, String column, String value) {
        Wrapper<User> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        if (!showDelete) {  // 不显示锁定的用户
            wrapper.eq("state", 0);
        }
        Page<User> userPage = new Page<>(pageNum, pageSize);
        List<User> userList = userMapper.selectPage(userPage, wrapper.orderBy("create_time", true));
        // 查询user的角色
        List<UserRole> userRoles = userRoleMapper.selectByUserIds(getUserIds(userList));
        for (User one : userList) {
            List<Role> tempURs = new ArrayList<>();
            for (UserRole ur : userRoles) {
                if (one.getUserId().equals(ur.getUserId())) {
                    tempURs.add(new Role(ur.getRoleId(), ur.getRoleName()));
                }
            }
            one.setRoles(tempURs);
        }
        return new PageResult<>(userPage.getTotal(), userList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(User user) throws BusinessException {
        if (userMapper.getByUsername(user.getUsername()) != null) {
            throw new BusinessException("账号已经存在");
        }
        String userId = UUIDUtil.randomUUID8();
        user.setUserId(userId);
        user.setPassword(EndecryptUtil.encrytMd5(user.getPassword(), userId, 3));
        user.setState(0);
        user.setCreateTime(new Date());
        boolean rs = userMapper.insert(user) > 0;
        if (rs) {
            if (!addUserRole(userId, user.getRoles())) {
                throw new BusinessException("添加失败，请重试");
            }
        }
        return rs;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(User user) {
        boolean rs = userMapper.updateById(user) > 0;
        if (rs) {
            if (userRoleMapper.delete(new EntityWrapper().eq("user_id", user.getUserId())) <= 0) {
                throw new BusinessException("修改失败，请重试");
            }
            if (!addUserRole(user.getUserId(), user.getRoles())) {
                throw new BusinessException("修改失败，请重试");
            }
        }
        return rs;
    }

    /**
     * 添加用户角色
     */
    private boolean addUserRole(String userId, List<Role> roles) {
        if (roles == null || roles.size() <= 0) {
            return false;
        }
        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setId(UUIDUtil.randomUUID8());
            userRole.setUserId(userId);
            userRole.setRoleId(role.getRoleId());
            userRole.setCreateTime(new Date());
            boolean rs = userRoleMapper.insert(userRole) > 0;
            if (!rs) {
                return false;
            }
        }
        return true;
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
        user.setPassword(EndecryptUtil.encrytMd5(password, userId, 3));
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

    private List<String> getUserIds(List<User> userList) {
        List<String> userIds = new ArrayList<>();
        for (User one : userList) {
            userIds.add(one.getUserId());
        }
        return userIds;
    }
}
